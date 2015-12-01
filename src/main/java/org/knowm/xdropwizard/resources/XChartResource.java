/**
 * Copyright 2015 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2013-2015 Xeiam LLC (http://xeiam.com) and contributors.
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
package org.knowm.xdropwizard.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.Chart;
import org.knowm.xchart.QuickChart;

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
