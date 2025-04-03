package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class LoginPageTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
    }

    @Test
    void fieldsVisibleTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.openPage();

        loginPage.getEmailField().shouldBe(visible);
        loginPage.getPasswordField().shouldBe(visible);
    }

    @Test
    void invalidLoginTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.openPage()
                .typeEmail("invalid_user@example.com")
                .typePassword("wrongPassword123")
                .clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible)
                .shouldHave(text("Неправильно указан логин и/или пароль"));
    }

    @Test
    void emptyFieldsTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.openPage()
                .clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible)
                .shouldHave(text("Введите логин"));
    }

    @Test
    void emptyFieldPasswordTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.openPage()
                .typeEmail("some_email@example.com")
                .clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible)
                .shouldHave(text("Введите пароль"));
    }
}
