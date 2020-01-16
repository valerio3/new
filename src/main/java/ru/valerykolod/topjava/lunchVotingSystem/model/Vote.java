package ru.valerykolod.topjava.lunchVotingSystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "votes")
@NamedQueries({
        @NamedQuery(name = Vote.DELETE_FOR_DATE, query = "DELETE FROM Vote v WHERE v.date < :date"),
        @NamedQuery(name = Vote.GET_BY_USER, query = "SELECT v FROM Vote v WHERE v.user.id =: userId " +
                "ORDER BY v.restaurant.name"),
        @NamedQuery(name = Vote.GET_BY_USER_AND_DATE, query = "SELECT v FROM Vote v WHERE v.user.id =: userId " +
                "AND v.date =: date ORDER BY v.restaurant.name")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Vote.class)
public class Vote {

    public static final LocalTime DECISION_TIME = LocalTime.of(11, 00);
    public static final String DELETE_FOR_DATE = "Vote.deleteForDate";
    public static final String GET_BY_USER = "Vote.getByUser";
    public static final String GET_BY_USER_AND_DATE = "Vote.getByUserAndDate";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @BatchSize(size = 200)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @BatchSize(size = 200)
    private Restaurant restaurant;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(@NotNull Integer id, @NotNull User user, @NotNull Restaurant restaurant, @NotNull LocalDate date) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }

    public Vote(@NotNull LocalDate date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

}
