import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("smoke")
public class WBTests extends TestBase {
    public final String url = "https://www.wildberries.ru/";
    public final String phoneNumberUnregistration = "79200127844";
    public final String phoneNumberRegistered = "79200127841";
    public final String crashPhoneNumber = phoneNumberUnregistration.substring(0, phoneNumberUnregistration.length() - 1);
    public final String variableForSearch = "цепочка";
    public final String article = "56236410";

    @DisplayName("Проверка регистрации по номеру телефона")
    @Test
    void testPositiveRegistrationWithUnregistrationNumber() {
        open(url);
        sleep(6000);
        step("Открываем форму авторизации", () -> {
            $("[data-wba-header-name=Login]").click();
            $("#spaAuthForm").shouldHave(text("Войти или создать профиль"));
        });
        step("Заполняем форму авторизации", () -> {
            $("[inputmode=tel]").click();
            $("[inputmode=tel]").setValue(phoneNumberUnregistration);
            $("#requestCode").click();
        });
        step("Проверяем данные в форме", () -> {
            $("#spaAuthForm").shouldHave(text("Введите код из СМС"));
            $("#spaAuthForm").shouldHave(text("Отправили на +7 920 012-78-44"));
        });
    }

    @DisplayName("Проверка на регистрацию с невалидным номером телефона")
    @Test
    void testNegativeRegistrationWithCrashNumber() {
        open(url);
        sleep(6000);
        step("Открываем форму авторизации", () -> {
            $("[data-wba-header-name=Login]").click();
            $("#spaAuthForm").shouldHave(text("Войти или создать профиль"));
        });
        step("Заполняем форму авторизации", () -> {
            $("[inputmode=tel]").click();
            $("[inputmode=tel]").setValue(crashPhoneNumber);
            $("#requestCode").click();
        });
        step("Проверяем цвет бордера инпута", () -> {
            $("[inputmode=tel]").getCssValue("rgb(255, 68, 68)");
        });

    }

    @DisplayName("Проверка регистрации по уже зарегистрированному телефону")
    @Test
    void testPositiveRegistrationWithRegistredNumber() {
        open(url);
        sleep(6000);
        step("Открываем форму авторизации", () -> {
            $("[data-wba-header-name=Login]").click();
            $("#spaAuthForm").shouldHave(text("Войти или создать профиль"));
        });
        step("Заполняем форму авторизации", () -> {
            $("[inputmode=tel]").click();
            $("[inputmode=tel]").setValue(phoneNumberRegistered);
            $("#requestCode").click();
        });
        step("Проверяем данные в форме", () -> {
            $("#spaAuthForm").shouldHave(text("Откройте уведомление в приложении Wildberries"));
        });
    }

    @DisplayName("Проверка работы поиска товаров")
    @Test
    void testSearch() {
        open(url);
        sleep(6000);
        step("Вводим данные для поиска", () -> {
            $("#searchInput").setValue(variableForSearch);
            $("#applySearchBtn").click();
        });
        step("Проверяем найденный товар", () -> {
            $("#mainContainer").shouldHave(text(variableForSearch));
        });
    }

    @DisplayName("Проверка корректности описания карточки товара")
    @Test
    void testSearchCharactersProductCard() {
        open(url);
        sleep(6000);
        step("Находим товар по артиклу", () -> {
            $("#searchInput").setValue(article).pressEnter();
            $(".j-details-btn-desktop").click();
        });
        step("Проверяем наличие в карточке характеристик и описания", () -> {
            $(".popup__content").shouldHave(text("Характеристики и описание"));
        });
    }

    @DisplayName("Проверка состояния продуктовой корзины")
    @Test
    void testEmptyShoppingCart() {
        open(url);
        sleep(3000);
        step("Переходим в корзину", () -> {
            $(byText("Корзина")).click();
        });
        step("Проверяем состояние корзины", () -> {
            $("#mainContainer").shouldHave(text("В корзине пока пусто"));
        });
    }

    @DisplayName("Проверка состояния продуктовой корзины2")
    @Test
    void testEmptyShoppingCart2() {
        open(url);
        sleep(3000);
        step("Переходим в корзину", () -> {
            $("[data-wba-header-name=Cart]").click();
        });
        step("Проверяем состояние корзины", () -> {
            $("#mainContainer").shouldHave(text("В корзине пока пусто"));
        });
    }

}
