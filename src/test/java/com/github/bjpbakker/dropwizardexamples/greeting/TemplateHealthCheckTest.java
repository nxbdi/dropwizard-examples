package com.github.bjpbakker.dropwizardexamples.greeting;

import org.junit.Test;

import static com.yammer.metrics.core.HealthCheck.Result.healthy;
import static com.yammer.metrics.core.HealthCheck.Result.unhealthy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TemplateHealthCheckTest {
    private static final String HEALTHY_TEMPLATE = "Hello, %s!";
    private static final String UNHEALTHY_TEMPLATE = "Hello there!";

    @Test
    public void it_has_a_distinguishing_name() throws Exception {
        assertThat(new TemplateHealthCheck(HEALTHY_TEMPLATE).getName(), is("template"));
    }

    @Test
    public void it_checks_a_healthy_template() throws Exception {
        assertThat(new TemplateHealthCheck(HEALTHY_TEMPLATE).check(), is(healthy()));
    }

    @Test
    public void it_checks_an_unhealthy_template() throws Exception {
        assertThat(new TemplateHealthCheck(UNHEALTHY_TEMPLATE).check(), is(unhealthy("template doesn't include a name")));
    }
}
