package com.cs209.project.entity;
import java.io.Serializable;

public class SpringBootBug implements Serializable {
    private Integer id;
    private String bug;
    private String version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBug() {
        return bug;
    }

    public void setBug(String bug) {
        this.bug = bug;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpringBootBug)) return false;

        SpringBootBug that = (SpringBootBug) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getBug() != null ? !getBug().equals(that.getBug()) : that.getBug() != null) return false;
        return getVersion() != null ? getVersion().equals(that.getVersion()) : that.getVersion() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getBug() != null ? getBug().hashCode() : 0);
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpringBootBug{" +
                "id=" + id +
                ", bug='" + bug + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
