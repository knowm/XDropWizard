package org.knowm.xdropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xdropwizard.api.Saying;

import java.util.concurrent.atomic.AtomicLong;


@Path("hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    /**
     * Constructor
     *
     * @param template
     * @param defaultName
     */
    public HelloWorldResource(String template, String defaultName) {

        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    /**
     * Dropwizard automatically records the duration and rate of its invocations as a Metrics Timer.
     */
    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {

        return new Saying(counter.incrementAndGet(), String.format(template, name.or(defaultName)));
    }
}
