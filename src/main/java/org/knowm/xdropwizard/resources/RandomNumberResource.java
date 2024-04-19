package org.knowm.xdropwizard.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xdropwizard.api.RandomNumber;

/**
 * @author timmolter
 */
@Path("random")
@Produces(MediaType.APPLICATION_JSON)
public class RandomNumberResource {

  @GET
  public RandomNumber getRandom() {

    return new RandomNumber();
  }
}
