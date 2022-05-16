package com.cs209.project.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class SpringBootIssue implements Serializable {
    String version;
    Date publishDate;
    int year;
    int month;
    String info;

    public SpringBootIssue(String version, Date publishDate, int year, int month, String info) {
        this.version = version;
        this.publishDate = publishDate;
        this.info = info;
        this.year = year;
        this.month = month;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
        SpringBootIssue that = (SpringBootIssue) o;
        return year == that.year && month == that.month && Objects.equals(version, that.version) && Objects.equals(publishDate, that.publishDate) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, publishDate, year, month, info);
    }
}
