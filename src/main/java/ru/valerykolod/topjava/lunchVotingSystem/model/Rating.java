package ru.valerykolod.topjava.lunchVotingSystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static ru.valerykolod.topjava.lunchVotingSystem.model.AbstractEntity.START_SEQ;

@Entity
@Table(name = "rating")
@NamedQueries({
        @NamedQuery(name = Rating.GET_FOR_DATE, query = "SELECT r FROM Rating r WHERE r.date =: date ORDER BY r.restaurant.name"),
        @NamedQuery(name = Rating.GET_FOR_RESTAURANT_NAME, query = "SELECT r FROM Rating r " +
                "WHERE r.restaurant.name IN (SELECT res.name FROM Restaurant res WHERE res.id =: restaurantId) ORDER BY r.restaurant.date DESC"),
        @NamedQuery(name = Rating.GET_FOR_DATE_AND_RESTAURANT, query = "SELECT r FROM Rating r " +
                "WHERE r.restaurant.id =: restaurantId AND r.date =: date")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Rating.class)
public class Rating {

    public static final String GET_FOR_DATE = "Rating.getDishesForDate";
    public static final String GET_FOR_RESTAURANT_NAME = "Rating.getForRestaurantName";
    public static final String GET_FOR_DATE_AND_RESTAURANT = "Rating.getForDateAndRestaurant";

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @BatchSize(size = 200)
    private Restaurant restaurant;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    @Column(name = "summary_votes", nullable = false)
    @NotNull
    private Integer summaryVotes;

    public Rating() {
    }

    public Rating(@NotNull Integer id, @NotNull Restaurant restaurant, @NotBlank Integer summaryVotes, @NotNull LocalDate date) {
        this.id = id;
        this.restaurant = restaurant;
        this.date = date;
        this.summaryVotes = summaryVotes;
    }

    public Rating(@NotNull Restaurant restaurant, @NotBlank Integer summaryVotes, @NotNull LocalDate date) {
        this.id = null;
        this.restaurant = restaurant;
        this.date = date;
        this.summaryVotes = summaryVotes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSummaryVotes() {
        return summaryVotes;
    }

    public void setSummaryVotes(Integer summaryVotes) {
        this.summaryVotes = summaryVotes;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", date=" + date +
                ", summaryVotes=" + summaryVotes +
                '}';
    }

}
