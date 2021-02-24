package com.levelup.dao;

import com.levelup.model.User;
import com.levelup.model.UserStates;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public class UsersDao {

    private EntityManager manager;

    public UsersDao(EntityManager manager) {
        this.manager = manager;
    }

    public List<User> findAll() {
        return manager.createQuery("from User", User.class).getResultList();
    }

    public User findByLogin(String login) {
        try {
            return manager.createQuery(
                    "from User where login = :loginParam",
                    User.class
            ).setParameter("loginParam", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findByStates(UserStates state) {
        return manager.createQuery("from User where status = :userParams", User.class)
                .setParameter("userParams", state.toString())
                .getResultList();
    }

    public User findByLoginAndPassword(String login, String password) {
        try {
            return manager.createQuery(
                    "from User where login = :paramsLogin and password = :paramsPassword",
                    User.class
            ).setParameter("paramsLogin", login)
                    .setParameter("paramsPassword", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findByIsAdmin(boolean isAdmin) {
        return manager.createQuery(
                "from User where isAdmin = :isAdminParams",
                User.class
        ).setParameter("isAdminParams", isAdmin)
                .getResultList();
    }

    public List<User> findByCreatedBefore(Date date) {
        return manager.createQuery("from User where created <= :dateParams", User.class)
                .setParameter("dateParams", date)
                .getResultList();
    }

}
