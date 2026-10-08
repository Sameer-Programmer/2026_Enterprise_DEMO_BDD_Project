package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    public static String captureScreenshot(String screenshotName) {

        try {
            TakesScreenshot takesScreenshot =
                    (TakesScreenshot) DriverFactory.getDriver();

            byte[] screenshotBytes =
                    takesScreenshot.getScreenshotAs(OutputType.BYTES);

            // Create reports/screenshots directory
            Path screenshotDirectory =
                    Paths.get("reports", "screenshots");

            Files.createDirectories(screenshotDirectory);

            // Current date and time
            String dateTime =
                    LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            // Final screenshot name
            String finalScreenshotName =
                    screenshotName + "_" + dateTime + ".png";

            Path screenshotPath =
                    screenshotDirectory.resolve(finalScreenshotName);

            Files.write(screenshotPath, screenshotBytes);

            System.out.println(
                    "Screenshot captured: " +
                            screenshotPath.toAbsolutePath()
            );

            return screenshotPath.toString();

        } catch (Exception e) {

            System.out.println(
                    "Failed to capture screenshot: " +
                            e.getMessage()
            );

            return null;
        }
    }
}