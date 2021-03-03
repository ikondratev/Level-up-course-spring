package com.levelup.web.controller;

import com.levelup.web.dao.AnswersDao;
import com.levelup.web.dao.CommentsDao;
import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentsDao commentsDao;

    @Autowired
    private AnswersDao answersDao;

    public Comment createComment(String commentBody, Answer answer) {
        return  null;
    }

    public Comment addComment(String answerId, String bodyComment) {
        Answer updatedAnswer = answersDao.findById(Long.parseLong(answerId));
        List<Comment> commentsList;

        if (updatedAnswer != null) {
            Comment comment = new Comment(bodyComment);
            comment.setAnswer(updatedAnswer);
            commentsDao.save(comment);
            commentsList = updatedAnswer.getListOfComments();
            commentsList.add(comment);
            updatedAnswer.setListOfComments(commentsList);
            answersDao.update(updatedAnswer);
            return comment;
        } else {
            return null;
        }
    }
}
