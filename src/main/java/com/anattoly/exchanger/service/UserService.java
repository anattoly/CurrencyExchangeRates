package com.anattoly.exchanger.service;

import com.anattoly.exchanger.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean saveUser(User user);
}
