package com.greymatter.sprint.model;

public class Notification {

    private String title,description,date_created;

    public Notification(String title, String description, String date_created) {
        this.title = title;
        this.description = description;
        this.date_created = date_created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
