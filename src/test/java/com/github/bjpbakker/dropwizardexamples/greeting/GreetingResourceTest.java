package com.github.bjpbakker.dropwizardexamples.greeting;

import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GreetingResourceTest extends ResourceTest {

    @Override
    protected void setUpResources() throws Exception {
        addResource(new GreetingResource("Hello, %s", "guest"));
    }

    @Test
    public void greets_a_guest() throws Exception {
        Greeting greeting = client().resource("/greeting").get(Greeting.class);
        assertThat(greeting, is(new Greeting(1, "Hello, guest")));
    }

    @Test
    public void greets_a_user() throws Exception {
        Greeting greeting = client().resource("/greeting?name=Bart").get(Greeting.class);
        assertThat(greeting, is(new Greeting(1, "Hello, Bart")));
    }
}
