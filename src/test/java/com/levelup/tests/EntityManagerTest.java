package com.levelup.tests;

import com.levelup.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private String base = System.getProperty("test_base");

    @Before
    public void initFactoryAndManager(){
        factory = Persistence.createEntityManagerFactory(base);
        manager = factory.createEntityManager();
    }

    @After
    public void cleanResources() {
        if (factory != null) {
            factory.close();
        }
        if (manager != null) {
            manager.close();
        }
    }

    @Test
    public void smokeTestUser() {
        try {
            manager.getTransaction().begin();
            User user = new User("AdmintTest", "@adminTest", true);
            manager.persist(user);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Test
    public void smokeTestQuestion() {
        try {
            manager.getTransaction().begin();
            Question question = new Question("Test title", "Test body question");
            manager.persist(question);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Test
    public void smokeTestAnswer() {
        try {
            manager.getTransaction().begin();
            Answer answer = new Answer("Test body answer");
            manager.persist(answer);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Test
    public void smokeTestComment() {
        try {
            manager.getTransaction().begin();
            Comment comment = new Comment("Test body comment");
            manager.persist(comment);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Test
    public void smokeTestLike() {
        try {
            manager.getTransaction().begin();
            Thumb thumb = new Thumb();
            manager.persist(thumb);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
