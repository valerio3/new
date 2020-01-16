package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantRepository {

    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Restaurant save(Restaurant user) {
        return crudRestaurantRepository.save(user);
    }

    public boolean delete(int id) {
        return crudRestaurantRepository.deleteById(id) != 0;
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    public List<String> getAllDistinctNames() {
        return crudRestaurantRepository.getAllDistinctNames();
    }

    public List<Restaurant> getAll() {
        return crudRestaurantRepository.getAll();
    }

    public List<Restaurant> getAllForDate(LocalDate date) {
        return crudRestaurantRepository.getAllForDate(date);
    }

}
