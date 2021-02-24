package com.levelup.tests;

import com.levelup.model.*;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerTest {
    private EntityManagerFactory factory;
    private EntityManager manager;

    @Before
    public void initFactoryAndManager(){
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
    }

    @Test
    public void smokeTestUser() {
        try {
            manager.getTransaction().begin();
            User user = new User("AdmintTest", "@adminTest", true);
            manager.persist(user);
            manager.getTransaction().commit();
        } finally {
            manager.close();
            factory.close();
        }
    }

    @Test
    public void smokeTestQuestion() {
        try {
            manager.getTransaction().begin();
            Question question = new Question("Test title", "Test body question");
            manager.persist(question);
            manager.getTransaction().commit();
        } finally {
            manager.close();
            factory.close();
        }
    }

    @Test
    public void smokeTestAnswer() {
        try {
            manager.getTransaction().begin();
            Answer answer = new Answer("Test body answer");
            manager.persist(answer);
            manager.getTransaction().commit();
        } finally {
            manager.close();
            factory.close();
        }
    }

    @Test
    public void smokeTestComment() {
        try {
            manager.getTransaction().begin();
            Comment comment = new Comment("Test body comment");
            manager.persist(comment);
            manager.getTransaction().commit();
        } finally {
            manager.close();
            factory.close();
        }
    }

    @Test
    public void smokeTestLike() {
        try {
            manager.getTransaction().begin();
            Like like = new Like();
            manager.persist(like);
            manager.getTransaction().commit();
        } finally {
            manager.close();
            factory.close();
        }
    }
}
