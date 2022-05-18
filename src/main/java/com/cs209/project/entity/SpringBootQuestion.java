package com.cs209.project.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class SpringBootQuestion implements Serializable {
    String question;
    Date date;
    int views;
    int answers;
    String href;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpringBootQuestion that = (SpringBootQuestion) o;
        return views == that.views && answers == that.answers && Objects.equals(question, that.question) && Objects.equals(date, that.date) && Objects.equals(href, that.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, date, views, answers, href);
    }

    @Override
    public String toString() {
        return "SpringBootQuestion{" +
                "question='" + question + '\'' +
                ", date=" + date +
                ", views=" + views +
                ", answers=" + answers +
                ", href='" + href + '\'' +
                '}';
    }
}
