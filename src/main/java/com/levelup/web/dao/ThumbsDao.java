package com.levelup.web.dao;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Thumb;
import com.levelup.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ThumbsDao {
    @Autowired
    EntityManager manager;

    public ThumbsDao(){}

    public List<Thumb> findAll() {
        return manager.createQuery("from Thumb", Thumb.class).getResultList();
    }

    public List<Thumb> findByAuthor(User user) {
        return manager.createQuery("from Thumb thumb where thumb.author.id = :userParams", Thumb.class)
                .setParameter("userParams", user.getId())
                .getResultList();
    }

    public List<Thumb> findByAnswer(Answer answer) {
        return manager.createQuery("from Thumb thumb where thumb.answer.id = :answerParams", Thumb.class)
                .setParameter("answerParams", answer.getId())
                .getResultList();
    }

}
