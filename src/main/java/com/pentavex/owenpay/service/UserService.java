package com.pentavex.owenpay.service;

import com.pentavex.owenpay.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> listAll();
    User getById(Long id);
    User saveOrUpdate(User user);
    void delete(Long id);
    User saveOrUpdateUserForm(User user);
    Long getUserIdByUsername(String username);
}
