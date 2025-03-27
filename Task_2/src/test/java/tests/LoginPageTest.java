package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPageTest {
    LoginPage loginPage = new LoginPage();

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
    }

    @Test
    void fieldsVisible() {
        loginPage.openPage();

        loginPage.getEmailField().shouldBe(visible);
        loginPage.getPasswordField().shouldBe(visible);
    }

    @Test
    void invalidLogin() {
        loginPage.openPage()
                .typeEmail("invalid_user@example.com")
                .typePassword("wrongPassword123")
                .clickLogin();

        $x("//*[contains(@id, 'tabpanel-login')]/form/div[3]/div")
                .shouldBe(visible)
                .shouldHave(text("Неправильно указан логин и/или пароль"));
    }

    @Test
    void emptyFields() {
        loginPage.openPage()
                .clickLogin();

        $x("//*[contains(@id, 'tabpanel-login')]/form/div[3]/div")
                .shouldBe(visible)
                .shouldHave(text("Введите логин"));
    }

    @Test
    void emptyFieldPassword() {
        loginPage.openPage()
                .typeEmail("some_email@example.com")
                .clickLogin();

        $x("//*[contains(@id, 'tabpanel-login')]/form/div[3]/div")
                .shouldBe(visible)
                .shouldHave(text("Введите пароль"));
    }
}
