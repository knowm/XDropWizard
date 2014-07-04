/**
 * Copyright 2012 - 2013 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
