package com.george.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskFull {
    @JsonProperty("tack_id")
    @JsonAlias({"id"})
    private Long id;

    @JsonProperty("description")
    @JsonAlias({"desc"})
    private String description;

    @JsonProperty("parent_task_id")
    @JsonAlias({"parent", "parent_task"})
    private TaskFull parentTask;

    public TaskFull() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskFull getParentTask() {
        return parentTask;
    }

    public void setParentTask(TaskFull parentTask) {
        this.parentTask = parentTask;
    }

    @Override
    public String toString() {
        return "TaskFull{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", parentTask=" + parentTask +
                '}';
    }
}
