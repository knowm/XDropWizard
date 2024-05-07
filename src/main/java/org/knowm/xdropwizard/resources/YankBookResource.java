package org.knowm.xdropwizard.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xdropwizard.business.Book;
import org.knowm.xdropwizard.business.BooksDAO;

import java.util.List;


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
