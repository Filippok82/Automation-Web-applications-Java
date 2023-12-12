package lesson02;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;


public class TestAddGroup {

    private String userName = "Student-5";
    private String password = "97d2434151";
    private static WebDriver driver;
    private static WebDriverWait wait;


    @BeforeAll
    static void initialization() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");


    }

    @BeforeEach
    void open(){

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://test-stand.gb.ru/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private void validAuthorization(){
        LoginPage loginPage = new LoginPage(driver,wait);
        loginPage.authorization(userName,password);
        loginPage.checkButtonInvisible();
        MainPage mainPage = new MainPage(driver,wait);
        Assertions.assertEquals(("Hello, "+userName), mainPage.getHelloUser());
    }



    private void testTitleGroup(String nameGroupText) {
        validAuthorization();
//        String nameGroupText = "New name group" + System.currentTimeMillis();
        MainPage mainPage = new MainPage(driver,wait);
        mainPage.successCreatingNewGroup(nameGroupText);

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src/main/resources/screen01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testStatusGroup(){
        String nameGroupText = "New name group" + System.currentTimeMillis();
        testTitleGroup(nameGroupText);

        MainPage mainPage = new MainPage(driver,wait);
        Assertions.assertEquals("active",mainPage.getStatusRow(nameGroupText));

        mainPage.successDelete(nameGroupText);
        Assertions.assertEquals("inactive",mainPage.getStatusRow(nameGroupText));

        mainPage.successRestore(nameGroupText);
        Assertions.assertEquals("active",mainPage.getStatusRow(nameGroupText));


    }
    @AfterAll
    static void close() {
        driver.quit();

    }


}