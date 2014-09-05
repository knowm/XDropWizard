package com.xeiam.xdropwizard.manager;

import io.dropwizard.lifecycle.Managed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xdropwizard.XDropWizardApplicationConfiguration.YankConfiguration;
import com.xeiam.xdropwizard.business.Book;
import com.xeiam.xdropwizard.business.BooksDAO;
import com.xeiam.yank.DBConnectionManager;
import com.xeiam.yank.PropertiesUtils;

/**
 * <p>
 * This is where Yank is bound to the main DropWizard thread.
 * </p>
 * 
 * @author timmolter
 */
public class YankManager implements Managed {

  private final Logger logger = LoggerFactory.getLogger(YankManager.class);

  private YankConfiguration yankConfiguration;

  /**
   * Constructor
   */
  public YankManager(YankConfiguration yankConfiguration) {

    this.yankConfiguration = yankConfiguration;
  }

  @Override
  public void start() throws Exception {

    logger.info("initializing Yank...");

    if (yankConfiguration.getSqlPropsFileName() == null || yankConfiguration.getSqlPropsFileName().trim().length() < 1) {
      DBConnectionManager.INSTANCE.init(PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getDbPropsFileName()));
    }
    else {
      DBConnectionManager.INSTANCE.init(PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getDbPropsFileName()), PropertiesUtils.getPropertiesFromClasspath(yankConfiguration
          .getSqlPropsFileName()));
    }

    logger.info("Yank started successfully.");

    // Create an in-memory table and fill it with Book Objects. Normally the database would already exist.
    logger.info("Creating Books table and adding data to it...");

    BooksDAO.createBooksTable();

    Book book = new Book();
    book.setTitle("Cryptonomicon");
    book.setAuthor("Neal Stephenson");
    book.setPrice(23.99);
    BooksDAO.insertBook(book);

    book = new Book();
    book.setTitle("Harry Potter");
    book.setAuthor("Joanne K. Rowling");
    book.setPrice(11.99);
    BooksDAO.insertBook(book);

    book = new Book();
    book.setTitle("Don Quijote");
    book.setAuthor("Cervantes");
    book.setPrice(21.99);
    BooksDAO.insertBook(book);

    logger.info("Creating Books table complete.");
  }

  @Override
  public void stop() throws Exception {

    logger.info("shutting down Yank...");

    DBConnectionManager.INSTANCE.release();

    logger.info("Yank shutdown successfully.");
  }

}
