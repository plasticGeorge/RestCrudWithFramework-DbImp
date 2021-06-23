package com.george.mappers;

import com.george.dto.ProjectFull;
import com.george.dto.ProjectLongId;
import com.george.entities.Project;
import com.george.support.RequiredFieldMissingException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    private final UserMapper userMapper;

    public ProjectMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Project mapToEntity(ProjectLongId projectLongId) throws RequiredFieldMissingException {
        if(projectLongId.getDescription() == null)
            throw new RequiredFieldMissingException("description");
        Project project = new Project();
        project.setId(projectLongId.getId());
        project.setDescription(projectLongId.getDescription());
        return project;
    }

    public ProjectLongId mapToLongId(Project project){
        ProjectLongId projectLongId = new ProjectLongId();
        projectLongId.setId(project.getId());
        projectLongId.setDescription(project.getDescription());
        return projectLongId;
    }

    public ProjectFull mapToFull(Project project){
        ProjectFull projectFull = new ProjectFull();
        projectFull.setId(project.getId());
        projectFull.setDescription(project.getDescription());
        if(project.getUsers() != null)
            projectFull.setUsers(project.getUsers().stream()
                                        .map(userMapper::mapToFull)
                                        .collect(Collectors.toSet()));
        return projectFull;
    }
}
