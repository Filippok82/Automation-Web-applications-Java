package lesson01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Task02 {

    @Test
    public void task02() {
        final String USERNAME = "Student-5";
        final String PASSWORD = "97d2434151";
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\IrinaFil\\IdeaProjects\\Automation-Web-applications-Java\\src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://test-stand.gb.ru/login");

        WebElement userName = driver.findElement(By.cssSelector("form#login input[type='text']"));
        WebElement password = driver.findElement(By.cssSelector("form#login input[type='password']"));
        userName.sendKeys(USERNAME);
        password.sendKeys(PASSWORD);

        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();
        WebElement helloUser = driver.findElement(By.partialLinkText(USERNAME));
        String actualUserName = helloUser.getText().replace("\n"," ").trim();
        Assertions.assertEquals(String.format("Hello, %s",USERNAME),actualUserName);

        driver.quit();
    }

}
