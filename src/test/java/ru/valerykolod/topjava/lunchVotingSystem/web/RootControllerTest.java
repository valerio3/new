package ru.valerykolod.topjava.lunchVotingSystem.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykolod.topjava.lunchVotingSystem.model.Role;
import ru.valerykolod.topjava.lunchVotingSystem.model.User;
import ru.valerykolod.topjava.lunchVotingSystem.service.UserService;
import ru.valerykolod.topjava.lunchVotingSystem.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.readFromJsonResultActions;
import static ru.valerykolod.topjava.lunchVotingSystem.TestUtil.userHttpBasic;
import static ru.valerykolod.topjava.lunchVotingSystem.UserTestData.*;
import static ru.valerykolod.topjava.lunchVotingSystem.web.RootController.REST_URL;

class RootControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void register() throws Exception {
        User newUser = new User(null, "NewUser", "new_email@email.com", "12345678");

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, newUser.getPassword())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        User returned = readFromJsonResultActions(action, User.class);

        newUser.setId(returned.getId());

        assertMatchUsers(returned, newUser);
        assertMatchUsers(userService.getByEmail("new_email@email.com"), newUser);
        assertMatchUsers(userService.getAll(), ADMIN, newUser, USER_1, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void registerDuplicateEmail() throws Exception {
        User newUser = new User(null, "newName", "new@mail.com", "newPassword", Role.ROLE_USER);
        newUser.setEmail(ADMIN.getEmail());
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_1))
                .content(JsonUtil.writeValue(newUser)))
                .andExpect(status().isForbidden());
    }

}