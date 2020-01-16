package ru.valerykolod.topjava.lunchVotingSystem;

import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.readFromJsonMvcResult;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.readListFromJsonMvcResult;
import static ru.valerykolod.topjava.lunchVotingSystem.model.AbstractEntity.START_SEQ;

public class RestaurantTestData {

    public static final int RESTAURANT_ID = START_SEQ + 6;
    public static final int DISH_ID = START_SEQ + 11;
    public static final LocalDate MAY_30_2015 = LocalDate.of(2015, 5, 30);
    public static final LocalDate MAY_31_2015 = LocalDate.of(2015, 5, 31);

    public static final String R1_NAME = "Dinner In The Sky";
    public static final String R2_NAME = "Funky Gourmet";
    public static final String R3_NAME = "Scala Vinoteca";

    public static final Dish DISH_R1_1 = new Dish(DISH_ID, "Soup-1", 100);
    public static final Dish DISH_R1_2 = new Dish(DISH_ID + 1, "Salad-1", 100);
    public static final Dish DISH_R1_3 = new Dish(DISH_ID + 2, "Main course-1", 100);
    public static final Dish DISH_R1_4 = new Dish(DISH_ID + 3, "Desert-1", 100);
    public static final List DISHES_R1 = new ArrayList(Arrays.asList(DISH_R1_1, DISH_R1_2, DISH_R1_3, DISH_R1_4));
    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID, R1_NAME, MAY_30_2015, DISHES_R1);

    public static final Dish DISH_R2_1 = new Dish(DISH_ID + 4, "Soup-2", 100);
    public static final Dish DISH_R2_2 = new Dish(DISH_ID + 5, "Salad-2", 100);
    public static final Dish DISH_R2_3 = new Dish(DISH_ID + 6, "Main course-2", 100);
    public static final Dish DISH_R2_4 = new Dish(DISH_ID + 7, "Desert-2", 100);
    public static final List DISHES_R2 = new ArrayList(Arrays.asList(DISH_R2_1, DISH_R2_2, DISH_R2_3, DISH_R2_4));
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID + 1, R2_NAME, MAY_30_2015, DISHES_R2);

    public static final Dish DISH_R3_1 = new Dish(DISH_ID + 8, "Soup-3", 100);
    public static final Dish DISH_R3_2 = new Dish(DISH_ID + 9, "Salad-3", 100);
    public static final Dish DISH_R3_3 = new Dish(DISH_ID + 10, "Main course-3", 100);
    public static final Dish DISH_R3_4 = new Dish(DISH_ID + 11, "Desert-3", 100);
    public static final List DISHES_R3 = new ArrayList<>(Arrays.asList(DISH_R3_1, DISH_R3_2, DISH_R3_3, DISH_R3_4));
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID + 2, R3_NAME, MAY_30_2015, DISHES_R3);

    public static final Dish DISH_R4_1 = new Dish(DISH_ID + 12, "Soup-4", 100);
    public static final Dish DISH_R4_2 = new Dish(DISH_ID + 13, "Salad-4", 100);
    public static final Dish DISH_R4_3 = new Dish(DISH_ID + 14, "Main course-4", 100);
    public static final Dish DISH_R4_4 = new Dish(DISH_ID + 15, "Desert-4", 100);
    public static final List DISHES_R4 = new ArrayList(Arrays.asList(DISH_R4_1, DISH_R4_2, DISH_R4_3, DISH_R4_4));
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_ID + 3, R2_NAME, MAY_31_2015, DISHES_R4);

    public static final Dish DISH_R5_1 = new Dish(DISH_ID + 16, "Soup-5", 100);
    public static final Dish DISH_R5_2 = new Dish(DISH_ID + 17, "Salad-5", 100);
    public static final Dish DISH_R5_3 = new Dish(DISH_ID + 18, "Main course-5", 100);
    public static final Dish DISH_R5_4 = new Dish(DISH_ID + 19, "Desert-5", 100);
    public static final List DISHES_R5 = new ArrayList(Arrays.asList(DISH_R5_1, DISH_R5_2, DISH_R5_3, DISH_R5_4));
    public static final Restaurant RESTAURANT_5 = new Restaurant(RESTAURANT_ID + 4, R3_NAME, MAY_31_2015, DISHES_R5);

    public static void assertMatchRestaurants(Restaurant actual, Restaurant expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes");
    }

    public static void assertMatchRestaurants(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatchRestaurants(actual, List.of(expected));
    }

    public static void assertMatchRestaurants(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("dishes").isEqualTo(expected);
    }

    public static void assertMatchDishes(Dish actual, Dish expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatchDishes(Iterable<Dish> actual, Dish... expected) {
        assertMatchDishes(actual, List.of(expected));
    }

    public static void assertMatchDishes(Iterable<Dish> actual, Iterable<Dish> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant... expected) {
        return result -> assertMatchRestaurants(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant expected) {
        return result -> assertMatchRestaurants(readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getDishMatcher(Dish... expected) {
        return result -> assertMatchDishes(readListFromJsonMvcResult(result, Dish.class), List.of(expected));
    }

    public static ResultMatcher getDishMatcher(Dish expected) {
        return result -> assertMatchDishes(readFromJsonMvcResult(result, Dish.class), expected);
    }

}
