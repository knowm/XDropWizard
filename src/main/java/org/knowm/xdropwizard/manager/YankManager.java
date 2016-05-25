/**
 * Copyright 2015 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2013-2015 Xeiam LLC (http://xeiam.com) and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.xdropwizard.manager;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xdropwizard.XDropWizardApplicationConfiguration.YankConfiguration;
import org.knowm.xdropwizard.business.Book;
import org.knowm.xdropwizard.business.BooksDAO;
import org.knowm.yank.PropertiesUtils;
import org.knowm.yank.Yank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.lifecycle.Managed;

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

    Yank.releaseDefaultConnectionPool();

    logger.info("Yank shutdown successfully.");
  }

}
