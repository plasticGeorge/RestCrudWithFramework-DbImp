package com.george.services;

import com.george.dto.TaskFull;
import com.george.dto.TaskLongId;
import com.george.entities.Task;
import com.george.mappers.TaskMapper;
import com.george.repositories.TaskRepository;
import com.george.support.InvalidIdentifierException;
import com.george.support.PrimaryKeyViolationException;
import com.george.support.RequiredFieldMissingException;
import com.george.support.WrongParentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public void createAllTasks(TaskLongId[] tasksLongId) throws PrimaryKeyViolationException, InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException, WrongParentException {
        for (TaskLongId taskLongId : tasksLongId)
            createTask(taskLongId);
    }

    public void createTask(TaskLongId taskLongId) throws PrimaryKeyViolationException, InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException, WrongParentException {
        Long id = taskLongId.getId();
        if(id != null && taskRepository.existsById(id))
            throw new PrimaryKeyViolationException("Task", id);
        Task task = taskMapper.mapToEntity(taskLongId);
        taskRepository.save(task);
    }

    public Iterable<TaskLongId> getAllTasks(){
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .map(taskMapper::mapToLongId)
                .collect(Collectors.toList());
    }

    public TaskLongId getTaskById(Long id) throws InvalidIdentifierException {
        if(!taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        return taskMapper.mapToLongId(taskRepository.findById(id).orElse(null));
    }

    public TaskFull fullInfo(Long id) throws InvalidIdentifierException {
        if(!taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        return taskMapper.mapToFull(taskRepository.fullInformation(id).orElse(null));
    }

    public void updateAllTasks(TaskLongId[] tasksLongId, boolean override) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException, WrongParentException {
        if (override) {
            for (TaskLongId taskLongId : tasksLongId)
                overrideTask(taskLongId);
        }
        else {
            for (TaskLongId taskLongId : tasksLongId)
                updateTask(taskLongId);
        }
    }

    public void updateTask(TaskLongId taskLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException, WrongParentException {
        Long id = taskLongId.getId();
        if(id == null || !taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        Task newTask = taskRepository.findById(id).orElse(null);
        Task update = taskMapper.mapToEntity(taskLongId);
        if(update.getDescription() != null)
            newTask.setDescription(update.getDescription());
        if(update.getUser() != null)
            newTask.setUser(update.getUser());
        if(update.getParentTask() != null)
            newTask.setParentTask(update.getParentTask());
        taskRepository.save(newTask);
    }

    public void overrideTask(TaskLongId taskLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException, WrongParentException {
        Long id = taskLongId.getId();
        if(id == null || !taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        Task update = taskMapper.mapToEntity(taskLongId);
        taskRepository.save(update);
    }

    public void deleteAllTasks(){
        taskRepository.deleteAll();
    }

    public void deleteTaskById(Long id) throws InvalidIdentifierException {
        if(!taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        taskRepository.deleteById(id);
    }
}
