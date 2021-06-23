package com.george.controllers;

import com.george.dto.ProjectLongId;
import com.george.services.ProjectService;
import com.george.support.InvalidIdentifierException;
import com.george.support.PrimaryKeyViolationException;
import com.george.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {
    private final ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    /**
     * Http POST method that adds new Projects to the database.
     * In the case of adding one Project, it must also be wrapped in an array.
     *
     * @param projectsLongId - Projects array received from the request body in JSON format
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws PrimaryKeyViolationException - in case of incorrectly specified ID
     */
    @PostMapping("/project")
    public Object doPost(@RequestBody ProjectLongId[] projectsLongId) throws PrimaryKeyViolationException, NoSuchFieldException, RequiredFieldMissingException {
        service.createAllProjects(projectsLongId);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http GET method that returns required Project.
     * If the Project with the specified ID is not found, throws an exception
     * that will be handled in a suitable handler.
     *
     * @param full - if true method returns complete information about Project, including Users and Tasks,
     *               otherwise only information of a specific Project
     * @param projectId - identifier of the Project to be returned
     * @return Project that was requested
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @GetMapping("/project/{projectId}")
    public Object doGet(@RequestParam(value = "full", defaultValue = "false") boolean full,
                        @PathVariable("projectId") Long projectId) throws InvalidIdentifierException {
        if(full)
            return service.fullInfo(projectId);
        else
            return service.getProjectById(projectId);
    }

    /**
     * Http GET method that returns all Projects from the database.
     *
     * @return Sequence of requested Projects
     */
    @GetMapping("/project")
    public Object doGet(){
        return service.getAllProjects();
    }

    @GetMapping("/project/{projectId}/tasks")
    public Object doGet(@PathVariable("projectId") Long projectId) {
        return service.getTasksCount(projectId);
    }

    /**
     * Http PUT method that updates Projects in database on received Projects.
     * In the case of updating one Project, it must also be wrapped in an array.
     * If Project with specified ID already exists in database, throws an exception
     * that will be handled in a suitable handler.
     *
     * @param override - if true method overrides the record in database, otherwise it will supplement it
     * @param projectsLongId - Projects array received from the request body in JSON format
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @PutMapping("/project")
    public Object doPut(@RequestParam(value = "override", defaultValue = "false") boolean override,
                        @RequestBody ProjectLongId[] projectsLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        service.updateAllProjects(projectsLongId, override);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http DELETE method that removes Project from database at the specified ID.
     * If the Project with the specified ID is not found, throws an exception
     * that will be handled in a suitable handler
     *
     * @param projectId - identifier of the Project to be returned
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @DeleteMapping("/project/{projectId}")
    public Object doDelete(@PathVariable("projectId") Long projectId) throws InvalidIdentifierException {
        service.deleteProjectById(projectId);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http DELETE method that removes all Projects from database.
     *
     * @param confirm - indicates that the deletion should definitely be done
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     */
    @DeleteMapping("/project")
    public Object doDelete(@RequestParam(value = "confirm", defaultValue = "false") boolean confirm) {
        if (confirm)
            service.deleteAllProjects();
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
