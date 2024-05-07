package org.knowm.xdropwizard.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.CompletionCallback;
import jakarta.ws.rs.container.Suspended;


@Path("/async")
public class AsyncResource {

    private static int numberOfSuccessResponses = 0;
    private static int numberOfFailures = 0;
    private static Throwable lastException = null;

    @GET
    public void asyncGetWithTimeout(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.register(
                new CompletionCallback() {
                    @Override
                    public void onComplete(Throwable throwable) {
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
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
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
                })
                .start();
    }
}
