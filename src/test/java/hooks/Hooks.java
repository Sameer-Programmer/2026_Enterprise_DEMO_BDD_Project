package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;
import utils.ScreenshotUtil;

public class Hooks {

    @Before
    public void setUp() {

        System.out.println("===== Test Started =====");

        DriverFactory.initializeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {

        System.out.println("===== Test Finished =====");

        if (scenario.isFailed()) {

            System.out.println(
                    "Scenario Failed: " + scenario.getName()
            );

            String screenshotName =
                    scenario.getName()
                            .replaceAll("[^a-zA-Z0-9]", "_")
                            + "_FAILED";

            ScreenshotUtil.captureScreenshot(screenshotName);
        }

        DriverFactory.quitDriver();

        System.out.println("Test Ended");
    }
}