package com.levelup.web.service;

import com.levelup.web.repo.AnswersRepository;
import com.levelup.web.repo.CommentsRepository;
import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private AnswersRepository answersRepository;

    public Comment addComment(String answerId, String bodyComment) {
        Optional<Answer> updatedAnswers = answersRepository.findById(Long.parseLong(answerId));

        List<Comment> commentsList;

        if (updatedAnswers.isPresent()) {
            Comment comment = new Comment(bodyComment);
            comment.setAnswer(updatedAnswers.get());
            commentsRepository.save(comment);
            commentsList = updatedAnswers.get().getListOfComments();
            commentsList.add(comment);
            updatedAnswers.get().setListOfComments(commentsList);
            answersRepository.save(updatedAnswers.get());
            return comment;
        } else {
            return null;
        }
    }
}
