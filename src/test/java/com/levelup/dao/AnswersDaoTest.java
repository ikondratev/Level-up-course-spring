package com.levelup.dao;

import com.levelup.model.Answer;
import com.levelup.model.Question;
import com.levelup.model.User;
import com.levelup.tests.TestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AnswersDaoTest {
    @Autowired
    private EntityManager manager;

    @Autowired
    private AnswersDao answersDao;

    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);

    @Before
    public void setUp() {
        manager.getTransaction().begin();
        Question question = new Question("TestTitle", "TestBody");
        manager.persist(question);
        User author = new User("testLogin", "testPasswoerd", false);
        manager.persist(author);
        Answer firstAnswer = new Answer("TestAnswerBodyFirst");
        Answer secondAnswer = new Answer("TestAnswerBodySecond");
        firstAnswer.setQuestion(question);
        firstAnswer.setAuthor(author);
        firstAnswer.setCreated(date);
        secondAnswer.setQuestion(question);
        manager.persist(firstAnswer);
        manager.persist(secondAnswer);
        manager.getTransaction().commit();
    }

    @Test
    public void findAll() {
        assertEquals(2,answersDao.findAll().size());
    }

    @Test
    public void findByAuthor() {
        assertEquals(0, answersDao.findByAuthor("wrongLogin").size());
        assertEquals("TestAnswerBodyFirst", answersDao.findByAuthor("testLogin").get(0).getBody());
    }

    @Test
    public void findByQuestion() {
        assertEquals(2, answersDao.findByQuestion("TestTitle").size());
    }

    @Test
    public void findByCreatedBefore() {
        List<Answer> foundList = answersDao.findByCreatedBefore(date);
        assertEquals(1, foundList.size());
        assertEquals("TestAnswerBodyFirst", foundList.get(0).getBody());

        List<Answer> emptyList = answersDao.findByCreatedBefore(dateBefore);
        assertEquals(0, emptyList.size());
    }
}