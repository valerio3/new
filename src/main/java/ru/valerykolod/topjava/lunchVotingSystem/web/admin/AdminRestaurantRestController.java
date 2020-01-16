package ru.valerykolod.topjava.lunchVotingSystem.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;
import ru.valerykolod.topjava.lunchVotingSystem.service.RestaurantService;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.assureIdConsistent;
import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.checkNew;
import static ru.valerykolod.topjava.lunchVotingSystem.web.admin.AdminRestaurantRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class AdminRestaurantRestController {

    static final String REST_URL = "/rest/admin/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants() {
        log.info("get all restaurants");
        return restaurantService.getAllRestaurants();
    }

    @GetMapping(value = "/names",  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllDistinctRestaurantNames() {
        log.info("get distinct restaurant names");
        return restaurantService.getAllDistinctRestaurantsNames();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurantById(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping(value = "/all/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurantsForDate(@PathVariable("date") String date) throws NotFoundException {
        log.info("get restaurants by date {}", date);
        LocalDate localDate = LocalDate.parse(date);
        return restaurantService.getAllRestaurantsForDate(localDate);
    }

    @GetMapping(value = "/menu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getMenuForRestaurant(@PathVariable("id") int id)
            throws NotFoundException {
        log.info("get menu by restaurant id {}", id);
        return restaurantService.getMenuForRestaurant(id);
    }

    @GetMapping(value = "/menu/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getMenuForDate(@PathVariable("date") String date) throws NotFoundException {
        log.info("get menu for date {}", date);
        LocalDate localDate = LocalDate.parse(date);
        return restaurantService.getMenuForDate(localDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("add restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant newRestaurant = restaurantService.addRestaurant(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @GetMapping(value = "/dish/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDishById(@PathVariable("id") int id) throws NotFoundException {
        log.info("get dish by id {}", id);
        return restaurantService.getDishById(id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> addDish(@Valid @RequestBody Dish dish, @PathVariable("id") int id) {
        log.info("add dish {} for restaurant {}", dish, id);
        checkNew(dish);
        Dish newDish = restaurantService.addDish(id, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/dish/{dishId}")
                .buildAndExpand(newDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newDish);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") int id) throws NotFoundException {
        log.info("delete restaurant {}", id);
        restaurantService.deleteRestaurant(id);
    }

    @DeleteMapping(value = "/{id}/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable("id") int id, @PathVariable("dishId") int dishId) throws NotFoundException {
        log.info("delete dish {} for restaurant {}", dishId, id);
        restaurantService.deleteDish(id, dishId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) throws NotFoundException {
        log.info("update restaurant {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantService.updateRestaurant(restaurant);
    }

    @PutMapping(value = "/{id}/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@RequestBody Dish dish, @PathVariable("id") int id, @PathVariable("dishId") int dishId)
            throws NotFoundException {
        log.info("update dish {} with id={} for restaurant {}", dish, dishId, id);
        assureIdConsistent(dish, dishId);
        restaurantService.updateDish(dish, id);
    }

}