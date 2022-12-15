Gatling load testing tool for SlimStampen
=============================================

This repository is a fork of https://github.com/gatling/gatling-gradle-plugin-demo-java

## Setup
To setup the load testing tool you will need to set the project SDK to Java 16 and run the `build.gradle` file.

## Run
To run the load tests, you will need to run the `gatling-load-testing-tool [gatlingRun]` configuration. This configuration can be found and added to your project in `.run\gatling-load-testing-tool [gatlingRun].run.xml`.

## Reports
Reports of the load tests are generated and stored in `/build/reports/gatling`. The command line output of the load tests will also display an url to the report of that run.
