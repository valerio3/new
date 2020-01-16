package ru.valerykolod.topjava.lunchVotingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.valerykolod.topjava.lunchVotingSystem.model.Rating;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;
import ru.valerykolod.topjava.lunchVotingSystem.repository.RatingRepository;
import ru.valerykolod.topjava.lunchVotingSystem.repository.VoteRepository;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;

import java.time.*;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.model.Vote.DECISION_TIME;
import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.checkNotFound;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final VoteRepository voteRepository;
    private Clock clock;
    private ZoneId zoneId;

    @Autowired
    public RatingService(RatingRepository ratingRepository, VoteRepository voteRepository) {
        this.ratingRepository = ratingRepository;
        this.voteRepository = voteRepository;
        this.clock = Clock.systemDefaultZone();
        this.zoneId = ZoneId.systemDefault();
    }

    public void setClockAndTimeZone(LocalDateTime dateTime) {
        this.clock = Clock.fixed(dateTime.atZone(zoneId).toInstant(), zoneId);
    }

    @CacheEvict(value = "rating", allEntries = true)
    public Vote voteForRestaurant(Integer restaurantId, Integer userId) {
        return voteForRestaurant(restaurantId, userId, LocalDate.now(clock), LocalTime.now(clock));
    }

    @Transactional
    Vote voteForRestaurant(Integer newRestaurantId, Integer userId, LocalDate date, LocalTime time) {
        Assert.notNull(newRestaurantId, "newRestaurantId must not be null");
        Vote vote = null;
        if (time.isBefore(DECISION_TIME)) {
            vote = getVoteForUserForDate(userId, date);
            if (vote != null) {
                voteRepository.deleteVote(vote);
                Integer oldRestaurantId = vote.getRestaurant().getId();
                ratingRepository.decreaseRating(oldRestaurantId, date);
            }
            vote = new Vote(date);
            voteRepository.save(vote, userId, newRestaurantId);
            ratingRepository.addNewVote(newRestaurantId, date);
        }
        return vote;
    }

    public Rating getRatingForRestaurantForToday(Integer restaurantId) throws NotFoundException {
        Assert.notNull(restaurantId, "newRestaurantId must not be null");
        return checkNotFound(ratingRepository.getRatingForRestaurantForDate(restaurantId, LocalDate.now()),
                "restaurant=" + restaurantId);
    }

    public Rating getRatingForRestaurantForDate(Integer restaurantId, LocalDate date) throws NotFoundException {
        Assert.notNull(restaurantId, "newRestaurantId must not be null");
        return checkNotFound(ratingRepository.getRatingForRestaurantForDate(restaurantId, date),
                "restaurant=" + restaurantId + " date=" + date.toString());
    }

    public List<Vote> getVoteForUser(Integer userId) {
        return voteRepository.getByUser(userId);
    }

    public Vote getVoteForUserForDate(Integer userId, LocalDate date) {
        return voteRepository.getByUserAndDate(userId, date);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.getAllVotes();
    }

    @CacheEvict(value = "rating", allEntries = true)
    public void deleteOldVotes() {
        voteRepository.deleteOldVotesFromDate(LocalDate.now());
    }

    @CacheEvict(value = "rating", allEntries = true)
    public void deleteOldVotesForDate(LocalDate date) {
        voteRepository.deleteOldVotesFromDate(date);
    }

    @Cacheable("rating")
    public List<Rating> getRatingForToday() throws NotFoundException {
        return checkNotFound(ratingRepository.getRatingForDate(LocalDate.now()), "rating for today");
    }

    public List<Rating> getRatingForDate(LocalDate date) throws NotFoundException {
        return checkNotFound(ratingRepository.getRatingForDate(date), "rating for day=" + date.toString());
    }

    public List<Rating> getRatingForRestaurantName(Integer restaurantId) throws NotFoundException {
        Assert.notNull(restaurantId, "newRestaurantId must not be null");
        return checkNotFound(ratingRepository.getRatingForRestaurantName(restaurantId),
                "restaurant=" + restaurantId);
    }

}
