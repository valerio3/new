package ru.valerykolod.topjava.lunchVotingSystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.valerykolod.topjava.lunchVotingSystem.model.User;
import ru.valerykolod.topjava.lunchVotingSystem.service.UserService;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;

import java.util.List;

import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.assureIdConsistent;
import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.checkNew;

public abstract class AbstractUserRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    public List<User> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all users for user with id = {}", userId);
        return userService.getAll();
    }

    public User get(int id) throws NotFoundException {
        int userId = SecurityUtil.authUserId();
        log.info("get user {} for user with id = {}", id, userId);
        return userService.get(id);
    }

    public User create(User user) {
        int userId = SecurityUtil.authUserId();
        log.info("create user {} for user with id = {}", user.getId(), userId);
        checkNew(user);
        return userService.create(user);
    }

    public void delete(int id) throws NotFoundException {
        int userId = SecurityUtil.authUserId();
        log.info("delete user {} for user with id = {}", id, userId);
        userService.delete(id);
    }

    public void update(User user, int id) throws NotFoundException {
        log.info("update user {} for user with id = {}", user, id);
        assureIdConsistent(user, id);
        userService.update(user);
    }

    public User getByEmail(String email) throws NotFoundException {
        int userId = SecurityUtil.authUserId();
        log.info("get user by email {} for user with id = {}", email, userId);
        return userService.getByEmail(email);
    }

}