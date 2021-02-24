package com.levelup.dao;

import com.levelup.model.Answer;
import com.levelup.model.Thumb;
import com.levelup.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ThumbsDaoTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private String base = System.getProperty("test_base");
    private ThumbsDao thumbsDao;
    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 1000000000);
    private User author;
    private Answer testAnswer;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory(base);
        manager = factory.createEntityManager();
        thumbsDao = new ThumbsDao(manager);

        manager.getTransaction().begin();
        testAnswer = new Answer("TestBodyAnswer");
        manager.persist(testAnswer);
        author = new User("testLoginUser", "testPassUser", false);
        manager.persist(author);
        Thumb firstThumb = new Thumb();
        Thumb secondThumb = new Thumb();
        firstThumb.setAuthor(author);
        secondThumb.setAuthor(author);
        firstThumb.setAnswer(testAnswer);
        manager.persist(firstThumb);
        manager.persist(secondThumb);
        manager.getTransaction().commit();
    }

    @After
    public void tearDown() throws Exception {
        if (factory != null) {
            factory.close();
        }
        if (manager != null) {
            manager.close();
        }
    }

    @Test
    public void findAll() {
        assertEquals(2, thumbsDao.findAll().size());
    }

    @Test
    public void findByAuthor() {
        List<Thumb> foundThumbsList = thumbsDao.findByAuthor(author);
        assertEquals(2, foundThumbsList.size());

        User wrongAuthor = new User("wrongLogin", "wrongPassword", false);
        List<Thumb> emptyList = thumbsDao.findByAuthor(wrongAuthor);
        assertEquals(0, emptyList.size());
    }

    @Test
    public void findByAnswer() {
        List<Thumb> foundThumbsList = thumbsDao.findByAnswer(testAnswer);
        assertEquals(1, foundThumbsList.size());

        Answer wrongAnswer = new Answer("WrongBodyAnswer");
        List<Thumb> emptyList = thumbsDao.findByAnswer(wrongAnswer);
        assertEquals(0, emptyList.size());
    }
}