package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query(name = Restaurant.DELETE)
    int deleteById(@Param("id") int id);

    Optional<Restaurant> findById(@Param("id") Integer id);

    @Query(name = Restaurant.ALL_DISTINCT_NAMES_SORTED)
    List<String> getAllDistinctNames();

    @Query(name = Restaurant.ALL_FOR_DATE)
    List<Restaurant> getAllForDate(@Param("date") LocalDate date);

    @Query(name = Restaurant.ALL_SORTED)
    List<Restaurant> getAll();
}