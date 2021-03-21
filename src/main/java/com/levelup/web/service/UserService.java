package com.levelup.web.service;

import com.levelup.web.model.User;
import com.levelup.web.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    public User findByLogin(String login) {
        return usersRepository.findByLogin(login);
    }
}
