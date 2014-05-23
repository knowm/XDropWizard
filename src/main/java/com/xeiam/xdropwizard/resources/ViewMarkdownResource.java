package com.xeiam.xdropwizard.resources;

import io.dropwizard.jersey.caching.CacheControl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.xeiam.xdropwizard.views.MarkdownView;

@Path("view/markdown")
@Produces(MediaType.TEXT_HTML)
public class ViewMarkdownResource {

  @GET
  @Timed
  @CacheControl(noCache = true)
  @Path("{name}")
  public MarkdownView markdownView(@PathParam("name") String name, @QueryParam("age") int age) {

    return new MarkdownView(name, age);
  }

}
