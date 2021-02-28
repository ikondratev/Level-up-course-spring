package com.levelup.tests;

import com.levelup.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EntityManagerTest {
    @Autowired
    private EntityManager manager;

    @Test
    public void smokeTestUser() {
        manager.getTransaction().begin();
        User user = new User("AdmintTest", "@adminTest", true);
        manager.persist(user);
        manager.getTransaction().commit();
        User found = manager.find(User.class, user.getId());
        Assert.assertNotNull(found);
    }

    @Test
    public void smokeTestQuestion() {
        manager.getTransaction().begin();
        Question question = new Question("Test title", "Test body question");
        manager.persist(question);
        manager.getTransaction().commit();
        Question found = manager.find(Question.class, question.getId());
        Assert.assertNotNull(found);
    }

    @Test
    public void smokeTestAnswer() {
        manager.getTransaction().begin();
        Answer answer = new Answer("Test body answer");
        manager.persist(answer);
        manager.getTransaction().commit();
        Answer found = manager.find(Answer.class, answer.getId());
        Assert.assertNotNull(found);
    }

    @Test
    public void smokeTestComment() {
        manager.getTransaction().begin();
        Comment comment = new Comment("Test body comment");
        manager.persist(comment);
        manager.getTransaction().commit();
        Comment found = manager.find(Comment.class, comment.getId());
        Assert.assertNotNull(found);
    }

    @Test
    public void smokeTestLike() {
        manager.getTransaction().begin();
        Thumb thumb = new Thumb();
        manager.persist(thumb);
        manager.getTransaction().commit();
        Thumb found = manager.find(Thumb.class, thumb.getId());
        Assert.assertNotNull(found);
    }
}
