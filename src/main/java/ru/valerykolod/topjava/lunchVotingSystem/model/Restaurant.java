package ru.valerykolod.topjava.lunchVotingSystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_on_date_idx")})
@NamedQueries({
        @NamedQuery(name = Restaurant.ALL_DISTINCT_NAMES_SORTED, query = "SELECT DISTINCT (r.name) FROM Restaurant r ORDER BY r.name"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT (r) FROM Restaurant r ORDER BY r.name"),
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.ALL_FOR_DATE, query = "SELECT r FROM Restaurant r WHERE r.date=:date ORDER BY r.name")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Restaurant.class)
public class Restaurant extends AbstractEntity {

    public static final String ALL_DISTINCT_NAMES_SORTED = "Restaurants.getAllDistinctNames";
    public static final String ALL_FOR_DATE = "Restaurants.getAllForDate";
    public static final String ALL_SORTED = "Restaurants.getAllSorted";
    public static final String DELETE = "Restaurants.deleteById";

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @OrderBy("id ASC")
    protected List<Dish> dishes;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.id, restaurant.name);
        this.date = restaurant.date;
        this.dishes = new ArrayList<>(restaurant.dishes);
    }

    public Restaurant(Integer id, String name, LocalDate date) {
        super(id, name);
        this.date = date;
    }

    public Restaurant(Integer id, String name, LocalDate date, List<Dish> dishes) {
        super(id, name);
        this.date = date;
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "date=" + date +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
