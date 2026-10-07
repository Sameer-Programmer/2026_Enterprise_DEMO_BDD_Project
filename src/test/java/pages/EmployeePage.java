package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmployeePage extends BasePage{
    public EmployeePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@type='button' and contains(normalize-space(), 'Add')]")
    WebElement btnAdd;

    public void clickAddEmployee() {
        waitHelper.waitForElementClickable(btnAdd).click();
    }






}
