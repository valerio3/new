package ru.valerykolod.topjava.lunchVotingSystem;

import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.valerykolod.topjava.lunchVotingSystem.model.Rating;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.RestaurantTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.readFromJsonMvcResult;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.readListFromJsonMvcResult;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.model.AbstractEntity.START_SEQ;

public class RatingTestData {

    public static final int VOTES_ID = START_SEQ + 31;
    public static final int RATING_ID = START_SEQ + 40;

    public static final Vote VOTE_U1_D30 = new Vote(VOTES_ID, USER_1, RESTAURANT_1, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U2_D30 = new Vote(VOTES_ID + 1, USER_2, RESTAURANT_1, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U3_D30 = new Vote(VOTES_ID + 2, USER_3, RESTAURANT_1, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U4_D30 = new Vote(VOTES_ID + 3, USER_4, RESTAURANT_2, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U5_D30 = new Vote(VOTES_ID + 4, USER_5, RESTAURANT_3, LocalDate.of(2015, 5, 30));

    public static final Vote VOTE_U2_D31 = new Vote(VOTES_ID + 5, USER_2, RESTAURANT_2, LocalDate.of(2015, 5, 31));
    public static final Vote VOTE_U3_D31 = new Vote(VOTES_ID + 6, USER_3, RESTAURANT_2, LocalDate.of(2015, 5, 31));
    public static final Vote VOTE_U4_D31 = new Vote(VOTES_ID + 7, USER_4, RESTAURANT_2, LocalDate.of(2015, 5, 31));
    public static final Vote VOTE_U5_D31 = new Vote(VOTES_ID + 8, USER_5, RESTAURANT_3, LocalDate.of(2015, 5, 31));

    public static final Rating RATING_R1_D30 = new Rating(RATING_ID, RESTAURANT_1, 3, LocalDate.of(2015, 5, 30));
    public static final Rating RATING_R2_D30 = new Rating(RATING_ID + 1, RESTAURANT_2, 1, LocalDate.of(2015, 5, 30));
    public static final Rating RATING_R3_D30 = new Rating(RATING_ID + 2, RESTAURANT_3, 1, LocalDate.of(2015, 5, 30));

    public static final Rating RATING_R2_D31 = new Rating(RATING_ID + 3, RESTAURANT_2, 3, LocalDate.of(2015, 5, 31));
    public static final Rating RATING_R3_D31 = new Rating(RATING_ID + 4, RESTAURANT_3, 1, LocalDate.of(2015, 5, 31));

    public static void assertMatchRatings(Rating actual, Rating expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatchRatings(Iterable<Rating> actual, Rating... expected) {
        assertMatchRatings(actual, List.of(expected));
    }

    private static void assertMatchRatings(Iterable<Rating> actual, Iterable<Rating> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static void assertMatchNewRatings(Iterable<Rating> actual, Iterable<Rating> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "id").isEqualTo(expected);
    }

    public static void assertMatchVotes(Vote actual, Vote expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "user");
    }

    public static void assertMatchVotes(Iterable<Vote> actual, Vote... expected) {
        assertMatchVotes(actual, List.of(expected));
    }

    private static void assertMatchVotes(Iterable<Vote> actual, Iterable<Vote> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "user").isEqualTo(expected);
    }

    public static ResultMatcher getRatingMatcher(Rating... expected) {
        return result -> assertMatchRatings(readListFromJsonMvcResult(result, Rating.class), List.of(expected));
    }

    public static ResultMatcher getRatingMatcher(Rating expected) {
        return result -> assertMatchRatings(readFromJsonMvcResult(result, Rating.class), expected);
    }

    public static ResultMatcher getVoteMatcher(Vote... expected) {
        return result -> assertMatchVotes(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }

    public static ResultMatcher getVoteMatcher(Vote expected) {
        return result -> assertMatchVotes(readFromJsonMvcResult(result, Vote.class), expected);
    }


}