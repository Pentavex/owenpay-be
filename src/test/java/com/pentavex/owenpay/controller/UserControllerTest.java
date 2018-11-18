package com.pentavex.owenpay.controller;

import com.pentavex.owenpay.BaseTestClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import swaggergen.model.CreateUserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseTestClass {

    @Before
    public void setUp() {

        super.setUp();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() {
        super.after();
    }

    @Test
    public void registerValidUserThenSuccess() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(USERNAME);
        createUserRequest.setPassword(PASSWORD);

        mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserWithoutUsernameThenExpectBadRequest() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setPassword(PASSWORD);

        mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserWithoutPasswordThenExpectBadRequest() throws Exception {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(USERNAME);

        mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }
}
