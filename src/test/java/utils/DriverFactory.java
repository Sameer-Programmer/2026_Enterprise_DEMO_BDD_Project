package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {
    }

    public static void initializeDriver() {

        String browser = ConfigReader.getProperty("browser");
        String headless = ConfigReader.getProperty("headless");

        if (browser == null || browser.isBlank()) {
            browser = "chrome";
        }

        boolean isHeadless =
                Boolean.parseBoolean(headless);

        switch (browser.toLowerCase()) {

            case "chrome":

                ChromeOptions chromeOptions =
                        new ChromeOptions();

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                driver = new ChromeDriver(chromeOptions);

                break;

            case "firefox":

                FirefoxOptions firefoxOptions =
                        new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("-headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }

                driver = new FirefoxDriver(firefoxOptions);

                break;

            case "edge":

                EdgeOptions edgeOptions =
                        new EdgeOptions();

                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }

                driver = new EdgeDriver(edgeOptions);

                break;

            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }

        // Implicit wait
        int implicitWait = Integer.parseInt(
                ConfigReader.getProperty("implicitWait")
        );

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(implicitWait)
        );

        // Delete all cookies
        driver.manage().deleteAllCookies();

        // Set browser window size
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is not initialized. " +
                            "Call initializeDriver() first."
            );
        }

        return driver;
    }

    public static void quitDriver() {

        if (driver != null) {

            driver.quit();
            driver = null;
        }
    }
}