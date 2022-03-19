package com.levelup.web.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 25)
    @NotEmpty(message = "user's login can't be empty")
    @Email(message = "format must be in format: ****@***.***")
    private String login;

    @Column(nullable = false, length = 250)
    @NotEmpty(message = "passPhrase can't be blank")
    private String passPhrase;

    @Enumerated(EnumType.STRING)
    private UserStates status;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Temporal(TemporalType.DATE)
    private Date created;

    @OneToMany(mappedBy = "author")
    private List<Question> listOfQuestions;

    @OneToMany(mappedBy = "author")
    private List<Comment> listOfComments;

    @OneToMany(mappedBy = "author")
    private List<Thumb> listOfThumbs;

    @OneToMany(mappedBy = "author")
    private List<Answer> listOfAnswers;

    public User() {
    }

    public User(String login, String passPhrase, UserRoles userRole) {
        this.login = login;
        this.passPhrase = passPhrase;
        this.role = userRole;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public List<Thumb> getListOfLikes() {
        return listOfThumbs;
    }

    public void setListOfLikes(List<Thumb> listOfThumbs) {
        this.listOfThumbs = listOfThumbs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public List<Comment> getListOfComments() {
        return listOfComments;
    }

    public void setListOfComments(List<Comment> listOfComments) {
        this.listOfComments = listOfComments;
    }

    public List<Answer> getListOfAnswers() {
        return listOfAnswers;
    }

    public void setListOfAnswers(List<Answer> listOfAnswers) {
        this.listOfAnswers = listOfAnswers;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserStates getStatus() {
        return status;
    }

    public void setStatus(UserStates status) {
        this.status = status;
    }

    public List<Thumb> getListOfThumbs() {
        return listOfThumbs;
    }

    public void setListOfThumbs(List<Thumb> listOfThumbs) {
        this.listOfThumbs = listOfThumbs;
    }
}
