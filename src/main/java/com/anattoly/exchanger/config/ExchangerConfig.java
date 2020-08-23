package com.anattoly.exchanger.config;

import com.anattoly.exchanger.repository.UserRepository;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.anattoly.exchanger.repository")
public class ExchangerConfig {
    private final UserRepository userRepository;

    public ExchangerConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
