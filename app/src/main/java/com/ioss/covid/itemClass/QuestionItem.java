package com.ioss.covid.itemClass;

import java.util.ArrayList;
import java.util.List;

public class QuestionItem {

    private String id,question;
    private String type;
    private List<String> answers;

    public QuestionItem(String id, String question, String type) {
        this.id = id;
        this.question = question;
        this.type = type;
        answers=new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
