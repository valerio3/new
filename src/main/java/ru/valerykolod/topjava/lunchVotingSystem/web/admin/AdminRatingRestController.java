package ru.valerykolod.topjava.lunchVotingSystem.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.valerykolod.topjava.lunchVotingSystem.model.Rating;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;
import ru.valerykolod.topjava.lunchVotingSystem.service.RatingService;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.web.admin.AdminRatingRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class AdminRatingRestController {

    static final String REST_URL = "/rest/admin/rating";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RatingService ratingService;

    @GetMapping(value = "/all/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatingForDate(@PathVariable("date") String date) throws NotFoundException {
        log.info("get rating for date {}", date);
        LocalDate localDate = LocalDate.parse(date);
        return ratingService.getRatingForDate(localDate);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllVotes() {
        log.info("get all votes");
        return ratingService.getAllVotes();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatingForRestaurantName(@PathVariable("id") int id) throws NotFoundException {
        log.info("get rating for restaurant name with id {}", id);
        return ratingService.getRatingForRestaurantName(id);
    }

    @GetMapping(value = "/{id}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rating getRatingForRestaurantForDate(@PathVariable("id") int id, @PathVariable("date") String date)
            throws NotFoundException {
        log.info("get rating for restaurant with id {} for date {}", id, date);
        LocalDate localDate = LocalDate.parse(date);
        return ratingService.getRatingForRestaurantForDate(id, localDate);
    }

    @GetMapping(value = "/user/{id}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote getVoteForUserForDate(@PathVariable("id") int id, @PathVariable("date") String date) {
        log.info("get vote for user with id {} for date {}", id, date);
        LocalDate localDate = LocalDate.parse(date);
        return ratingService.getVoteForUserForDate(id, localDate);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getVoteForUser(@PathVariable("id") int id) {
        log.info("get vote for user with id {} ", id);
        return ratingService.getVoteForUser(id);
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteOldVotes() {
        log.info("delete old votes");
        ratingService.deleteOldVotes();
    }

    @DeleteMapping("/{date}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteOldVotesForDate(@PathVariable("date") String date) {
        log.info("delete old votes for date {}", date);
        LocalDate localDate = LocalDate.parse(date);
        ratingService.deleteOldVotesForDate(localDate);
    }

}
