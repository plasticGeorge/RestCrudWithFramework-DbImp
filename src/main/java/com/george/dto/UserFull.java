package com.george.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFull {
    @JsonProperty("user_id")
    @JsonAlias({"id"})
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("task")
    private TaskFull task;

    public UserFull() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskFull getTask() {
        return task;
    }

    public void setTask(TaskFull task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "UserFull{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", task=" + task +
                '}';
    }
}
