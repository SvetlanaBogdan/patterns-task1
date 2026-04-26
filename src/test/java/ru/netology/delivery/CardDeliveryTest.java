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
        //headless НЕ задаём — он придёт из CI
    }

    @Test
    void shouldReplanMeetingWithConfirmation() {

        open("/");


        var user = DataGenerator.generateUser();

        String firstDate = DataGenerator.generateDate(3);


        $("[data-test-id='city'] input").setValue(user.getCity());

        $("[data-test-id='date'] input")
                .doubleClick()
                .sendKeys(firstDate);

        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhone());

        $("[data-test-id='agreement']").click();


        $$("button.button")
                .findBy(text("Запланировать"))
                .click();


        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + firstDate));

        //
        String secondDate = DataGenerator.generateDate(4);

        $("[data-test-id='date'] input")
                .doubleClick()
                .sendKeys(secondDate);

        $$("button.button")
                .findBy(text("Запланировать"))
                .click();


        $("[data-test-id='replan-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));


        $$("button.button")
                .findBy(text("Перепланировать"))
                .shouldBe(visible, Duration.ofSeconds(15))
                .click();


        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + secondDate));
    }
}