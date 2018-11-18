package com.pentavex.owenpay.controller;

import com.pentavex.owenpay.domain.User;
import com.pentavex.owenpay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swaggergen.controller.UserApi;
import swaggergen.model.CreateUserRequest;
import swaggergen.model.GeneralResponse;

import javax.validation.Valid;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<GeneralResponse> createUser(@Valid @RequestBody final CreateUserRequest request) {

        User newUser = new User(request.getUsername(), request.getPassword());

        User savedUser = userService.saveOrUpdateUserForm(newUser);

        GeneralResponse response = new GeneralResponse();
        response.setMessage(savedUser.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<GeneralResponse> getUsers() {
        GeneralResponse response = new GeneralResponse();
        response.setMessage("{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"},"
                + "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
