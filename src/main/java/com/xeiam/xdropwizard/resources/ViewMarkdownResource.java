package com.xeiam.xdropwizard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xdropwizard.views.MarkdownView;
import com.yammer.dropwizard.jersey.caching.CacheControl;
import com.yammer.metrics.annotation.Timed;

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
