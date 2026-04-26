package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private static Faker faker = new Faker(new Locale("ru"));

    // генерируем ТОЛЬКО валидные города
    public static String generateCity() {
        String[] cities = {"Москва", "Санкт-Петербург", "Казань", "Новосибирск"};
        int index = faker.number().numberBetween(0, cities.length);
        return cities[index];
    }

    // имя
    public static String generateName() {
        return faker.name().fullName();
    }

    // телефон
    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    // дата
    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // пользователь (собираем всё вместе)
    public static UserInfo generateUser() {
        return new UserInfo(
                generateCity(),
                generateName(),
                generatePhone()
        );
    }
}