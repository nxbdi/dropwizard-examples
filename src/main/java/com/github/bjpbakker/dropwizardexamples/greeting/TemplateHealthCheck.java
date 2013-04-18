package com.github.bjpbakker.dropwizardexamples.greeting;

import com.yammer.metrics.core.HealthCheck;

import static com.yammer.metrics.core.HealthCheck.Result.healthy;
import static com.yammer.metrics.core.HealthCheck.Result.unhealthy;

public class TemplateHealthCheck extends HealthCheck {
    private static final String TEST_NAME = "TEST";
    private final String template;

    public TemplateHealthCheck(String template) {
        super("template");
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        String saying = String.format(template, TEST_NAME);
        if (!saying.contains(TEST_NAME)) {
            return unhealthy("template doesn't include a name");
        }
        return healthy();
    }
}
