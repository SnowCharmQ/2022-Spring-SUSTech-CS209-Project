package com.cs209.project.entity;

import java.io.Serializable;

public class SpringBootIteration implements Serializable {
    private String version;
    private String time;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpringBootIteration)) return false;

        SpringBootIteration that = (SpringBootIteration) o;

        if (getVersion() != null ? !getVersion().equals(that.getVersion()) : that.getVersion() != null) return false;
        return getTime() != null ? getTime().equals(that.getTime()) : that.getTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getVersion() != null ? getVersion().hashCode() : 0;
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpringBootIteration{" +
                "version='" + version + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
