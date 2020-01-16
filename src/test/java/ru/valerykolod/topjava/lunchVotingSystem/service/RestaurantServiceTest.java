package ru.valerykolod.topjava.lunchVotingSystem.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;
import ru.valerykolod.topjava.lunchVotingSystem.util.JpaUtil;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("restaurants").clear();
        cacheManager.getCache("restaurantsForToday").clear();
        cacheManager.getCache("menus").clear();
        cacheManager.getCache("menuForToday").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void addRestaurant() {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant", LocalDate.now());
        restaurantService.addRestaurant(newRestaurant);
        assertMatchRestaurants(restaurantService.getAllRestaurants(),
                RESTAURANT_1, RESTAURANT_2, RESTAURANT_4, newRestaurant, RESTAURANT_3, RESTAURANT_5);
    }

    @Test
    void deleteRestaurant() throws NotFoundException {
        restaurantService.deleteRestaurant(RESTAURANT_ID);
        assertMatchRestaurants(restaurantService.getAllRestaurants(),
                RESTAURANT_2, RESTAURANT_4, RESTAURANT_3, RESTAURANT_5);
    }

    @Test
    void updateRestaurant() throws NotFoundException {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        restaurantService.updateRestaurant(updated);
        assertMatchRestaurants(restaurantService.getRestaurantById(RESTAURANT_ID), updated);
    }

    @Test
    void getRestaurantById() {
        Restaurant restaurant = restaurantService.getRestaurantById(RESTAURANT_ID);
        assertMatchRestaurants(restaurant, RESTAURANT_1);
        assertMatchDishes(restaurant.getDishes(), DISHES_R1);
    }


    @Test
    void addDish() {
        Dish newDish = new Dish(null, "New dish", 200);
        restaurantService.addDish(RESTAURANT_ID, newDish);
        Restaurant restaurant = restaurantService.getRestaurantById(RESTAURANT_ID);
        assertMatchDishes(restaurant.getDishes(), DISH_R1_1, DISH_R1_2, DISH_R1_3, DISH_R1_4, newDish);
    }

    @Test
    void deleteDish() throws NotFoundException {
        restaurantService.deleteDish(RESTAURANT_ID, DISH_ID);
        List<Dish> dishesForRestaurant = restaurantService.getMenuForRestaurant(RESTAURANT_ID);
        assertMatchDishes(dishesForRestaurant, DISH_R1_4, DISH_R1_3, DISH_R1_2);
    }

    @Test
    void updateDish() throws NotFoundException {
        Dish updated = new Dish(DISH_R1_1);
        updated.setName("UpdatedName");
        restaurantService.updateDish(updated, RESTAURANT_ID);
        Restaurant restaurant = restaurantService.getRestaurantById(RESTAURANT_ID);
        assertMatchDishes(restaurant.getDishes(), updated, DISH_R1_2, DISH_R1_3, DISH_R1_4);

    }

    @Test
    void getDishForRestaurant() throws NotFoundException {
        Dish dish = restaurantService.getDishForRestaurant(RESTAURANT_ID, DISH_ID);
        assertMatchDishes(dish, DISH_R1_1);
    }

    @Test
    void getMenuForRestaurant() throws NotFoundException {
        List<Dish> dishList = restaurantService.getMenuForRestaurant(RESTAURANT_ID);
        assertMatchDishes(dishList, DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1);
    }

    @Test
    void getMenuForDate() throws NotFoundException {
        List<Dish> menuForDate = restaurantService.getMenuForDate(MAY_30_2015);
        assertMatchDishes(menuForDate, DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1,
                DISH_R2_4, DISH_R2_3, DISH_R2_2, DISH_R2_1, DISH_R3_4, DISH_R3_3, DISH_R3_2, DISH_R3_1);
    }

    @Test
    void getAllDistinctRestaurantsNames() {
        Assertions.assertThat(restaurantService.getAllDistinctRestaurantsNames())
                .isEqualTo(List.of(R1_NAME, R2_NAME, R3_NAME));
    }

    @Test
    void getAllRestaurants() {
        assertMatchRestaurants(restaurantService.getAllRestaurants(),
                RESTAURANT_1, RESTAURANT_2, RESTAURANT_4, RESTAURANT_3, RESTAURANT_5);
    }

    @Test
    void getAllRestaurantsForDate() throws NotFoundException {
        List<Restaurant> allRestaurantsForDate = restaurantService.getAllRestaurantsForDate(MAY_30_2015);
        assertMatchRestaurants(allRestaurantsForDate, RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    void getDishById() {
        Dish dish = restaurantService.getDishById(DISH_ID);
        assertMatchDishes(dish, DISH_R1_1);
    }

}
