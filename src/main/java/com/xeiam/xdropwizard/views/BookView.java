package com.xeiam.xdropwizard.views;

import io.dropwizard.views.View;

import com.google.common.base.Charsets;
import com.xeiam.xdropwizard.business.Book;

/**
 * @author timmolter
 */
public class BookView extends View {

  public BookView() {

    super("ftl/book.ftl", Charsets.UTF_8);
  }

  public Book getBook() {

    Book book = new Book();
    book.setTitle("Cryptonomicon");
    book.setAuthor("Neal Stephenson");
    book.setPrice(23.99);
    return book;
  }

}
