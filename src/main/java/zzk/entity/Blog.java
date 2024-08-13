package zzk.entity;

import java.util.List;
import java.util.Map;

public class Blog {
    private int id;
    private String title;
    private User author;
    private String body;
    private List<Common> commons;

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

    public List<Common> getCommons() {
        return commons;
    }

    public void setCommons(List<Common> commons) {
        this.commons = commons;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
