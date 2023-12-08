package lesson01;


import org.apache.commons.io.FileUtils;
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
    private static WebDriver driver;
    private static WebDriverWait wait;


    @BeforeAll
    static void creation() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://test-stand.gb.ru/login");


    }


    void authorization() {
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


    @Test
    void testTitleGroup() {
        authorization();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement buttonPlus = driver.findElement(By.cssSelector("#create-btn"));
        buttonPlus.click();
        String nameGroupText = "New name group" + System.currentTimeMillis();
        WebElement nameGroupField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mdc-text-field .mdc-text-field__input")));
        nameGroupField.sendKeys(nameGroupText);

        WebElement buttonSaveGroup = driver.findElement(By.cssSelector("#update-item > div.submit > button"));
        buttonSaveGroup.click();

        String tableTitleXpath = "//table[@aria-label='Tutors list']//tbody/tr/td[text()='%s']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(tableTitleXpath, nameGroupText))));


        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
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