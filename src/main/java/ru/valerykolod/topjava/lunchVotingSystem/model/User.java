package ru.valerykolod.topjava.lunchVotingSystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.valerykolod.topjava.lunchVotingSystem.model.Role.ROLE_USER;

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
@NamedQueries({
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.name, u.email"),
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id =:id")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = User.class)
public class User extends AbstractEntity implements Serializable {

    public static final String ALL_SORTED = "User.getAllSorted";
    public static final String DELETE = "User.deleteById";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime registered = LocalDateTime.now();

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(User user) {
        super(user.id, user.name);
        this.email = user.email;
        this.password = user.password;
        this.registered = user.registered;
        this.role = user.role;
    }

    public User(Integer id, String name, String email, String password, Role role, LocalDateTime registered) {
        this(id, name, email, password, role);
        this.registered = registered;
    }

    public User(Integer id, String name, String email, String password, Role role) {
        this(id, name, email, password);
        this.role = role;
    }

    public User(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.registered = LocalDateTime.now();
        this.role = ROLE_USER;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
