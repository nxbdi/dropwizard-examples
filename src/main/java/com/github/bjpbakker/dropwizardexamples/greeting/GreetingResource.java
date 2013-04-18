package com.github.bjpbakker.dropwizardexamples.greeting;

import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/greeting")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {
    private final String template;
    private final String anonymousName;
    private final AtomicLong counter;

    public GreetingResource(String template, String anonymousName) {
        this.template = template;
        this.anonymousName = anonymousName;
        this.counter = new AtomicLong();
    }

    @GET
    public Greeting greet(@QueryParam("name") Optional<String> name) {
        return new Greeting(counter.incrementAndGet(), formatGreetingTo(name.or(anonymousName)));
    }

    private String formatGreetingTo(String name) {
        return String.format(template, name);
    }
}
