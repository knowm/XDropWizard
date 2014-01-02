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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.QuickChart;

/**
 * @author timmolter
 */
@Path("xchart")
public class XChartResource {

  private final Logger logger = LoggerFactory.getLogger(XChartResource.class);

  @GET
  @Path("random.png")
  @Produces("image/png")
  public Response getRandomLineChart() {

    Chart chart = QuickChart.getChart("XChart Sample - Random Walk", "X", "Y", null, null, getRandomWalk(105));

    BufferedImage bufferedImage = new BufferedImage(chart.getWidth(), chart.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = bufferedImage.createGraphics();
    chart.paint(graphics2D);

    Response response = null;
    try {
      response = Response.ok().type("image/png").entity(BitmapEncoder.getPNGBytes(chart)).build();
    } catch (IOException e) {
      logger.error("ERROR GENERATING PNG!!!", e);
    }
    return response;
  }

  // generate random walk data set
  private static double[] getRandomWalk(int numPoints) {

    double[] y = new double[numPoints];
    y[0] = 0;
    for (int i = 1; i < y.length; i++) {
      y[i] = y[i - 1] + Math.random() - .5;
    }
    return y;
  }

}
