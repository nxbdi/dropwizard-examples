# Dropwizard Examples

This project contains examples of working with [Dropwizard](http://dropwizard.codahale.com). 
Dropwizard is a Java framework for developing RESTful web services with standard Java libraries 
like Jersey and Jackson. Adding to these Dropwizard provides simple yet elegant solutions to 
configuration, metrics, operational tools and more.

# Running the samples

To run the examples first you must issue a build.

    $ mvn package

This builds an executable jar in the `target` directory.

To run the examples simply execute the jar and provide it your configuration. A default 
configuration is available in `config/config.yml`.

    $ java -jar target/dropwizard-examples.jar server config/config.yml

