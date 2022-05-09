package com.cs209.project.entity;

import java.io.Serializable;

public class stackoverflow implements Serializable {
    private Integer id;
    private String tag;
    private Integer AllQuestion;
    private Integer today;
    private Integer week;
    private Integer month;
    private Integer year;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getAllQuestion() {
        return AllQuestion;
    }

    public void setAllQuestion(Integer allQuestion) {
        AllQuestion = allQuestion;
    }

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof stackoverflow)) return false;

        stackoverflow that = (stackoverflow) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTag() != null ? !getTag().equals(that.getTag()) : that.getTag() != null) return false;
        if (getAllQuestion() != null ? !getAllQuestion().equals(that.getAllQuestion()) : that.getAllQuestion() != null)
            return false;
        if (getToday() != null ? !getToday().equals(that.getToday()) : that.getToday() != null) return false;
        if (getWeek() != null ? !getWeek().equals(that.getWeek()) : that.getWeek() != null) return false;
        if (getMonth() != null ? !getMonth().equals(that.getMonth()) : that.getMonth() != null) return false;
        return getYear() != null ? getYear().equals(that.getYear()) : that.getYear() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTag() != null ? getTag().hashCode() : 0);
        result = 31 * result + (getAllQuestion() != null ? getAllQuestion().hashCode() : 0);
        result = 31 * result + (getToday() != null ? getToday().hashCode() : 0);
        result = 31 * result + (getWeek() != null ? getWeek().hashCode() : 0);
        result = 31 * result + (getMonth() != null ? getMonth().hashCode() : 0);
        result = 31 * result + (getYear() != null ? getYear().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "stackoverflow{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", AllQuestion=" + AllQuestion +
                ", today=" + today +
                ", week=" + week +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
    public stackoverflow(){
        this.today = 0;
        this.month = 0;
        this.week = 0;
        this.year = 0;
        this.AllQuestion = 0;
    }
}
