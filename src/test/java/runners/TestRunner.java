package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "stepdefinations",
                "hooks"
        },
        tags = "@sanity",
        plugin = {
                "pretty",
                "html:reports/Cucumber.html"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}