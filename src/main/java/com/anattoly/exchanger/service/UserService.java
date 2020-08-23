package com.anattoly.exchanger.service;

import com.anattoly.exchanger.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    boolean saveUser(User user);
}
