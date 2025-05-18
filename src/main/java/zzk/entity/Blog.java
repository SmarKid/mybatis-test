package zzk.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Blog implements Serializable {

    private int           id;
    private String        title;
    private User          author;
    private String        body;
    private List<Comment> comments;

    Map<String, String> labels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Comment> getCommons() {
        return comments;
    }

    public void setCommons(List<Comment> comments) {
        this.comments = comments;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
