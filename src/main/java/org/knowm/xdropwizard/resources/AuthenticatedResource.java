package org.knowm.xdropwizard.resources;

import io.dropwizard.auth.Auth;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xdropwizard.api.RandomNumber;
import org.knowm.xdropwizard.business.User;


@Path("authenticated")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticatedResource {

    @GET
    public RandomNumber getRandom(@Auth User user) {

        return new RandomNumber();
    }
}
