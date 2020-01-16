package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {

    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public DishRepository(CrudRestaurantRepository crudRestaurantRepository, CrudDishRepository crudDishRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Dish save(Dish dish, Integer restaurantId) {
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    public boolean delete(int id) {
        return crudDishRepository.deleteById(id) != 0;
    }

    public Dish get(int id) {
        return crudDishRepository.findById(id).orElse(null);
    }

    public List<Dish> getAllMenusForDate(LocalDate date) {
        return crudDishRepository.getAllMenusForDate(date);
    }

    public List<Dish> getMenuForRestaurant(Integer restaurantId) {
        return crudDishRepository.getMenuForRestaurant(restaurantId);
    }

    public Dish getDishForRestaurant(int restaurantId, int dishId) {
        return crudDishRepository.getDishForRestaurant(restaurantId, dishId).orElse(null);
    }
}
