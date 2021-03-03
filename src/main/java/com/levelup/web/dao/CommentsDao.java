package com.levelup.web.dao;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class CommentsDao {
    @Autowired
    EntityManager manager;

    public CommentsDao() { }

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

    public void save(Comment comment) {
        manager.getTransaction().begin();
        manager.persist(comment);
        manager.getTransaction().commit();
    }
}
