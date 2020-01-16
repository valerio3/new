package ru.valerykolod.topjava.lunchVotingSystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.valerykolod.topjava.lunchVotingSystem.model.Rating;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;
import ru.valerykolod.topjava.lunchVotingSystem.util.JpaUtil;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;

import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.RatingTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.RestaurantTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.USER_ID;
import static ru.valerykolod.topjava.lunchVotingSystem.model.Vote.DECISION_TIME;

public class RatingServiceTest extends AbstractServiceTest {

    @Autowired
    protected RatingService ratingService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("rating").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void voteForRestaurant() throws NotFoundException {
        ratingService.voteForRestaurant(RESTAURANT_ID, USER_ID, MAY_31_2015, DECISION_TIME.minusHours(1));
        Rating newRating = new Rating(null, RESTAURANT_1, 1, MAY_31_2015);
        assertMatchNewRatings(ratingService.getRatingForDate(MAY_31_2015),
                List.of(newRating, RATING_R2_D31, RATING_R3_D31));
    }

    @Test
    void voteForRestaurantChangeDecisionBeforeDecisionTime() throws NotFoundException {
        ratingService.voteForRestaurant(RESTAURANT_ID + 2, USER_ID, MAY_30_2015, DECISION_TIME.minusHours(1));
        Rating newRatingForRestaurant1 = new Rating(null, RESTAURANT_1, 2, MAY_30_2015);
        Rating newRatingForRestaurant3 = new Rating(null, RESTAURANT_3, 2, MAY_30_2015);
        assertMatchNewRatings(ratingService.getRatingForDate(MAY_30_2015),
                List.of(newRatingForRestaurant1, RATING_R2_D30, newRatingForRestaurant3));
    }

    @Test
    void voteForRestaurantAfterDecisionTime() throws NotFoundException {
        ratingService.voteForRestaurant(RESTAURANT_ID + 2, USER_ID, MAY_30_2015, DECISION_TIME.plusHours(1));
        assertMatchNewRatings(ratingService.getRatingForDate(MAY_30_2015),
                List.of(RATING_R1_D30, RATING_R2_D30, RATING_R3_D30));
    }

    @Test
    void getRatingForRestaurantForDate() throws NotFoundException {
        Rating rating = ratingService.getRatingForRestaurantForDate(RESTAURANT_ID, MAY_30_2015);
        assertMatchRatings(rating, RATING_R1_D30);
    }

    @Test
    void deleteOldVotes() {
        ratingService.deleteOldVotes();
        List<Vote> voteList = ratingService.getAllVotes();
        Assertions.assertEquals(0, voteList.size());
    }

    @Test
    void deleteOldVotesForDate() throws NotFoundException {
        ratingService.deleteOldVotesForDate(MAY_31_2015);
        List<Vote> voteList = ratingService.getAllVotes();
        assertMatchVotes(voteList, VOTE_U2_D31, VOTE_U3_D31, VOTE_U4_D31, VOTE_U5_D31);
    }

    @Test
    void getRatingForDate() throws NotFoundException {
        List<Rating> ratingList = ratingService.getRatingForDate(MAY_30_2015);
        assertMatchRatings(ratingList, RATING_R1_D30, RATING_R2_D30, RATING_R3_D30);
    }

    @Test
    void getRatingForRestaurantName() throws NotFoundException {
        List<Rating> raitingList = ratingService.getRatingForRestaurantName(RESTAURANT_ID + 1);
        assertMatchRatings(raitingList, RATING_R2_D31, RATING_R2_D30);
    }

    @Test
    void getVoteForUser() {
        List<Vote> voteList = ratingService.getVoteForUser(USER_ID + 1);
        assertMatchVotes(voteList, VOTE_U2_D30, VOTE_U2_D31);
    }

    @Test
    void getVoteForUserForDate() {
        Vote vote = ratingService.getVoteForUserForDate(USER_ID, MAY_30_2015);
        assertMatchVotes(vote, VOTE_U1_D30);
    }

    @Test
    void getAllVotes(){
        List<Vote> voteList = ratingService.getAllVotes();
        assertMatchVotes(voteList, VOTE_U1_D30, VOTE_U2_D30, VOTE_U3_D30, VOTE_U4_D30, VOTE_U5_D30, VOTE_U2_D31,
                VOTE_U3_D31, VOTE_U4_D31, VOTE_U5_D31);
    }

}
