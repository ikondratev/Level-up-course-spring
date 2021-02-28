package com.levelup.web.dao;

import com.levelup.web.model.Question;
import com.levelup.web.model.User;
import com.levelup.tests.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class QuestionsDaoTest {
    @Autowired
    private EntityManager manager;

    @Autowired
    private QuestionsDao questionsDao;

    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);
    private Question testSaveQuestion;

    @Before
    public void setUp() throws Exception {
        User authorFirst = new User("loginFirst", "passFirst", false);
        User authorSecond = new User("loginSecond", "passSecond", false);
        Question questionFirst = new Question("TestTitleFirst", "TestBodyFirst");
        questionFirst.setCreated(date);
        Question questionSecond = new Question("TestTitleSecond ", "TestBodySecond");
        testSaveQuestion = new Question("testSaveTitleQuestion", "testSaveBodyQuestion");

        manager.getTransaction().begin();
        manager.persist(authorFirst);
        manager.persist(authorSecond);
        questionFirst.setAuthor(authorFirst);
        questionSecond.setAuthor(authorSecond);
        manager.persist(questionFirst);
        manager.persist(questionSecond);
        manager.getTransaction().commit();
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

    @Test
    public void saveQuestion() {
        questionsDao.save(testSaveQuestion);
        Question found = questionsDao.findByTitle("testSaveTitleQuestion");
        assertNotNull(found);
        assertEquals("testSaveTitleQuestion", found.getTitle());

    }
}