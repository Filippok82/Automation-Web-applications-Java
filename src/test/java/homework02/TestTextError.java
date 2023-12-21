package homework02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestTextError {


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

    @Test
    void testNoValidAuthorisation(){
        LoginPage loginPage = new LoginPage(driver,wait);
        loginPage.checkButtonNoAuthorisation();
        MainPage mainPage = new MainPage(driver,wait);
        Assertions.assertEquals("Invalid credentials.", mainPage.getTextError());
    }
    @AfterAll
    static void close() {
        driver.quit();

    }

}
