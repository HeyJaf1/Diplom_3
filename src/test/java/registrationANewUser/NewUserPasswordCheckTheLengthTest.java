package registrationANewUser;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page_object_models.PersonalCabinet;

import static credentials.Constants.BASE_URL;
import static org.junit.Assert.assertEquals;

import static credentials.Constants.LOGIN_USER_URL;

@RunWith(Parameterized.class)
public class NewUserPasswordCheckTheLengthTest {
    private PersonalCabinet personalCabinet;
    private WebDriver driver;
    private final String attempt;
    private String expected;
    public By invalidPasswordMessage = By.xpath(".//p[text()='Некорректный пароль']"); // сообщение "Некорректный пароль"

    public NewUserPasswordCheckTheLengthTest(String attempt, String expected) {
        this.attempt = attempt;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"1J89k", "Некорректный пароль"},
                {"1k", "Некорректный пароль"},
                {"1234Kl", LOGIN_USER_URL},
        };
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    @DisplayName("Тест на проверку длины пароля.")
    @Description("Проверка, что пароль НЕ менее 6 символов. В тестовых данных присутствуют варианты с паролем менее 6 символов - эти тесты - падающие.")
    public void shouldCheckThePasswordLength() {
        personalCabinet = new PersonalCabinet(driver);
        personalCabinet.goToPersonalCabinetPage();
        personalCabinet.waitForRegistrationPage();
        personalCabinet.nameFieldFillingToRegisterAUser();
        personalCabinet.emailFieldFillingToRegisterAUser();
        personalCabinet.passwordLengthCheck(attempt);
        personalCabinet.registrationButtonClick();

        if (expected.equals("Некорректный пароль")) {
            personalCabinet.isErrorWaitForEnterPage();
            assertEquals(expected, driver.findElement(invalidPasswordMessage).getText());
        } else {
            personalCabinet.waitForEnterPage();
            assertEquals(expected, driver.getCurrentUrl());
        }
    }

    @After
    public void tearDown() {
        driver.quit();
        personalCabinet.userDelete();
    }
}