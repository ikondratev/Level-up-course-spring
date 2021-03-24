package com.levelup.web.service;

import com.levelup.web.model.User;
import com.levelup.web.model.UserRoles;
import com.levelup.web.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User findByLogin(String login) {
        return usersRepository.findByLogin(login);
    }

    public boolean save(String login, String password, String confirmationPassword) {
        if (password.equals(confirmationPassword) && usersRepository.findByLogin(login) == null) {
            User newUser = new User(
                    login,
                    encoder.encode(password), UserRoles.USER
            );
            usersRepository.save(newUser);
            return true;
        } else {
            return false;
        }
    }
}
