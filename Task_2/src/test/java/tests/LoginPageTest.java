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
        loginPage.getEmailField().shouldBe(visible.because("Не отображается поле ввода логина"));
        loginPage.getPasswordField().shouldBe(visible.because("Не отображается поле ввода пароля"));
    }

    @Test
    void invalidLoginTest() {
        loginPage.typeEmail("invalid_user@example.com")
                .typePassword("wrongPassword123")
                .clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible.because("Не отображается предупреждение о некорректных введенных данных"))
                .shouldHave(text("Неправильно указан логин и/или пароль"));
    }

    @Test
    void emptyFieldsTest() {
        loginPage.clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible.because("Не отображается предупреждение о вводе логина"))
                .shouldHave(text("Введите логин"));
    }

    @Test
    void emptyFieldPasswordTest() {
        loginPage.typeEmail("some_email@example.com")
                .clickLogin();

        loginPage.getLoginError()
                .shouldBe(visible.because("Не отображается предупреждение о вводе пароля"))
                .shouldHave(text("Введите пароль"));
    }
}
