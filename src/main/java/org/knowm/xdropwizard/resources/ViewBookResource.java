package org.knowm.xdropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.caching.CacheControl;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xdropwizard.views.BookView;

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
