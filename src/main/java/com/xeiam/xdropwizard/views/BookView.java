package com.xeiam.xdropwizard.views;

import com.xeiam.xdropwizard.business.Book;
import com.yammer.dropwizard.views.View;

/**
 * @author timmolter
 */
public class BookView extends View {

  public BookView() {

    super("ftl/book.ftl");
  }

  public Book getBook() {

    Book book = new Book();
    book.setTitle("Cryptonomicon");
    book.setAuthor("Neal Stephenson");
    book.setPrice(23.99);
    return book;
  }

}
