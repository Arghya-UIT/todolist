package com.example.todolist.database;

public class TaskModel {
    private int id;
    private String title;
    private String description;
    private String time_for_store;
    private String date_for_store;
    private String priority;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime_for_store() {
        return time_for_store;
    }

    public void setTime_for_store(String time_for_store) {
        this.time_for_store = time_for_store;
    }

    public String getDate_for_store() {
        return date_for_store;
    }

    public void setDate_for_store(String date_for_store) {
        this.date_for_store = date_for_store;
    }


}
