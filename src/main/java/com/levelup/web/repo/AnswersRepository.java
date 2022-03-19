package com.levelup.web.repo;

import com.levelup.web.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Long> {
    @Query("from Answer answer where answer.author.login = :loginParams")
    public List<Answer> findByAuthorLogin(@Param("loginParams") String loginParams);
    @Query("from Answer answer where answer.question.title = :titleParams")
    public List<Answer> findByQuestionTitle(@Param("titleParams") String titleParams);
    public List<Answer> findByCreatedIsLessThanEqual(Date date);
}
