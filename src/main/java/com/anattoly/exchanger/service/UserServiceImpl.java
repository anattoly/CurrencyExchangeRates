package com.anattoly.exchanger.service;

import com.anattoly.exchanger.model.Role;
import com.anattoly.exchanger.model.User;
import com.anattoly.exchanger.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByLogin(username);
    }

    public boolean saveUser(User user) {
        User addToDB = userRepo.findUserByLogin(user.getLogin());

        if (addToDB != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(Collections.singleton(Role.USER));

        userRepo.save(user);

        return true;
    }
}
