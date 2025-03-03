import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@Tag("smoke")
public class WBTests extends TestBase {
 public final String url = "https://www.wildberries.ru/";
 public final String phoneNumberUnregistration = "79200127844";
 public final String phoneNumberRegistered = "79200127841";
 public final String crashPhoneNumber = phoneNumberUnregistration.substring(0, phoneNumberUnregistration.length() - 1);
 public final String variableForSearch = "цепочка";
 public final String article = "56236410";
 @Test
 void testPositiveRegistrationWithUnregistrationNumber() {
 open(url);
 sleep(3000);
 $("[data-wba-header-name=Login]").click();
 $("#spaAuthForm").shouldHave(text("Войти или создать профиль"));
 $("[inputmode=tel]").click();
 $("[inputmode=tel]").setValue(phoneNumberUnregistration);
 $("#requestCode").click();
 $("#spaAuthForm").shouldHave(text("Введите код из СМС"));
 $("#spaAuthForm").shouldHave(text("Отправили на +7 920 012-78-44"));

}
 @Test
 void testNegativeRegistrationWithCrashNumber() {
  open(url);
  sleep(3000);
  $("[data-wba-header-name=Login]").click();
  $("#spaAuthForm").shouldHave(text("Войти или создать профиль"));
  $("[inputmode=tel]").click();
  $("[inputmode=tel]").setValue(crashPhoneNumber);
  $("#requestCode").click();
  $("[inputmode=tel]").getCssValue("rgb(255, 68, 68)");


 }
 @Test
 void testPositiveRegistrationWithRegistredNumber() {
  open(url);
  sleep(3000);
  $("[data-wba-header-name=Login]").click();
  $("#spaAuthForm").shouldHave(text("Войти или создать профиль"));
  $("[inputmode=tel]").click();
  $("[inputmode=tel]").setValue(phoneNumberRegistered);
  $("#requestCode").click();
  $("#spaAuthForm").shouldHave(text("Откройте уведомление в приложении Wildberries"));
 }
 @Test
 void testSearch() {
  open(url);
  sleep(3000);
  $("#searchInput").setValue(variableForSearch);
  $("#applySearchBtn").click();
  $("#mainContainer").shouldHave(text(variableForSearch));
 }

 @Test
 void testSearchCharactersProductCard() {
  open(url);
  sleep(3000);
  $("#searchInput").setValue(article).pressEnter();
  $(".j-details-btn-desktop").click();
  $(".popup__content").shouldHave(text("Характеристики и описание"));
 }
 @Test
 void testEmptyShoppingCart() {
  open(url);
  sleep(3000);
  $(byText("Корзина")).click();
  $("#mainContainer").shouldHave(text("В корзине пока пусто"));

 }


}
