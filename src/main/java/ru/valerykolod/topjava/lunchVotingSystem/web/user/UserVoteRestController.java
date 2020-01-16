package ru.valerykolod.topjava.lunchVotingSystem.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.valerykolod.topjava.lunchVotingSystem.model.Dish;
import ru.valerykolod.topjava.lunchVotingSystem.model.Rating;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;
import ru.valerykolod.topjava.lunchVotingSystem.service.RatingService;
import ru.valerykolod.topjava.lunchVotingSystem.service.RestaurantService;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;
import ru.valerykolod.topjava.lunchVotingSystem.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.web.user.UserVoteRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class UserVoteRestController {

    static final String REST_URL = "/rest/rating";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatingForToday() throws NotFoundException {
        log.info("get rating for today");
        return ratingService.getRatingForToday();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rating getRatingForRestaurantForToday(@PathVariable("id") int id) throws NotFoundException {
        log.info("get rating for restaurant {} for today", id);
        return ratingService.getRatingForRestaurantForToday(id);
    }

    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote showCurrentVote() {
        int userId = SecurityUtil.authUserId();
        log.info("show vote made by user {}", userId);
        return ratingService.getVoteForUserForDate(userId, LocalDate.now());
    }

    @PostMapping(value = "/vote/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> voteForRestaurant(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        log.info("add vote for restaurant {} by user {}", id, userId);
        Vote vote = ratingService.voteForRestaurant(id, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/vote")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurantsForToday() throws NotFoundException {
        log.info("get restaurants for today");
        return restaurantService.getAllRestaurantsForToday();
    }

    @GetMapping(value = "/menu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getMenuForRestaurant(@PathVariable("id") int id) throws NotFoundException {
        log.info("get restaurants by restaurantId {} for today", id);
        return restaurantService.getMenuForRestaurant(id);
    }

    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getMenuForToday() throws NotFoundException {
        log.info("get menu for today");
        return restaurantService.getMenuForToday();
    }

}


