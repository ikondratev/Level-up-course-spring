package com.levelup.dao;

import com.levelup.model.Answer;
import com.levelup.model.Thumb;
import com.levelup.model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class ThumbsDao {
    EntityManager manager;

    public ThumbsDao(EntityManager manager) {
        this.manager = manager;
    }

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
