package com.cs209.project.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class MyBatisIssue implements Serializable {
    String version;
    Date date;
    int year;
    String info;

    public MyBatisIssue(String version, Date date, int year, String info) {
        this.version = version;
        this.date = date;
        this.year = year;
        this.info = info;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyBatisIssue that = (MyBatisIssue) o;
        return year == that.year && Objects.equals(version, that.version) && Objects.equals(date, that.date) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, date, year, info);
    }

    @Override
    public String toString() {
        return "MyBatisIssue{" +
                "version='" + version + '\'' +
                ", date=" + date +
                ", year=" + year +
                ", info='" + info + '\'' +
                '}';
    }
}
