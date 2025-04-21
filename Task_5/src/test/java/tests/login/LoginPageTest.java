package tests.login;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты страницы входа")
public class LoginPageTest {
    private LoginPage loginPage;

    private static final String VALID_EMAIL = "technopol32";
    private static final String VALID_PASSWORD = "technopolisPassword";

    private static final String INVALID_EMAIL = "invalid_user@example.com";
    private static final String INVALID_PASSWORD = "wrongPassword123";

    private static final String EXPECTED_ERROR_INVALID_LOGIN = "Неправильно указан логин и/или пароль";
    private static final String EXPECTED_ERROR_EMPTY_LOGIN = "Введите логин";
    private static final String EXPECTED_ERROR_EMPTY_PASSWORD = "Введите пароль";

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
    }

    @BeforeEach
    void openPage() {
        open("https://ok.ru/");
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Поля логина и пароля отображаются")
    void fieldsVisibleTest() {
        assertAll("Проверка отображения полей логина и пароля",
                () -> Assertions.assertTrue(loginPage.isEmailFieldVisible(), "Поле логина не отображается"),
                () -> Assertions.assertTrue(loginPage.isPasswordFieldVisible(), "Поле пароля не отображается")
        );
    }

    @Nested
    @Tag("negative")
    @DisplayName("Негативные сценарии авторизации")
    class NegativeLoginTests {

        @Test
        @DisplayName("Неверный логин и пароль")
        void invalidLoginTest() {
            loginPage.typeEmail(INVALID_EMAIL)
                    .typePassword(INVALID_PASSWORD)
                    .clickLogin();

            String actualError = loginPage.getLoginErrorText();
            assertEquals(EXPECTED_ERROR_INVALID_LOGIN, actualError,
                    "Сообщение об ошибке некорректного логина не совпадает с ожидаемым");
        }

        @Test
        @DisplayName("Поля логина и пароля пустые")
        void emptyFieldsTest() {
            loginPage.clickLogin();

            String actualError = loginPage.getLoginErrorText();
            assertEquals(EXPECTED_ERROR_EMPTY_LOGIN, actualError,
                    "Сообщение об ошибке при пустом логине не совпадает с ожидаемым");
        }

        @Test
        @DisplayName("Пустой пароль")
        void emptyFieldPasswordTest() {
            loginPage.typeEmail(INVALID_EMAIL)
                    .clickLogin();

            String actualError = loginPage.getLoginErrorText();
            assertEquals(EXPECTED_ERROR_EMPTY_PASSWORD, actualError,
                    "Сообщение об ошибке при пустом пароле не совпадает с ожидаемым");
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
            loginPage.typeEmail(VALID_EMAIL)
                    .typePassword(VALID_PASSWORD)
                    .clickLogin();

            new HomePage().getLeftSidebarAvatar()
                    .shouldBe(visible.because("Не появился аватар на левой панели после авторизации"));
        }
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}