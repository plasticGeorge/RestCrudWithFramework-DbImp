package com.george.mappers;

import com.george.dto.UserFull;
import com.george.dto.UserLongId;
import com.george.entities.User;
import com.george.repositories.ProjectRepository;
import com.george.support.InvalidIdentifierException;
import com.george.support.RequiredFieldMissingException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public UserMapper(ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    public User mapToEntity(UserLongId userLongId) throws InvalidIdentifierException, RequiredFieldMissingException {
        if(userLongId.getName() == null)
            throw new RequiredFieldMissingException("name");
        User user = new User();
        user.setId(userLongId.getId());
        user.setName(userLongId.getName());
        if(userLongId.getProject() != null) {
            if (projectRepository.existsById(userLongId.getProject()))
                user.setProject(projectRepository.findById(userLongId.getProject()).orElse(null));
            else
                throw new InvalidIdentifierException("Project", userLongId.getProject());
        }
        return user;
    }

    public UserLongId mapToLongId(User user){
        UserLongId userLongId = new UserLongId();
        userLongId.setId(user.getId());
        userLongId.setName(user.getName());
        if (user.getProject() != null)
            userLongId.setProject(user.getProject().getId());
        return userLongId;
    }

    public UserFull mapToFull(User user){
        UserFull userFull = new UserFull();
        userFull.setId(user.getId());
        userFull.setName(user.getName());
        if(user.getTask() != null)
            userFull.setTask(taskMapper.mapToFull(user.getTask()));
        return userFull;
    }
}
