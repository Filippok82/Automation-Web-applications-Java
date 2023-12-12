package lesson01;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class Task03 {

    final String USERNAME = "Student-5";
    final String PASSWORD = "97d2434151";
    static WebDriver driver;


    @BeforeAll
    static void creation() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://test-stand.gb.ru/login");

    }

    @Test
    public void task03() {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement userName = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='text']")));
        WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='password']")));
        userName.sendKeys(USERNAME);
        password.sendKeys(PASSWORD);

        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginButton));
        WebElement helloUser = driver.findElement(By.partialLinkText(USERNAME));
        String actualUserName = helloUser.getText().replace("\n", " ").trim();
        Assertions.assertEquals(String.format("Hello, %s", USERNAME), actualUserName);
    }

    @AfterAll
    static void close() {
        driver.quit();
    }
}
