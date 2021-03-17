package com.levelup.web.repo;

import com.levelup.web.model.User;
import com.levelup.web.model.UserStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    public List<User> findByLogin(String login);
    public List<User> findByStatus(UserStates state);
    public List<User> findByLoginAndPassword(String login, String password);
    public List<User> findByIsAdmin(boolean isAdmin);
    public List<User> findByCreatedIsLessThanEqual(Date date);

}
