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
        Configuration.baseUrl = "http://localhost:9999";
        // headless не задаём — он придёт из CI
    }

    @Test
    void shouldReplanMeeting() {
        open("/");

        var user = DataGenerator.generateUser();
        String firstDate = DataGenerator.generateDate(3);
        String secondDate = DataGenerator.generateDate(5);

        // ввод города
        $("[data-test-id='city'] input").setValue(user.getCity());

        // первая дата
        $("[data-test-id='date'] input")
                .doubleClick()
                .sendKeys(firstDate);

        // имя и телефон
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());

        // согласие
        $("[data-test-id='agreement']").click();

        // кнопка запланировать
        $$("button.button")
                .findBy(text("Запланировать"))
                .click();

        // проверка первой даты
        $(".notification")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + firstDate));

        // ВТОРАЯ ДАТА (перепланирование)
        $("[data-test-id='date'] input")
                .doubleClick()
                .sendKeys(secondDate);

        $$("button.button")
                .findBy(text("Запланировать"))
                .click();

        // кнопка перепланировать
        $$("button.button")
                .findBy(text("Перепланировать"))
                .click();

        // проверка второй даты
        $(".notification")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + secondDate));
    }
}