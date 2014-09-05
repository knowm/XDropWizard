package com.xeiam.xdropwizard.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.BitmapEncoder.BitmapFormat;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.QuickChart;

/**
 * @author timmolter
 */
@Path("xchart")
public class XChartResource {

  @GET
  @Path("random.png")
  @Produces("image/png")
  public Response getRandomLineChart() throws IOException {

    Chart chart = QuickChart.getChart("XChart Sample - Random Walk", "X", "Y", null, null, getRandomWalk(105));

    return Response.ok().type("image/png").entity(BitmapEncoder.getBitmapBytes(chart, BitmapFormat.PNG)).build();
  }

  private double[] getRandomWalk(int numPoints) {

    double[] y = new double[numPoints];
    for (int i = 1; i < y.length; i++) {
      y[i] = y[i - 1] + Math.random() - .5;
    }
    return y;
  }

}
