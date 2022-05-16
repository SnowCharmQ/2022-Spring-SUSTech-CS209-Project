package com.cs209.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class SpringBootIteration implements Serializable {
    private String version;
    private String time;

    private int year;
    private int month;
    private int day;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpringBootIteration that = (SpringBootIteration) o;
        return year == that.year && month == that.month && day == that.day && Objects.equals(version, that.version) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, time, year, month, day);
    }

    @Override
    public String toString() {
        return "SpringBootIteration{" +
                "version='" + version + '\'' +
                ", time='" + time + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
