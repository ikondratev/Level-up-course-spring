package com.levelup.web.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Questions")
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @NotEmpty(message = "Please add title")
    private String title;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String body;

    @Temporal(TemporalType.DATE)
    private Date created;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "question")
    private List<Answer> listOfAnswer;

    public Question() {
        this.listOfAnswer = new ArrayList<>();
    }

    public Question(String title, String body) {
        this.listOfAnswer = new ArrayList<>();
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Answer> getListOfAnswer() {
        return listOfAnswer;
    }

    public void setListOfAnswer(List<Answer> listOfAnswer) {
        this.listOfAnswer = listOfAnswer;
    }
}
