package com.levelup.web.repo;

import com.levelup.web.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {
    public Question findByTitle(String title);
    @Query("from Question question where question.author.login = :login")
    public List<Question> findByAuthorLogin(@Param("login") String login);
    public List<Question> findByCreatedIsLessThanEqual(Date date);
}