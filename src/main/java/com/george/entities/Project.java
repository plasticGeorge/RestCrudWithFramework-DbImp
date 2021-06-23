package com.george.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID", nullable = false)
    @Basic
    @JsonProperty("project_id")
    @JsonAlias({"id"})
    private Long id;

    @Column(name = "DESCRIPTION", nullable = false)
    @Basic
    @JsonProperty("description")
    @JsonAlias({"desc"})
    private String description;

    @OneToMany(mappedBy = "project")
    @JsonProperty("users")
    private Set<User> users;

    public Project() {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
