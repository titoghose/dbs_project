package com.example.android.requiry;

import java.util.Date;

/**
 * Created by tito on 26/3/17.
 */
public class Discussions {
    private String project;
    private String username;
    private String message;
    private String date;

    public Discussions() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discussions that = (Discussions) o;

        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = project != null ? project.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Discussions{" +
                ", project='" + project + '\'' +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public Discussions(String project, String username, String message, String date) {
        this.project = project;
        this.username = username;
        this.message = message;
        this.date = date;
    }


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
