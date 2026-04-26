package ru.netology.delivery.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {}

    private static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static  UserInfo generateUser() {
        return new UserInfo(
                faker.address().city(),
                faker.name().fullName(),
                "+7" + faker.number().digits(10)
        );
    }
}