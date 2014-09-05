package com.xeiam.xdropwizard.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xdropwizard.business.Book;
import com.xeiam.xdropwizard.business.BooksDAO;

/**
 * @author timmolter
 */
@Path("book")
@Produces(MediaType.APPLICATION_JSON)
public class YankBookResource {

  @GET
  @Path("random")
  public Book getRandomBook() {

    return BooksDAO.selectRandomBook();
  }

  @GET
  @Path("all")
  public List<Book> getAllBooks() {

    return BooksDAO.selectAllBooks();
  }
}
