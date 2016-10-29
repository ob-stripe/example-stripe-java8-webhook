# Stripe webhook Java 8 example

This is a simple example project illustrating how to implement a [Stripe webhooks endpoint](https://stripe.com/docs/webhooks).

It will also [verify the events](https://stripe.com/docs/webhooks#verifying-events) by fetching them back from Stripe.

## Use with Heroku

Deploy the project on your Heroku account:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

When deploying, you'll be prompted for your secret API key (you can find it in the [API keys](https://dashboard.stripe.com/account/apikeys) tab of your account settings).

Once the app is running on Heroku, head back to your [webhooks](https://dashboard.stripe.com/account/webhooks) settings and add a test webhook endpoint with your app's URL followed by `/webhook`. E.g. if the URL of the Heroku app is `https://thawing-crag-63114.herokuapp.com`, set the endpoint's URL to be `https://thawing-crag-63114.herokuapp.com/webhook`.

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

Get your secret API key (you can find it in the [API keys](https://dashboard.stripe.com/account/apikeys) tab of your account settings) and run the project:

```bash
STRIPE_TEST_SECRET_KEY=sk_test_... \
mvn exec:java
```

Head to your [webhooks](https://dashboard.stripe.com/account/webhooks) settings and add a test webhook endpoint with URL set to your server's URL followed by `/webhook`. The server runs on port 4567 by default (you can use the `PORT` environment variable to change this), so for instance if your server is accessible at `myserver.com`, set the endpoint's URL to be `http://myserver.com:4567/webhook`.

If your server is not directly visible from the internet, you can use [ngrok](https://ngrok.com/) to create a tunnel:

```bash
ngrok http 4567
```

then set the endpoint's URL to the one provided by ngrok followed by `/webhook`, e.g. `http://87fe9a4d.ngrok.io/webhook`.

You can then create test events on your Stripe account and view the logs of the app to check that the events were correctly received and verified.
