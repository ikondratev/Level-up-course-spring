package com.levelup.web.controller.partial;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class AddQuestionForm {

    @NotEmpty(message = "title can't be blank")
    @Length(max = 50)
    private String title;

    @NotEmpty(message = "body can't be blank")
    private String body;

    public AddQuestionForm() {
    }

    public AddQuestionForm(String title, String body) {
        this.title = title;
        this.body = body;
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
}
