package com.cs209.project.entity;

import java.io.Serializable;
import java.util.Objects;

public class SpringBootIssueVersion implements Serializable {
    String version;
    int year;
    int month;
    int count;

    public SpringBootIssueVersion() {
    }

    public SpringBootIssueVersion(String version, int year, int month, int count) {
        this.version = version;
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTime() {
        if (this.month < 10) return year + "-0" + month;
        else return year + "-" + month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpringBootIssueVersion that = (SpringBootIssueVersion) o;
        return year == that.year && month == that.month && count == that.count && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, year, month, count);
    }

    @Override
    public String toString() {
        return String.format("[\"%s\",%d,\"%d-%d\"]", version, count, year, month);
    }
}
