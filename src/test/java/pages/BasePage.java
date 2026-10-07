package pages;

import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.WaitHelper;

public class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;

    public BasePage(WebDriver driver) {

        this.driver = driver;
        int timeout = Integer.parseInt(ConfigReader.getProperty("explicitWait"));
        this.waitHelper = new WaitHelper(driver, timeout);
    }
}