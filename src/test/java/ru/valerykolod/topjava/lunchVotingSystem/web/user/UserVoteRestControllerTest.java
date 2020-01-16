package ru.valerykolod.topjava.lunchVotingSystem.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.valerykolod.topjava.lunchVotingSystem.TestUtil;
import ru.valerykolod.topjava.lunchVotingSystem.model.Rating;
import ru.valerykolod.topjava.lunchVotingSystem.model.Restaurant;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;
import ru.valerykolod.topjava.lunchVotingSystem.service.RatingService;
import ru.valerykolod.topjava.lunchVotingSystem.service.RestaurantService;
import ru.valerykolod.topjava.lunchVotingSystem.web.AbstractControllerTest;
import ru.valerykolod.topjava.lunchVotingSystem.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.valerykolod.topjava.lunchVotingSystem.RatingTestData.assertMatchNewRatings;
import static ru.valerykolod.topjava.lunchVotingSystem.RatingTestData.getVoteMatcher;
import static ru.valerykolod.topjava.lunchVotingSystem.RestaurantTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.userAuth;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.USER_1;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.USER_ID;
import static ru.valerykolod.topjava.lunchVotingSystem.model.Vote.DECISION_TIME;

class UserVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteRestController.REST_URL + '/';

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void voteForRestaurant() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTimeForTest = LocalDateTime.of(
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                DECISION_TIME.getHour() - 1,
                now.getMinute()
        );

        ratingService.setClockAndTimeZone(dateTimeForTest);
        Restaurant testingRestaurant = new Restaurant(null, "TestingRestaurant", dateTimeForTest.toLocalDate());
        restaurantService.addRestaurant(testingRestaurant);
        int restaurantId = restaurantService.getAllRestaurantsForToday().get(0).getId();
        testingRestaurant.setId(restaurantId);

        ratingService.voteForRestaurant(restaurantId, USER_ID);

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "vote/" + restaurantId)
                .with(userAuth(USER_1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(testingRestaurant.getId())))
                .andExpect(status().isCreated());

        Rating newRating = new Rating(null, testingRestaurant, 1, dateTimeForTest.toLocalDate());
        assertMatchNewRatings(ratingService.getRatingForDate(dateTimeForTest.toLocalDate()), Collections.singleton(newRating));
    }

    @Test
    @DirtiesContext
    void getMenuForRestaurant() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "/menu/" + RESTAURANT_ID)
                .with(userAuth(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1)));
    }

    @Test
    void showCurrentVote() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTimeForTest = LocalDateTime.of(
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                DECISION_TIME.getHour() - 1,
                now.getMinute()
        );

        ratingService.setClockAndTimeZone(dateTimeForTest);

        Vote userVote = ratingService.voteForRestaurant(RESTAURANT_ID, USER_ID);

        TestUtil.print(mockMvc.perform(get(REST_URL + "/vote")
                .with(userAuth(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(userVote)));

    }

}