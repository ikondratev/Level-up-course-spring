package com.levelup.web.repo;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    @Query("from Comment comment where comment.author.login = :loginParams")
    public List<Comment> findByAuthorLogin(@Param("loginParams") String login);
    @Query("from Comment comment where comment.answer.id = :answerId")
    public List<Comment> findByAnswerId(@Param("answerId") Long answerId);
    public List<Comment> findByCreatedIsLessThanEqual(Date date);
}
