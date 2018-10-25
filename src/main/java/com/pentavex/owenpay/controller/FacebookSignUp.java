package com.pentavex.owenpay.controller;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        System.out.println(connection.getDisplayName());
        return connection.getDisplayName();
    }
}
