package com.levelup.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Answers")
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 255)
    private String body;

    @Temporal(TemporalType.DATE)
    private Date created;

    @OneToMany(mappedBy = "answer")
    private List<Comment> listOfComments;

    @OneToMany(mappedBy = "answer")
    private List<Like> listOfLikes;

    @ManyToOne
    private User author;

    @ManyToOne
    private Question question;

    public List<Comment> getListOfComments() {
        return listOfComments;
    }

    public List<Like> getListOfLikes() {
        return listOfLikes;
    }

    public void setListOfLikes(List<Like> listOfLikes) {
        this.listOfLikes = listOfLikes;
    }

    public void setListOfComments(List<Comment> listOfComments) {
        this.listOfComments = listOfComments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Answer() {
    }

    public Answer(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
