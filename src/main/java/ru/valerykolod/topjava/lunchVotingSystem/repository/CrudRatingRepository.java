package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykolod.topjava.lunchVotingSystem.model.Rating;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRatingRepository extends JpaRepository<Rating, Integer> {

    @Override
    @Transactional
    Rating save(Rating rating);

    @Override
    Optional<Rating> findById(Integer id);

    @Query(name = Rating.GET_FOR_DATE)
    List<Rating> getForDate(@Param("date") LocalDate date);

    @Query(name = Rating.GET_FOR_DATE_AND_RESTAURANT)
    Optional<Rating> getForRestaurantForDate(@Param("restaurantId") Integer restaurantId, @Param("date") LocalDate date);

    @Query(name = Rating.GET_FOR_RESTAURANT_NAME)
    List<Rating> getForRestaurantName(@Param("restaurantId") Integer restaurantId);

    @Override
    void delete(Rating entity);

}
