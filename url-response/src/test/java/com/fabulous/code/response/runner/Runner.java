package com.fabulous.code.response.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true,
        glue = "com.fabulous.code.response",
        features = {"src/test/resources/feature"},
        format = {"pretty", "html:target/cucumber", "json:target/cucumber-report.json"})
public class Runner {
}
