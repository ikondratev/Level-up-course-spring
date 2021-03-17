package com.levelup.web.repo;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
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
public class CommentsRepositoryTest {
    @Autowired
    private EntityManager manager;

    @Autowired
    private CommentsRepository commentsRepository;

    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);
    private Answer testAnswer;

    @Before
    public void setUp() throws Exception {
        manager.getTransaction().begin();
        testAnswer = new Answer("testBodyAnswer");
        User author = new User("testLoginUser", "testPasswordUser", false);
        manager.persist(testAnswer);
        manager.persist(author);
        Comment firstComment = new Comment("FirstTestBodyComment");
        Comment secondComment = new Comment("SecondTestBodyComment");
        firstComment.setAuthor(author);
        firstComment.setAnswer(testAnswer);
        firstComment.setCreated(date);
        secondComment.setAnswer(testAnswer);
        manager.persist(firstComment);
        manager.persist(secondComment);
        manager.getTransaction().commit();
    }

    @Test
    public void findByAuthor() {
        List<Comment> foundCommentsList = commentsRepository.findByAuthorLogin("testLoginUser");
        assertEquals(1, foundCommentsList.size());
        assertEquals("FirstTestBodyComment", foundCommentsList.get(0).getBody());
    }

    @Test
    public void findByAnswerId() {
        List<Comment> foundCommentsList = commentsRepository.findByAnswerId(testAnswer.getId());
        assertEquals(2, foundCommentsList.size());
    }

    @Test
    public void findByCreatedBefore() {
        List<Comment> foundCommentsList = commentsRepository.findByCreatedIsLessThanEqual(date);
        assertEquals(1, foundCommentsList.size());
        assertEquals("FirstTestBodyComment", foundCommentsList.get(0).getBody());

        List<Comment> emptyList = commentsRepository.findByCreatedIsLessThanEqual(dateBefore);
        assertEquals(0, emptyList.size());
    }

    @Test
    public void save() {
        Answer testNewAnswer = new Answer("TestFirstBodyAnswer");

        manager.getTransaction().begin();
        manager.persist(testNewAnswer);
        manager.getTransaction().commit();

        Comment newComment = new Comment("TestBodyComment");
        newComment.setAnswer(testNewAnswer);
        commentsRepository.save(newComment);

        List<Comment> foundsList = commentsRepository.findByAnswerId(testNewAnswer.getId());

        assertNotNull(foundsList);
        assertEquals("TestBodyComment", foundsList.get(0).getBody());
    }
}