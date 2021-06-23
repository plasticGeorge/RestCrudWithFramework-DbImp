package com.george.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ProjectFull {
    @JsonProperty("project_id")
    @JsonAlias({"id"})
    private Long id;

    @JsonProperty("description")
    @JsonAlias({"desc"})
    private String description;

    @JsonProperty("users")
    private Set<UserFull> users;

    public ProjectFull() {
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

    public Set<UserFull> getUsers() {
        return users;
    }

    public void setUsers(Set<UserFull> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ProjectFull{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
