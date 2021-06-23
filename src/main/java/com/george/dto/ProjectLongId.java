package com.george.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectLongId {
    @JsonProperty("project_id")
    @JsonAlias({"id"})
    private Long id;

    @JsonProperty("description")
    @JsonAlias({"desc"})
    private String description;

    public ProjectLongId() {
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

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
