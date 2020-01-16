package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    @Transactional
    @Modifying
    @Query(name = Dish.DELETE)
    int deleteById(@Param("id") int id);

    Optional<Dish> findById(int id);

    @Query(name = Dish.GET_MENUS_FOR_DATE)
    List<Dish> getAllMenusForDate(@Param("date") LocalDate date);

    @Query(name = Dish.GET_MENU_FOR_RESTAURANT)
    List<Dish> getMenuForRestaurant(@Param("restaurantId") int restaurantId);

    @Query(name = Dish.GET_DISH_FOR_RESTAURANT)
    Optional<Dish> getDishForRestaurant(@Param("restaurantId") int restaurantId, @Param("id") int id);
}
