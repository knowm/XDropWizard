package com.xeiam.xdropwizard.resources;

import io.dropwizard.jersey.caching.CacheControl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.xeiam.xdropwizard.views.BookView;

@Path("view/book")
@Produces(MediaType.TEXT_HTML)
public class ViewBookResource {

  @GET
  @Timed
  @CacheControl(noCache = true)
  public BookView bookView() {

    return new BookView();
  }

}
