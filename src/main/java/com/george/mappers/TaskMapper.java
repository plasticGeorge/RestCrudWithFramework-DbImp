package com.george.mappers;

import com.george.dto.TaskFull;
import com.george.dto.TaskLongId;
import com.george.entities.Task;
import com.george.repositories.TaskRepository;
import com.george.repositories.UserRepository;
import com.george.support.InvalidIdentifierException;
import com.george.support.RequiredFieldMissingException;
import com.george.support.WrongParentException;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskMapper(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task mapToEntity(TaskLongId taskLongId) throws InvalidIdentifierException, RequiredFieldMissingException, WrongParentException {
        if(taskLongId.getId() != null && taskLongId.getParentTask().equals(taskLongId.getId()))
            throw new WrongParentException(taskLongId.getId(), taskLongId.getParentTask());
        if(taskLongId.getDescription() == null)
            throw new RequiredFieldMissingException("description");
        Task task = new Task();
        task.setId(taskLongId.getId());
        task.setDescription(taskLongId.getDescription());
        if(taskLongId.getParentTask() != null) {
            if (taskRepository.existsById(taskLongId.getParentTask()))
                task.setParentTask(taskRepository.findById(taskLongId.getParentTask()).orElse(null));
            else
                throw new InvalidIdentifierException("Task", taskLongId.getParentTask());
        }
        if(taskLongId.getUser() != null) {
            if (userRepository.existsById(taskLongId.getUser()))
                task.setUser(userRepository.findById(taskLongId.getUser()).orElse(null));
            else
                throw new InvalidIdentifierException("User", taskLongId.getUser());
        }
        return task;
    }

    public TaskLongId mapToLongId(Task task){
        TaskLongId taskLongId = new TaskLongId();
        taskLongId.setId(task.getId());
        taskLongId.setDescription(task.getDescription());
        if(task.getParentTask() != null)
            taskLongId.setParentTask(task.getParentTask().getId());
        return taskLongId;
    }

    public TaskFull mapToFull(Task task){
        TaskFull taskFull = new TaskFull();
        taskFull.setId(task.getId());
        taskFull.setDescription(task.getDescription());
        if(task.getParentTask() != null)
            taskFull.setParentTask(mapToFull(task.getParentTask()));
        return taskFull;
    }
}
