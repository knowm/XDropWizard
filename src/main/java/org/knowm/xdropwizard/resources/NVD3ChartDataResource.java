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

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import io.dropwizard.jersey.caching.CacheControl;

/**
 * @author timmolter
 */
@Path("nvd3data")
public class NVD3ChartDataResource {

  @Path("chartdata")
  @GET
  @Timed
  @CacheControl(noCache = true)
  @Produces(MediaType.APPLICATION_JSON)
  public ClassifierAppAllEvalLabelsPerformance getGroupPerformance() {

    return new ClassifierAppAllEvalLabelsPerformance();
  }

  public class ClassifierAppAllEvalLabelsPerformance {

    private final int dataLength = 100;
    private final Random random = new Random();

    private final int[] xAxisData;

    private final float[] a;
    private final float[] b;
    private final float[] c;
    private final float[] d;

    /**
     * Constructor
     */
    public ClassifierAppAllEvalLabelsPerformance() {

      xAxisData = new int[dataLength];
      a = new float[dataLength];
      b = new float[dataLength];
      c = new float[dataLength];
      d = new float[dataLength];

      for (int i = 0; i < dataLength; i++) {

        xAxisData[i] = i;
        a[i] = random.nextFloat();
        b[i] = random.nextFloat();
        c[i] = random.nextFloat();
        d[i] = random.nextFloat();

      }

    }

    public int[] getxAxisData() {
      return xAxisData;
    }

    public float[] getA() {
      return a;
    }

    public float[] getB() {
      return b;
    }

    public float[] getC() {
      return c;
    }

    public float[] getD() {
      return d;
    }

  }
}
