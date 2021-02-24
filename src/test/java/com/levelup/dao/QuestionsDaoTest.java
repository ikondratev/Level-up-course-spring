package com.levelup.dao;

import com.levelup.model.Question;
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

public class QuestionsDaoTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private String base = System.getProperty("test_base");
    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);
    private QuestionsDao questionsDao;


    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory(base);
        manager = factory.createEntityManager();
        User authorFirst = new User("loginFirst", "passFirst", false);
        User authorSecond = new User("loginSecond", "passSecond", false);
        Question questionFirst = new Question("TestTitleFirst", "TestBodyFirst");
        questionFirst.setCreated(date);
        Question questionSecond = new Question("TestTitleSecond ", "TestBodySecond");
        questionsDao = new QuestionsDao(manager);

        manager.getTransaction().begin();
        manager.persist(authorFirst);
        manager.persist(authorSecond);
        questionFirst.setAuthor(authorFirst);
        questionSecond.setAuthor(authorSecond);
        manager.persist(questionFirst);
        manager.persist(questionSecond);
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
        assertEquals(questionsDao.findAll().size(), 2);
    }

    @Test
    public void findByTitle() {
        assertNull(questionsDao.findByTitle("WrongTitle"));
        Question foundQuestion = questionsDao.findByTitle("TestTitleFirst");
        assertNotNull(foundQuestion);
        assertEquals("TestTitleFirst", foundQuestion.getTitle());
    }

    @Test
    public void findByAuthor() {
        List<Question> wrongAuthorList = questionsDao.findByAuthor("wrongAuthorLogin");
        assertEquals(0, wrongAuthorList.size());
        List<Question> foundAuthorsList = questionsDao.findByAuthor("loginFirst");
        assertEquals( "TestTitleFirst", foundAuthorsList.get(0).getTitle());
    }

    @Test
    public void testFindByDateBefore() {
        assertEquals(1, questionsDao.findByDateBefore(date).size());
        assertEquals("TestTitleFirst", questionsDao.findByDateBefore(date).get(0).getTitle());
        assertEquals(0, questionsDao.findByDateBefore(dateBefore).size());
    }
}