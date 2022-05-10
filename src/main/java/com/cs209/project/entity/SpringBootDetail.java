package com.cs209.project.entity;

import java.util.ArrayList;

public class SpringBootDetail {
    private String id;
    private String version;
    private ArrayList<String> tag;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpringBootDetail)) return false;

        SpringBootDetail that = (SpringBootDetail) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getVersion() != null ? !getVersion().equals(that.getVersion()) : that.getVersion() != null) return false;
        if (getTag() != null ? !getTag().equals(that.getTag()) : that.getTag() != null) return false;
        return getTime() != null ? getTime().equals(that.getTime()) : that.getTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        result = 31 * result + (getTag() != null ? getTag().hashCode() : 0);
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpringBootDetail{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", tag=" + tag +
                ", time='" + time + '\'' +
                '}';
    }
}
