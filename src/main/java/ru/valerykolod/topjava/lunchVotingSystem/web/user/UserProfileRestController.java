package ru.valerykolod.topjava.lunchVotingSystem.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.valerykolod.topjava.lunchVotingSystem.model.User;
import ru.valerykolod.topjava.lunchVotingSystem.util.exception.NotFoundException;
import ru.valerykolod.topjava.lunchVotingSystem.web.AbstractUserRestController;
import ru.valerykolod.topjava.lunchVotingSystem.web.SecurityUtil;

import javax.validation.Valid;

import static ru.valerykolod.topjava.lunchVotingSystem.web.user.UserProfileRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class UserProfileRestController extends AbstractUserRestController {

    static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() throws NotFoundException {
        return super.get(SecurityUtil.authUserId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() throws NotFoundException {
        super.delete(SecurityUtil.authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user) throws NotFoundException {
        super.update(user, SecurityUtil.authUserId());
    }

}