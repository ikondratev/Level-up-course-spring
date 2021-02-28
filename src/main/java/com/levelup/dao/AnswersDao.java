package com.levelup.dao;

import com.levelup.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class AnswersDao {

    @Autowired
    private EntityManager manager;

    public AnswersDao() { }

    public List<Answer> findAll(){
        return manager.createQuery("from Answer", Answer.class)
                .getResultList();
    }

    public List<Answer> findByAuthor(String login) {
        return manager.createQuery("from Answer answer where answer.author.login = :loginParams", Answer.class)
                .setParameter("loginParams", login)
                .getResultList();
    }

    public List<Answer> findByQuestion(String title) {
        return manager.createQuery("from Answer answer where answer.question.title = :titleParams", Answer.class)
                .setParameter("titleParams", title)
                .getResultList();
    }

    public List<Answer> findByCreatedBefore(Date date) {
        return manager.createQuery("from Answer where created <= :dateParams", Answer.class)
                .setParameter("dateParams", date)
                .getResultList();
    }


}
