package com.ioss.covid.itemClass;

public class SymptomsItem {
    private String id,question,ans;

    public SymptomsItem(String id, String question, String ans) {
        this.id = id;
        this.question = question;
        this.ans = ans;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
