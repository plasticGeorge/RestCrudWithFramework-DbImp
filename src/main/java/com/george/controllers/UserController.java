package com.george.controllers;

import com.george.dto.UserLongId;
import com.george.services.UserService;
import com.george.support.InvalidIdentifierException;
import com.george.support.PrimaryKeyViolationException;
import com.george.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    /**
     * Http POST method that adds new Users to the database.
     * In the case of adding one User, it must also be wrapped in an array.
     *
     * @param usersLongId - Users array received from the request body in JSON format
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws PrimaryKeyViolationException - in case of incorrectly specified ID
     */
    @PostMapping("/user")
    public Object doPost(@RequestBody UserLongId[] usersLongId) throws PrimaryKeyViolationException, InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        service.createAllUsers(usersLongId);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http GET method that returns required User.
     * If the User with the specified ID is not found, throws an exception
     * that will be handled in a suitable handler.
     *
     * @param full - if true method returns complete information about User, including Tasks,
     *               otherwise only information of a specific User
     * @param userId - identifier of the User to be returned
     * @return User that was requested
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @GetMapping("/user/{userId}")
    public Object doGet(@RequestParam(value = "full", defaultValue = "false") boolean full,
                        @PathVariable("userId") Long userId) throws InvalidIdentifierException {
        if(full)
            return service.fullInfo(userId);
        else
            return service.getUserById(userId);
    }

    /**
     * Http GET method that returns all Users from the database.
     *
     * @return Sequence of requested Users
     */
    @GetMapping("/user")
    public Object doGet(){
        return service.getAllUsers();
    }

    /**
     * Http PUT method that updates Users in database on received Users.
     * In the case of updating one User, it must also be wrapped in an array.
     * If User with specified ID already exists in database, throws an exception
     * that will be handled in a suitable handler.
     *
     * @param override - if true method overrides the record in database, otherwise it will supplement it
     * @param usersLongId - Users array received from the request body in JSON format
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @PutMapping("/user")
    public Object doPut(@RequestParam(value = "override", defaultValue = "false") boolean override,
                        @RequestBody UserLongId[] usersLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        service.updateAllUsers(usersLongId, override);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http DELETE method that removes User from database at the specified ID.
     * If the User with the specified ID is not found, throws an exception
     * that will be handled in a suitable handler
     *
     * @param userId - identifier of the User to be returned
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     * @throws InvalidIdentifierException - in case of incorrectly specified ID
     */
    @DeleteMapping("/user/{userId}")
    public Object doDelete(@PathVariable("userId") Long userId) throws InvalidIdentifierException {
        service.deleteUserById(userId);
        return new ResponseTemplate(true, null);
    }

    /**
     * Http DELETE method that removes all Users from database.
     *
     * @param confirm - indicates that the deletion should definitely be done
     * @return ResponseTemplate - response template containing a success indicator and accompanying message
     */
    @DeleteMapping("/user")
    public Object doDelete(@RequestParam(value = "confirm", defaultValue = "false") boolean confirm) {
        if (confirm)
            service.deleteAllUsers();
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
