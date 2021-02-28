package com.levelup.web.dao;

import com.levelup.web.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

@Repository
public class QuestionsDao {
    @Autowired
    private EntityManager manager;

    public QuestionsDao() { }

    public List<Question> findAll(){
        return manager.createQuery("from Question", Question.class).getResultList();
    }

    public Question findByTitle(String title) {
        try {
            return manager.createQuery("from Question where title = :paramsTitle", Question.class)
                    .setParameter("paramsTitle", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Question> findByAuthor(String author) {
        return manager.createQuery("from Question question where question.author.login = :authorLogin", Question.class)
                .setParameter("authorLogin", author)
                .getResultList();
    }

    public List<Question> findByDateBefore(Date date) {
        return manager.createQuery("from Question where created <= :dateParams", Question.class)
                .setParameter("dateParams", date)
                .getResultList();
    }

    public void save(Question question) {
        manager.getTransaction().begin();
        manager.persist(question);
        manager.getTransaction().commit();
    }
}