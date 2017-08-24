# Stripe webhook Java 8 example

This is a simple example project illustrating how to implement a [Stripe webhooks endpoint](https://stripe.com/docs/webhooks).

It will also [verify the events](https://stripe.com/docs/webhooks#verifying-events) by using [Stripe's Java library](https://github.com/stripe/stripe-java) to check the events' signatures.

## Use with Heroku

Head to your [webhooks](https://dashboard.stripe.com/account/webhooks) settings and create an endpoint with any URL (you'll change it later). Click on the button to reveal the signing secret.

Then deploy the project on your Heroku account:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

When deploying, you'll be prompted for the webhook signing secret.

Once the app is running on Heroku, head back to the webhooks settings and update the endpoint's URL with your app's URL followed by `/webhook`. E.g. if the URL of the Heroku app is `https://thawing-crag-63114.herokuapp.com`, set the endpoint's URL to be `https://thawing-crag-63114.herokuapp.com/webhook`.

You can then create test events and view the logs of your Heroku app to check that the events were correctly received and verified.

## Use as a standalone project

### Requirements

- Java 1.8 or later
- Maven

### Instructions

Clone the repository:

```bash
git clone https://github.com/ob-stripe/example-stripe-java8-webhook.git
```

Compile the project:

```bash
mvn compile
```

Head to your [webhooks](https://dashboard.stripe.com/account/webhooks) settings and add a test webhook endpoint with URL set to your server's URL followed by `/webhook`. The server runs on port 4567 by default (you can use the `PORT` environment variable to change this), so for instance if your server is accessible at `myserver.com`, set the endpoint's URL to be `http://myserver.com:4567/webhook`.

If your server is not directly visible from the internet, you can use [ngrok](https://ngrok.com/) to create a tunnel:

```bash
ngrok http 4567
```

then set the endpoint's URL to the one provided by ngrok followed by `/webhook`, e.g. `http://87fe9a4d.ngrok.io/webhook`.

Once the endpoint is created, you can reveal the signing secret and use its value to run the project:

```bash
STRIPE_ENDPOINT_SECRET=whsec_... \
mvn exec:java
```

You can then create test events on your Stripe account and view the logs of the app to check that the events were correctly received and verified.
