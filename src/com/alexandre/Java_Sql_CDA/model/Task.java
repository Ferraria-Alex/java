package com.alexandre.Java_Sql_CDA.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private int id;
    private String title;
    private String content;
    private Date creationDate;
    private Date endDate;
    private boolean status;
    private User user;
    private final List<Category> categories;

    public Task(){
        this.categories = new ArrayList<>();
    }

    public Task(String title, String content, Date endDate, User user){
        this.title = title;
        this.content = content;
        this.endDate = endDate;
        this.user = user;
        this.categories = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", user=" + user.toString() +
                ", categories=" + categories +
                '}';
    }

    public void addCategory(Category category){
        try {
            if(this.categories.contains(category)){
                System.out.println("Category already exists");
                return;
            }
            this.categories.add(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public void removeCategory(Category category){
        try {
            if(!this.categories.contains(category)){
                System.out.println("Category doesn't exist");
                return;
            }
            this.categories.remove(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
}
