package lesson01;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.io.*;

import static org.apache.commons.io.FileUtils.copyFile;


/*Написать успешный тест на том же стенде - https://test-stand.gb.ru/login

        Тестовый сценарий:
        Логин под своими именем и паролем
        Создать новую группу, используя иконку ‘+’
        Проверить, что группа появилась в таблице
        достаточно проверить что появился title
        закрывать модальное окно создания не обязательно, таблица успешно прочитается

        Требования и рекомендации:
        использовать явные ожидания: после логина, открытие модального окна, после сохранения группы
        в конце теста написать сохранение скриншота, можно сохранять просто в директорию resources
        использовать в задании корректную структуру тестового класса, BeforeAll, BeforeEach, AfterEach
*/
public class Homework01 {

    final String USERNAME = "Student-5";
    final String PASSWORD = "97d2434151";
    String generatedString;
    static WebDriver driver;
    static WebDriverWait wait;
    File screenshot;

    @BeforeAll
    static void creation() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\IrinaFil\\IdeaProjects\\Automation-Web-applications-Java\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://test-stand.gb.ru/login");


    }

    public void authorization() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public String generateGroupName() {
        generatedString = RandomStringUtils.randomAlphanumeric(10);

        return generatedString;
    }

    @Test
    void testHomework01() throws InterruptedException {
        authorization();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement buttonPlus = driver.findElement(By.cssSelector("#create-btn"));
        buttonPlus.click();

        WebElement nameGroup = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mdc-text-field .mdc-text-field__input")));
        nameGroup.sendKeys(generateGroupName());

        WebElement buttonSave = driver.findElement(By.cssSelector("#update-item > div.submit > button"));
        buttonSave.click();

        Thread.sleep(5000);
        WebElement titleText = driver.findElement(By.cssSelector("#app > main > div > div > div.mdc-data-table > div.mdc-data-table__table-container > table > tbody > tr:nth-child(1) > td:nth-child(2)"));
        Assertions.assertEquals(generatedString, titleText.getText());


        screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src/main/resources/screen01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void close() {
        driver.quit();

    }


}