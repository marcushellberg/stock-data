# Stock Data demo app

This is an example application to show how you can use Hilla, React, Spring Boot, Cassandra, and Pulsar together.

## Requirements

- Java 17+
- Node 18
- Cassandra and Pulsar instances

## Configuration

1. Configure Astra DB and Astra Streaming instances on the same cloud/region.
2. Configure the properties into `application.properties`
3. Download the DataStax secure connection bundle, rename it to `secure-connect.zip` and add it to `resources/META-INF`.
4. Add your [Alpha Vantage API key](https://www.alphavantage.co/support/#api-key) to `application.properties`

## Running the application

Start the application by running `Application.java`. Alternatively, you can use the bundled Maven wrapper:

Mac/Linux

```
./mvnw
```

Windows

```
.\mvnw
```
