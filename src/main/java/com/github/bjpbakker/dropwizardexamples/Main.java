package com.github.bjpbakker.dropwizardexamples;

import com.github.bjpbakker.dropwizardexamples.config.DropwizardExampleConfiguration;
import com.github.bjpbakker.dropwizardexamples.greeting.GreetingResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class Main extends Service<DropwizardExampleConfiguration> {
    @Override
    public void initialize(Bootstrap<DropwizardExampleConfiguration> configurationBootstrap) {}

    @Override
    public void run(DropwizardExampleConfiguration configuration, Environment environment) throws Exception {
        final String template = configuration.getTemplate();
        final String anonymousName = configuration.getAnonymousName();
        environment.addResource(new GreetingResource(template, anonymousName));
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }
}
