package com.practice.controller;

import com.practice.service.UserService;
import com.practice.model.User;
import com.practice.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class.getName());

    @Autowired
    UserService userService;
//  ----------------------------------Retrive all users--------------------------------------------
    @RequestMapping(value ="/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllusers(){
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
//  ---------------------------------Retrive Single user---------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id){
        logger.info("Fetching User with id {}",id);
        User user = userService.findById(id);
        if(user == null){
            logger.error("User With id {} not found!", id);
            return new ResponseEntity<Object>(new CustomErrorType("User with id "+id+" Not Found!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

//    ---------------------------------------- Create user ------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
        logger.info("Creating User {} ", user);
        if(userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable create. A User with name " + user.getName() + "already exist."), HttpStatus.CONFLICT);
            }
            userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

//    ------------------------------------------ Update a user -----------------------------------------------------
    @RequestMapping(value = "/user/id", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user){
        logger.info("Updating User with id {}", id);

        User currentUser = userService.findById(id);
        if(currentUser == null){
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id"+id+"not found!"), HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
//  ---------------------------------------------- Delete a user ---------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
        logger.info("Unable to delete. User with id {} not found.", id);
        User user = userService.findById(id);
        if(user == null){
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id"+id+" not found."),HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
    }

//    ------------------------------------- Delete all user ---------------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUser(){
        logger.info("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
