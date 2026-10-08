package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import listeners.ExtentTestNGListener;
import org.testng.annotations.Listeners;

@Listeners(ExtentTestNGListener.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinations", "hooks"},
        tags = "@smoke",
        plugin = {"pretty", "html:reports/Cucumber.html"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}