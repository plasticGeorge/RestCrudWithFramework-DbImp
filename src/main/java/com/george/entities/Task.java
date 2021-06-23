package com.george.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TASK")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "TASK_ID", nullable = false)
    @Basic
    @JsonProperty("tack_id")
    @JsonAlias({"id"})
    private Long id;

    @Column(name = "DESCRIPTION", nullable = false)
    @Basic
    @JsonProperty("description")
    @JsonAlias({"desc"})
    private String description;

    @JoinColumn (name = "USER_ID", referencedColumnName = "USER_ID")
    @OneToOne
    @JsonProperty(value = "user_id", access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias({"user", "executor"})
    private User user;

    @JoinColumn (name = "PARENT_TASK_ID", referencedColumnName = "TASK_ID")
    @ManyToOne
    @JsonProperty("parent_task_id")
    @JsonAlias({"parent", "parent_task"})
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask")
    @JsonProperty("child_tasks")
    @JsonAlias({"children"})
    private Set<Task> childTasks;

    public Task() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Set<Task> getChildTasks() {
        return childTasks;
    }

    public void setChildTasks(Set<Task> childTasks) {
        this.childTasks = childTasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", parentTask=" + parentTask +
                ", childTasks=" + childTasks +
                '}';
    }
}
