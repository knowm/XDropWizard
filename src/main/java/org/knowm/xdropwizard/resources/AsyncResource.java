/**
 * Copyright 2015-2018 Knowm Inc. (http://knowm.org) and contributors.
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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;

/**
 * @author timmolter
 */
@Path("/async") public class AsyncResource {

  private static int numberOfSuccessResponses = 0;
  private static int numberOfFailures = 0;
  private static Throwable lastException = null;

  @GET public void asyncGetWithTimeout(@Suspended final AsyncResponse asyncResponse) {
    asyncResponse.register(new CompletionCallback() {
      @Override public void onComplete(Throwable throwable) {
        if (throwable == null) {
          // no throwable - the processing ended successfully
          // (response already written to the client)
          numberOfSuccessResponses++;
        } else {
          numberOfFailures++;
          lastException = throwable;
        }
      }
    });
    new Thread(new Runnable() {
      @Override public void run() {
        String result = veryExpensiveOperation();
        asyncResponse.resume(result);
      }

      private String veryExpensiveOperation() {
        // ... very expensive operation
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        return "Hello Async!";
      }
    }).start();
  }
}
