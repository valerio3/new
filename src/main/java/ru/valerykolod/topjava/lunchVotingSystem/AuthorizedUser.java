package ru.valerykolod.topjava.lunchVotingSystem;

import ru.valerykolod.topjava.lunchVotingSystem.model.User;

import java.util.Collections;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{

    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true,
                true, Collections.singleton(user.getRole()));
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public void update(User newUser) {
        user = newUser;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }

}
