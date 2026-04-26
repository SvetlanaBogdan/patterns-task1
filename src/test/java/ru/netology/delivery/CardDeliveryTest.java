package ru.netology.delivery;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import ru.netology.delivery.data.DataGenerator;

public class CardDeliveryTest {

    @BeforeAll
    static void setup() {
        Configuration.headless = false; // чтобы видеть браузер
        Configuration.baseUrl = "http://localhost:9999";
    }

    @Test
    void shouldReplanMeetingWithConfirmation() {

        open("/");

        // один пользователь (по ДЗ обязательно)
        var user = DataGenerator.generateUser();

        String firstDate = DataGenerator.generateDate(3);

        // Первая отправка
        $("[data-test-id='city'] input").setValue(user.getCity());

        $("[data-test-id='date'] input")
                .doubleClick()
                .sendKeys(firstDate);

        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());

        $("[data-test-id='agreement']").click();
        $$("button.button").findBy(text("Запланировать")).click();
        // проверка успеха
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + firstDate));

        // Меняем только дату
        String secondDate = DataGenerator.generateDate(4);

        $("[data-test-id='date'] input")
                .doubleClick()
                .sendKeys(secondDate);

        $$("button.button").findBy(text("Запланировать")).click();

        // Всплывающее окно подтверждения
        $("[data-test-id='replan-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));

        // кнопка "Перепланировать"
        $$("button.button")
                .findBy(text("Перепланировать"))
                .shouldBe(visible, Duration.ofSeconds(15))
                .click();

        // Финальный результат с перепланированием
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + secondDate));
    }
}
