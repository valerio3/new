package ru.valerykolod.topjava.lunchVotingSystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.valerykolod.topjava.lunchVotingSystem.model.User;
import ru.valerykolod.topjava.lunchVotingSystem.service.UserService;

import javax.validation.Valid;

import static ru.valerykolod.topjava.lunchVotingSystem.util.ValidationUtil.checkNew;
import static ru.valerykolod.topjava.lunchVotingSystem.web.RootController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class RootController {

    static final String REST_URL = "/rest/register";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User register(@Valid @RequestBody User user) {
        checkNew(user);
        User registered = userService.create(user);
        log.info("registered new user {} with id {}", registered, registered.getId());
        return registered;
    }

}
