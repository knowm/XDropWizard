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
package org.knowm.xdropwizard;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;

/**
 * https://dennis-xlc.gitbooks.io/restful-java-with-jax-rs-2-0-2rd-edition/content/en/part1/chapter13/async_invoker_client_api.html
 *
 * @author timmolter
 */
public class AsyncTest {

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    AsyncTest asyncTest = new AsyncTest();
    asyncTest.go();
  }

  private void go() throws InterruptedException, ExecutionException {

    Client client = ClientBuilder.newClient();

    final Future<String> entityFuture = client.target("http://localhost:9090/service/async").request().async().get(new InvocationCallback<String>() {
      @Override public void completed(String response) {
        System.out.println("Response entity '" + response + "' received.");
      }

      @Override public void failed(Throwable throwable) {
        System.out.println("Invocation failed.");
        throwable.printStackTrace();
      }
    });
    System.out.println(entityFuture.get());

  }

}
