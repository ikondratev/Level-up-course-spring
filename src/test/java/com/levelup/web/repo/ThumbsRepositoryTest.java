package com.levelup.web.repo;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Thumb;
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
public class ThumbsRepositoryTest {
    @Autowired
    private EntityManager manager;

    @Autowired
    private ThumbsRepository thumbsRepository;

    private String base = System.getProperty("test_base");
    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 1000000000);
    private User author;
    private Answer testAnswer;

    @Before
    public void setUp() throws Exception {
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

    @Test
    public void findAll() {
        assertEquals(2, thumbsRepository.findAll().size());
    }

    @Test
    public void findByAuthor() {
        List<Thumb> foundThumbsList = thumbsRepository.findByAuthorId(author.getId());
        assertEquals(2, foundThumbsList.size());

        User wrongAuthor = new User("wrongLogin", "wrongPassword", false);
        List<Thumb> emptyList = thumbsRepository.findByAuthorId(wrongAuthor.getId());
        assertEquals(0, emptyList.size());
    }

    @Test
    public void findByAnswer() {
        List<Thumb> foundThumbsList = thumbsRepository.findByAnswerId(testAnswer.getId());
        assertEquals(1, foundThumbsList.size());

        Answer wrongAnswer = new Answer("WrongBodyAnswer");
        List<Thumb> emptyList = thumbsRepository.findByAnswerId(wrongAnswer.getId());
        assertEquals(0, emptyList.size());
    }
}