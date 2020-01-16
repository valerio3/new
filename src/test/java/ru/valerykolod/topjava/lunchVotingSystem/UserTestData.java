package ru.valerykolod.topjava.lunchVotingSystem;

import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.valerykolod.topjava.lunchVotingSystem.model.Role;
import ru.valerykolod.topjava.lunchVotingSystem.model.User;
import ru.valerykolod.topjava.lunchVotingSystem.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.readFromJsonMvcResult;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.readListFromJsonMvcResult;
import static ru.valerykolod.topjava.lunchVotingSystem.model.AbstractEntity.START_SEQ;

public class UserTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 5;

    public static final User USER_1 = new User(USER_ID, "User_1", "user-1@yandex.ru", "password1",
            Role.ROLE_USER, LocalDateTime.of(2019, 01, 01, 10, 00, 00));
    public static final User USER_2 = new User(USER_ID + 1, "User_2", "user-2@yandex.ru", "password2",
            Role.ROLE_USER, LocalDateTime.of(2019, 01, 01, 10, 00, 00));
    public static final User USER_3 = new User(USER_ID + 2, "User_3", "user-3@yandex.ru", "password3",
            Role.ROLE_USER, LocalDateTime.of(2019, 01, 01, 10, 00, 00));
    public static final User USER_4 = new User(USER_ID + 3, "User_4", "user-4@yandex.ru", "password4",
            Role.ROLE_USER, LocalDateTime.of(2019, 01, 01, 10, 00, 00));
    public static final User USER_5 = new User(USER_ID + 4, "User_5", "user-5@yandex.ru", "password5",
            Role.ROLE_USER, LocalDateTime.of(2019, 01, 01, 10, 00, 00));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin",
            Role.ROLE_ADMIN, LocalDateTime.of(2019, 01, 01, 10, 00, 00));

    public static List<User> allUsers() {
        return Arrays.asList(USER_1, USER_2, USER_3, USER_4, USER_5, ADMIN);
    }

    public static void assertMatchUsers(User actual, User expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    public static void assertMatchUsers(Iterable<User> actual, User... expected) {
        assertMatchUsers(actual, List.of(expected));
    }

    public static void assertMatchUsers(Iterable<User> actual, Iterable<User> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("registered", "password").isEqualTo(expected);
    }

    public static ResultMatcher getUserMatcher(User... expected) {
        return result -> assertMatchUsers(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher getUserMatcher(User expected) {
        return result -> assertMatchUsers(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }

}

