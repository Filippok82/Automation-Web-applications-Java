package homework02;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestStandGB {
    private String userName = "Student-5";
    private String password = "97d2434151";
    private static WebDriver driver;
    private static WebDriverWait wait;


    @BeforeAll
    static void initialization() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");


    }

    @BeforeEach
    void open() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://test-stand.gb.ru/login");
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
    void testCheckNumberStudentsInGroup()  {
        String count = "3";
        String nameGroupText = "New name group" + System.currentTimeMillis();
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.authorization(userName, password);
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.successCreatingNewGroup(nameGroupText);
        mainPage.addingNumbersStudent(count);
        Assertions.assertTrue(mainPage.checkIconCount(count));
        mainPage.clickIconMagnifier();
        String  name = mainPage.nameStudent();
        assertEquals("active",mainPage.getStatusStudent(name));
        mainPage.successDeleteStudent(name);
        assertEquals("block", mainPage.getStatusStudent(name));
        mainPage.successRestoreStudent(name);
        assertEquals("active", mainPage.getStatusStudent(name));

    }

    @AfterAll
    static void close() {
        driver.quit();

    }
}
