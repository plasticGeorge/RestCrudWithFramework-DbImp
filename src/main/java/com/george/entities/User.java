package com.george.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    @Basic
    @JsonProperty("user_id")
    @JsonAlias({"id"})
    private Long id;

    @Column(name = "NAME", nullable = false)
    @Basic
    @JsonProperty("name")
    private String name;

    @JoinColumn (name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    @JsonProperty(value = "project_id", access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias({"project"})
    private Project project;

    @OneToOne(mappedBy = "user")
    private Task task;

    public User() {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", task=" + task +
                '}';
    }
}
