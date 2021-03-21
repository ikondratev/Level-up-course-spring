package com.levelup.web.service;

import com.levelup.web.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userName.equals("admin")) {
            return User.withUsername("admin")
                    .password(encoder.encode("admin"))
                    .roles("ADMIN", "USER")
                    .build();
        }

        com.levelup.web.model.User foundedUser = usersRepository.findByLogin(userName);
        if (foundedUser == null) {
            throw new UsernameNotFoundException("User" + userName + "not found");
        }

        return User.withUsername(userName)
                .password(foundedUser.getPassPhrase())
                .roles("USER")
                .build();
    }
}
