package com.george.controllers;

import com.george.dto.TaskLongId;
import com.george.services.TaskService;
import com.george.support.InvalidIdentifierException;
import com.george.support.PrimaryKeyViolationException;
import com.george.support.RequiredFieldMissingException;
import com.george.support.WrongParentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    /**
     * Http POST method that adds new Tasks to the database.
     * In the case of adding one Task, it must also be wrapped in an array.
     *
     * @param tasksLongId - Tasks array received from the request body in JSON format
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws PrimaryKeyViolationException - in case of incorrectly specified ID
     */
    @PostMapping("/task")
    public Object doPost(@RequestBody TaskLongId[] tasksLongId) throws PrimaryKeyViolationException, InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException, WrongParentException {
        service.createAllTasks(tasksLongId);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http GET method that returns required Tasks.
     * If the Tasks with the specified ID is not found, throws an exception
     * that will be handled in a suitable handler.
     *
     * @param full - if true method returns complete information about Task, including parent Tasks,
     *               otherwise only information of a specific Task
     * @param taskId - identifier of the Task to be returned
     * @return Task that was requested
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @GetMapping("/task/{taskId}")
    public Object doGet(@RequestParam(value = "full", defaultValue = "false") boolean full,
                        @PathVariable("taskId") Long taskId) throws InvalidIdentifierException {
        if(full)
            return service.fullInfo(taskId);
        else
            return service.getTaskById(taskId);
    }

    /**
     * Http GET method that returns all Tasks from the database.
     *
     * @return Sequence of requested Tasks
     */
    @GetMapping("/task")
    public Object doGet(){
        return service.getAllTasks();
    }

    /**
     * Http PUT method that updates Tasks in database on received Tasks.
     * In the case of updating one Task, it must also be wrapped in an array.
     * If Task with specified ID already exists in database, throws an exception
     * that will be handled in a suitable handler.
     *
     * @param override - if true method overrides the record in database, otherwise it will supplement it
     * @param tasksLongId - Tasks array received from the request body in JSON format
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @PutMapping("/task")
    public Object doPut(@RequestParam(value = "override", defaultValue = "false") boolean override,
                        @RequestBody TaskLongId[] tasksLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException, WrongParentException {
        service.updateAllTasks(tasksLongId, override);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http DELETE method that removes Task from database at the specified ID.
     * If the Task with the specified ID is not found, throws an exception
     * that will be handled in a suitable handler
     *
     * @param taskId - identifier of the Task to be returned
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @DeleteMapping("/task/{taskId}")
    public Object doDelete(@PathVariable("taskId") Long taskId) throws InvalidIdentifierException {
        service.deleteTaskById(taskId);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http DELETE method that removes all Tasks from database.
     *
     * @param confirm - indicates that the deletion should definitely be done
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     */
    @DeleteMapping("/task")
    public Object doDelete(@RequestParam(value = "confirm", defaultValue = "false") boolean confirm) {
        if (confirm)
            service.deleteAllTasks();
        return new ResponseTemplate(confirm, null);
    }

    /**
     * General handler.
     *
     * @param exception - contains the Exception to be handled
     * @return ResponseTemplate - response template containing a success indicator and accompanying message.
     *         In this case, it's fail notification and error message
     */
    @ExceptionHandler(Exception.class)
    public Object GeneralHandlerException(Exception exception) {
        return new ResponseTemplate(false, exception.getMessage());
    }
}
