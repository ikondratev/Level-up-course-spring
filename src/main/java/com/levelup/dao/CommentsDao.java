package com.levelup.dao;

import com.levelup.model.Answer;
import com.levelup.model.Comment;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class CommentsDao {
    EntityManager manager;

    public CommentsDao(EntityManager manager) {
        this.manager = manager;
    }

    public List<Comment> findAll() {
        return manager.createQuery("from Comment", Comment.class)
                .getResultList();
    }

    public List<Comment> findByAuthor(String login) {
        return manager.createQuery("from Comment comment where comment.author.login = :loginParams", Comment.class)
                .setParameter("loginParams", login)
                .getResultList();
    }

    public List<Comment> findByAnswerId(Answer answer) {
        return manager.createQuery("from Comment comment where comment.answer.id = :answerParams", Comment.class)
                .setParameter("answerParams", answer.getId())
                .getResultList();
    }

    public List<Comment> findByCreatedBefore(Date date) {
        return manager.createQuery("from Comment where created <= :dateParams", Comment.class)
                .setParameter("dateParams", date)
                .getResultList();
    }
}
