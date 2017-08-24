package com.stripe;

import java.io.IOException;
import java.util.Map;

import static spark.Spark.port;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.http.HttpStatus;

import com.google.gson.JsonSyntaxException;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;


public final class ExampleStripeJava8Webhook {
    private static final Logger LOGGER =
        LoggerFactory.getLogger(ExampleStripeJava8Webhook.class);

    private ExampleStripeJava8Webhook() { }

    public static void main(final String[] args) throws IOException {
        Map<String, String> env = System.getenv();

        // Retrieve webhook endpoint's signing secret
        final String endpointSecret = env.get("STRIPE_ENDPOINT_SECRET");

        // Set the webserver's port, if necessary
        if (env.containsKey("PORT")) {
            port(Integer.parseInt(env.get("PORT")));
        }

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            Event event = null;

            try {
                // Retrieve the request's body and parse it as JSON
                event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret
                );
            } catch (JsonSyntaxException e) {
                String error = "Error: invalid JSON payload ("
                             + e.getMessage() + ")";
                LOGGER.warn(error);
                response.status(HttpStatus.SC_BAD_REQUEST);
                return error;
            } catch (SignatureVerificationException e) {
                String error = "Error: signature verification failed ("
                             + e.getMessage() + ")";
                LOGGER.warn(error);
                response.status(HttpStatus.SC_BAD_REQUEST);
                return error;
            } catch (Exception e) {
                String error = "Error: unexpected exception "
                             + e.getClass().getName() + " ("
                             + e.getMessage() + ")\n";
                LOGGER.warn(error);
                response.status(HttpStatus.SC_BAD_REQUEST);
                return error;
            }

            LOGGER.info("Received event: " + event.getId()
                        + ", type: " + event.getType()
                        + ", account: " + event.getAccount());

            // Do something with event

            // Reply with 200 status code to acknowledge receipt of the
            // webhook
            response.status(HttpStatus.SC_OK);
            return "";
        });
    }
}
