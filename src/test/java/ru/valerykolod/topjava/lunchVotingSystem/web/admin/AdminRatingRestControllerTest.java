package ru.valerykolod.topjava.lunchVotingSystem.web.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.valerykolod.topjava.lunchVotingSystem.TestUtil;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;
import ru.valerykolod.topjava.lunchVotingSystem.service.RatingService;
import ru.valerykolod.topjava.lunchVotingSystem.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.valerykolod.topjava.lunchVotingSystem.RatingTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.RestaurantTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.userHttpBasic;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.ADMIN;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.USER_ID;

class AdminRatingRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRatingRestController.REST_URL + '/';

    @Autowired
    private RatingService ratingService;

    @Test
    void getRatingForDate() throws Exception {
        mockMvc.perform(get(REST_URL + "all/" + MAY_30_2015)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRatingMatcher( RATING_R1_D30, RATING_R2_D30, RATING_R3_D30));
    }

    @Test
    void getAllVotes() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "all")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_U1_D30, VOTE_U2_D30, VOTE_U3_D30, VOTE_U4_D30, VOTE_U5_D30, VOTE_U2_D31,
                VOTE_U3_D31, VOTE_U4_D31, VOTE_U5_D31)));
    }

    @Test
    void getRatingForRestaurantName() throws Exception {
        int id = RESTAURANT_ID + 1;
        mockMvc.perform(get(REST_URL + id)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRatingMatcher(RATING_R2_D31, RATING_R2_D30));
    }

    @Test
    void getRatingForRestaurantForDate() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_ID + "/" + MAY_30_2015)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRatingMatcher(RATING_R1_D30));
    }

    @Test
    void getVoteForUserForDate() throws Exception {
        mockMvc.perform(get(REST_URL + "user/" + USER_ID + "/" + MAY_30_2015)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_U1_D30));
    }

    @Test
    void getVoteForUser() throws Exception {
        int userId = USER_ID + 1;
        mockMvc.perform(get(REST_URL + "user/" + userId)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_U2_D30, VOTE_U2_D31));
    }

    @Test
    void deleteOldVotes() throws Exception {
        mockMvc.perform(delete(REST_URL)
            .with(userHttpBasic(ADMIN)))
            .andDo(print())
            .andExpect(status().isNoContent());

        List<Vote> voteList = ratingService.getAllVotes();
        Assertions.assertEquals(0, voteList.size());
    }

    @Test
    void deleteOldVotesForDate() throws Exception {
        mockMvc.perform(delete(REST_URL + MAY_31_2015)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        List<Vote> voteList = ratingService.getAllVotes();
        assertMatchVotes(voteList, VOTE_U2_D31, VOTE_U3_D31, VOTE_U4_D31, VOTE_U5_D31);
    }

}