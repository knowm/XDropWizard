package com.xeiam.xdropwizard.manager;

import io.dropwizard.lifecycle.Managed;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xdropwizard.XDropWizardApplicationConfiguration.YankConfiguration;
import com.xeiam.xdropwizard.business.Book;
import com.xeiam.xdropwizard.business.BooksDAO;
import com.xeiam.yank.PropertiesUtils;
import com.xeiam.yank.Yank;

/**
 * <p>
 * This is where Yank is bound to the main DropWizard thread.
 * </p>
 *
 * @author timmolter
 */
public class YankManager implements Managed {

  private final Logger logger = LoggerFactory.getLogger(YankManager.class);

  private final YankConfiguration yankConfiguration;

  /**
   * Constructor
   */
  public YankManager(YankConfiguration yankConfiguration) {

    this.yankConfiguration = yankConfiguration;
  }

  @Override
  public void start() throws Exception {

    logger.info("initializing Yank...");

    // setup connection pool
    if (yankConfiguration.getDbPropsFileName() == null || yankConfiguration.getSqlPropsFileName().trim().length() == 0) {
      Yank.setupDataSource(PropertiesUtils.getPropertiesFromClasspath("DB.properties"));
    } else {
      Yank.setupDataSource(PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getDbPropsFileName()));
    }

    // setup sql statements
    if (yankConfiguration.getSqlPropsFileName() != null && yankConfiguration.getSqlPropsFileName().trim().length() > 0) {
      Yank.addSQLStatements(PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getSqlPropsFileName()));
    }

    logger.info("Yank started successfully.");

    /// The below code is just to create some dummy data in an in-memory DB for use later by the REST API. See YankBookResource.

    // put some data in DB
    BooksDAO.createBooksTable();

    List<Book> books = new ArrayList<Book>();

    Book book = new Book();
    book.setTitle("Cryptonomicon");
    book.setAuthor("Neal Stephenson");
    book.setPrice(23.99);
    books.add(book);

    book = new Book();
    book.setTitle("Harry Potter");
    book.setAuthor("Joanne K. Rowling");
    book.setPrice(11.99);
    books.add(book);

    book = new Book();
    book.setTitle("Don Quijote");
    book.setAuthor("Cervantes");
    book.setPrice(21.99);
    books.add(book);

    BooksDAO.insertBatch(books);

  }

  @Override
  public void stop() throws Exception {

    logger.info("shutting down Yank...");

    Yank.releaseDataSource();

    logger.info("Yank shutdown successfully.");
  }

}
