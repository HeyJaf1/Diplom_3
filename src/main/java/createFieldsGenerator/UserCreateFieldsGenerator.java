package createFieldsGenerator;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import userCreate.UserCreate;

import java.security.SecureRandom;
import java.util.Random;


public class UserCreateFieldsGenerator {

    static final String[] emails = {
            "@yandex.ru", "@gmail.com", "@yahoo.com", "@mail.ru",
            "@ya.ru", "@hotbox.com", "@rambler.ru", "@list.ru", "@bk.ru",
            "@postbox.com"
    };

    public static String email() {

        return RandomStringUtils.random(new Random().nextInt(10) + 1,
                true, false).toLowerCase() + emails[new Random().nextInt(emails.length)];
    }

    public static String password() {
        int max = 12;
        int min = 6;
        return RandomStringUtils.random(new SecureRandom().nextInt(max - min) + min, false, true);
    }

    public static String passwordInvalid() {
        int max = 5;
        int min = 1;
        return RandomStringUtils.random(new SecureRandom().nextInt(max - min) + min, false, true);
    }

    public static String name() {
        Random name = new Random();
        int length = name.nextInt(10) + 1;
        return RandomStringUtils.random(length, true, false);
    }

    @Step("Создание дефолтного пользователя через API.")
    public static UserCreate passingGeneratorDataViaApi() {
        return new UserCreate().setEmail("bbbbb@yahoo.com").setPassword("123456Hgd").setName("bbbb");
    }

    public static UserCreate passingGeneratorNewEmail() {
        return new UserCreate().setEmail(email());
    }

    public static UserCreate passingGeneratorNewPassword() {
        return new UserCreate().setPassword(password());
    }

    public static UserCreate passingGeneratorNewName() {
        return new UserCreate().setName(name());
    }

    public static UserCreate defaultUserName() {
        return new UserCreate().setName("bbbb");
    }

    public static UserCreate defaultUserEmail() {
        return new UserCreate().setEmail("bbbbb@yahoo.com");
    }

    public static UserCreate defaultUserPassword() {
        return new UserCreate().setPassword("123456Hgd");
    }
}