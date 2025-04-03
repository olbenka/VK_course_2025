package tests.login;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.closeWebDriver;

@DisplayName("Тесты страницы входа")
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
    @DisplayName("Поля логина и пароля отображаются")
    void fieldsVisibleTest() {
        loginPage.getEmailField().shouldBe(visible.because("Не отображается поле ввода логина"));
        loginPage.getPasswordField().shouldBe(visible.because("Не отображается поле ввода пароля"));
    }

    @Nested
    @Tag("negative")
    @DisplayName("Негативные сценарии авторизации")
    class NegativeLoginTests {

        @Test
        @DisplayName("Неверный логин и пароль")
        void invalidLoginTest() {
            loginPage.typeEmail("invalid_user@example.com")
                    .typePassword("wrongPassword123")
                    .clickLogin();

            loginPage.getLoginError()
                    .shouldBe(visible.because("Не отображается предупреждение о некорректных введенных данных"))
                    .shouldHave(text("Неправильно указан логин и/или пароль"));
        }

        @Test
        @DisplayName("Поля логина и пароля пустые")
        void emptyFieldsTest() {
            loginPage.clickLogin();

            loginPage.getLoginError()
                    .shouldBe(visible.because("Не отображается предупреждение о вводе логина"))
                    .shouldHave(text("Введите логин"));
        }

        @Test
        @DisplayName("Пустой пароль")
        void emptyFieldPasswordTest() {
            loginPage.typeEmail("some_email@example.com")
                    .clickLogin();

            loginPage.getLoginError()
                    .shouldBe(visible.because("Не отображается предупреждение о вводе пароля"))
                    .shouldHave(text("Введите пароль"));
        }
    }

    @Nested
    @Tag("positive")
    @DisplayName("Позитивные сценарии авторизации")
    class PositiveLoginTests {

        @Test
        //@Disabled("Отключено из-за капчи")
        @DisplayName("Успешная авторизация через кнопку 'Войти в Одноклассники'")
        void successfulLoginWithButtonTest() {
            loginPage.typeEmail("technopol32")
                    .typePassword("technopolisPassword")
                    .clickLogin();

            new HomePage().getLeftSidebarAvatar()
                    .shouldBe(visible.because("Не появился аватар на левой панели после авторизации"));
        }

        @Test
        //@Disabled("Отключено из-за капчи")
        @DisplayName("Успешная авторизация с нажатием клавиши Enter")
        void successfulLoginWithEnterTest() {
            loginPage.typeEmail("technopol32")
                    .typePassword("technopolisPassword")
                    .getPasswordField()
                    .pressEnter();

            new HomePage().getLeftSidebarAvatar()
                    .shouldBe(visible.because("Не появился аватар на левой панели после авторизации"));
        }
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
