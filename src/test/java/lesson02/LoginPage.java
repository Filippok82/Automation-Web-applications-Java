package lesson02;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class LoginPage {

    WebDriver driver;
    private final WebDriverWait wait;
    @FindBy(css="form#login input[type='text']")
    WebElement userNameField;
    @FindBy(css="form#login input[type='password']")
    WebElement passwordField;
    @FindBy(css="form#login button")
    WebElement loginButton;

    public LoginPage(WebDriver driver, WebDriverWait wait){
        this.driver=driver;
        this.wait=wait;
        PageFactory.initElements(driver,this);
    }

    void authorization(String userName, String password) {
        wait.until(ExpectedConditions.visibilityOf(userNameField));
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        userNameField.sendKeys(userName);
        passwordField.sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();

    }
    void checkButtonInvisible(){
        wait.until(ExpectedConditions.invisibilityOf(loginButton));


    }



}
