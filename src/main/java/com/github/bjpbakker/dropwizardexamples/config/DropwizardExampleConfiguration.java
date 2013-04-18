package com.github.bjpbakker.dropwizardexamples.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class DropwizardExampleConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String template;
    @NotEmpty
    @JsonProperty
    private String anonymousName;

    public String getTemplate() {
        return template;
    }

    public String getAnonymousName() {
        return anonymousName;
    }
}
