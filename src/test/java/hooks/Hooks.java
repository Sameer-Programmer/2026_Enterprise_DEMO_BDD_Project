package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;

import java.nio.file.Path;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {

        System.out.println("===== Test Started =====");

        DriverFactory.initializeDriver();

        // Create Extent test using Cucumber scenario name
        ExtentReportManager.startTest(
                scenario.getName()
        );
    }

    @After
    public void tearDown(Scenario scenario) {

        System.out.println("===== Test Finished =====");

        if (scenario.isFailed()) {

            System.out.println(
                    "Scenario Failed: " +
                            scenario.getName()
            );

            String screenshotName =
                    scenario.getName()
                            .replaceAll("[^a-zA-Z0-9]", "_")
                            + "_FAILED";

            String screenshotPath =
                    ScreenshotUtil.captureScreenshot(
                            screenshotName
                    );

            ExtentReportManager.getTest()
                    .fail("Scenario Failed");

            if (screenshotPath != null) {

                String relativeScreenshotPath =
                        Path.of(
                                "screenshots",
                                Path.of(screenshotPath)
                                        .getFileName()
                                        .toString()
                        ).toString();

                ExtentReportManager.getTest()
                        .addScreenCaptureFromPath(
                                relativeScreenshotPath
                        );
            }

        } else {

            ExtentReportManager.getTest()
                    .pass("Scenario Passed");
        }

        DriverFactory.quitDriver();

        ExtentReportManager.removeTest();

        System.out.println("Test Ended");
    }
}