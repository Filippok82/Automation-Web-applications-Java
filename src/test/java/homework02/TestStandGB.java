package homework02;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestStandGB {
    private final String userName = "Student-5";
    private final String password = "97d2434151";
    private static RemoteWebDriver driver;
    private static WebDriverWait wait;


    @BeforeEach
    void initialization() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");


    }

    //@BeforeEach //Selenium
////   void open() {
////
////        driver = new ChromeDriver();
////        driver.manage().window().maximize();
////        driver.get("https://test-stand.gb.ru/login");
////        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
////    }
    @BeforeAll // запуска тестов из Selenoid
    static void open() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("browserName", "chrome");
        selenoidOptions.put("browserVersion", "119");
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableLog", true);
        options.setCapability("selenoid:options", selenoidOptions);
        options.setScriptTimeout(Duration.ofSeconds(30));
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver.get("https://test-stand.gb.ru/login");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }


    @Test
    void testTitleGroup() {
        String nameGroupText = "New name group" + System.currentTimeMillis();
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.authorization(userName, password);

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.successCreatingNewGroup(nameGroupText);
        Assertions.assertEquals("active", mainPage.getStatusRow(nameGroupText));

        mainPage.successDelete(nameGroupText);
        Assertions.assertEquals("inactive", mainPage.getStatusRow(nameGroupText));

        mainPage.successRestore(nameGroupText);
        Assertions.assertEquals("active", mainPage.getStatusRow(nameGroupText));


        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src/main/resources/screen01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testCheckNumberStudentsInGroup() {
        String count = "3";
        String nameGroupText = "New name group" + System.currentTimeMillis();
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.authorization(userName, password);
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.successCreatingNewGroup(nameGroupText);
        mainPage.addingNumbersStudent(count);
        Assertions.assertTrue(mainPage.checkIconCount(count));
        mainPage.clickIconMagnifier();
        String name = mainPage.nameStudent();
        assertEquals("active", mainPage.getStatusStudent(name));
        mainPage.successDeleteStudent(name);
        assertEquals("block", mainPage.getStatusStudent(name));
        mainPage.successRestoreStudent(name);
        assertEquals("active", mainPage.getStatusStudent(name));

    }

    @AfterAll
    static void close() {
//        driver.quit();
        WebDriverRunner.closeWebDriver();
    }
}
