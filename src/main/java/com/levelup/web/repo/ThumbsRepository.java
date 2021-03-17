package com.levelup.web.repo;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Thumb;
import com.levelup.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface ThumbsRepository extends JpaRepository<Thumb, Long> {
    @Query("from Thumb thumb where thumb.author.id = :userId")
    public List<Thumb> findByAuthorId(@Param("userId") Long userId);
    @Query("from Thumb thumb where thumb.answer.id = :answerId")
    public List<Thumb> findByAnswerId(@Param("answerId") Long answerId);

}
