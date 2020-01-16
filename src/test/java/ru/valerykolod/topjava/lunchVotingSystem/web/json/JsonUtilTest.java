package ru.valerykolod.topjava.lunchVotingSystem.web.json;

import org.junit.jupiter.api.Test;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;
import ru.valerykolod.topjava.lunchVotingSystem.model.User;

import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.RestaurantTestData.DISHES_R1;
import static ru.valerykolod.topjava.lunchVotingSystem.RestaurantTestData.assertMatchDishes;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.USER_1;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.assertMatchUsers;

class JsonUtilTest {

   @Test
    void testReadWriteValue() {
        String json = JsonUtil.writeValue(USER_1);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
       assertMatchUsers(user, USER_1);
    }

    @Test
    void testReadWriteValues() {
        String json = JsonUtil.writeValue(DISHES_R1);
        System.out.println(json);
        List<Dish> dishes = JsonUtil.readValues(json, Dish.class);
        assertMatchDishes(dishes, DISHES_R1);
    }
}