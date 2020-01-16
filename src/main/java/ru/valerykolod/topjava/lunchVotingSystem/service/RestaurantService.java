package ru.valerykolod.topjava.lunchVotingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;
import ru.valerykolod.topjava.lunchVotingSystem.repository.DishRepository;
import ru.valerykolod.topjava.lunchVotingSystem.repository.RestaurantRepository;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.checkNotFound;
import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = {"restaurants", "restaurantsForToday"}, allEntries = true)
    public Restaurant addRestaurant(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"restaurants", "restaurantsForToday"}, allEntries = true)
    public void deleteRestaurant(Integer id) throws NotFoundException {
        checkNotFoundWithId(id, restaurantRepository.delete(id));
    }

    @CacheEvict(value = {"restaurants", "restaurantsForToday"}, allEntries = true)
    public void updateRestaurant(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    public Restaurant getRestaurantById(Integer id) {
        Assert.notNull(id, "id must be not null");
        return restaurantRepository.get(id);
    }

    @CacheEvict(value = {"menus", "menuForToday"}, allEntries = true)
    public Dish addDish(Integer restaurantId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish, restaurantId);
    }

    @CacheEvict(value = {"menus", "menuForToday"}, allEntries = true)
    public void deleteDish(int restaurantId, int dishId) throws NotFoundException {
        Assert.notNull(getDishForRestaurant(restaurantId, dishId), "this restaurant has no such dish");
        checkNotFoundWithId(dishId, dishRepository.delete(dishId));
    }

    @CacheEvict(value = {"menus", "menuForToday"}, allEntries = true)
    public void updateDish(Dish dish, Integer restaurantId) throws NotFoundException {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.save(dish, restaurantId), dish.getId());
    }

    // for testing purposes
    protected Dish getDishForRestaurant(int restaurantId, int dishId) throws NotFoundException {
        Dish dish = dishRepository.getDishForRestaurant(restaurantId, dishId);
        checkNotFoundWithId(dish, dishId);
        return dish;
    }

    public List<Dish> getMenuForRestaurant(Integer restaurantId) throws NotFoundException {
        return checkNotFound(dishRepository.getMenuForRestaurant(restaurantId),
                "restaurant=" + restaurantId);
    }

    @Cacheable("menus")
    public List<Dish> getMenuForDate(LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(dishRepository.getAllMenusForDate(date),
                "date=" + date.toString());
    }

    @Cacheable("menuForToday")
    public List<Dish> getMenuForToday() throws NotFoundException {
        return checkNotFound(dishRepository.getAllMenusForDate(LocalDate.now()),
                "date=" + LocalDate.now().toString());
    }

    public List<String> getAllDistinctRestaurantsNames() {
        return restaurantRepository.getAllDistinctNames();
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.getAll();
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllRestaurantsForDate(LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(restaurantRepository.getAllForDate(date),
                "date=" + date.toString());
    }

    @Cacheable("restaurantsForToday")
    public List<Restaurant> getAllRestaurantsForToday() throws NotFoundException {
        return checkNotFound(restaurantRepository.getAllForDate(LocalDate.now()),
                "date=" + LocalDate.now().toString());
    }

    public Dish getDishById(int dishId) {
        return checkNotFound(dishRepository.get(dishId), "id=" + dishId);
    }
}
