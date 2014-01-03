package com.xeiam.xdropwizard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xdropwizard.views.BookView;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;

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
