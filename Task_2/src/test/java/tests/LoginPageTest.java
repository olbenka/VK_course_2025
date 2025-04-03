package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class LoginPageTest {
    private LoginPage loginPage;
    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
    }

    @BeforeEach
    void openPage() {
        loginPage = new LoginPage();
        loginPage.openPage();
    }

    @Test
    void fieldsVisibleTest() {
        loginPage.getEmailField().shouldBe(visible);
        loginPage.getPasswordField().shouldBe(visible);
    }

    @Test
    void invalidLoginTest() {
        loginPage.typeEmail("invalid_user@example.com")
                .typePassword("wrongPassword123")
                .clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible)
                .shouldHave(text("Неправильно указан логин и/или пароль"));
    }

    @Test
    void emptyFieldsTest() {
        loginPage.clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible)
                .shouldHave(text("Введите логин"));
    }

    @Test
    void emptyFieldPasswordTest() {
        loginPage.typeEmail("some_email@example.com")
                .clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible)
                .shouldHave(text("Введите пароль"));
    }
}
