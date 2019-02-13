package com.ibm.services.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ibm.services.data.entity.User;
import com.ibm.services.data.manager.UserManager;
import com.ibm.services.exception.UserNameAlreadyTakenException;

@Api(value = "users", description = "A set of endpoints for managing users")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private UserManager userManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }
    
    @ApiOperation(value = "createUser", notes = "Create a new user")
    @RequestMapping(value = "/sign-up", method = RequestMethod.PUT)
    public User createUser(@RequestBody User model) throws UserNameAlreadyTakenException {
        model.setPassword(bCryptPasswordEncoder.encode(model.getPassword()));
        User createUser = userManager.createUser(model);
        return createUser;
    }
    
//    @PreAuthorize("hasRole('INTEGRATION_TEST')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String id) {
        userManager.deleteUserById(id);
    }
    
    @ApiOperation(value = "getUserById", notes = "Return User object for a given User")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") String id) {
    	log.debug("getUserById()");
        return userManager.getUserById(id);
    }
    
    @ApiOperation(value = "getAllUsers", notes = "Return all user object")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
    	log.info("getAllUsers called");
        return userManager.getAllUsers();
    }
    
    @ApiOperation(value = "getUserByUsername", notes = "Return User object for a given User")
    @RequestMapping(value = "/loadUser", method = RequestMethod.GET)
    public User getUserByUsername(@RequestParam("username") String id) {

        return userManager.getUserByUsername(id);
    }
    
    @ApiOperation(value = "getUserByType", notes = "Return User object for a given type")
    @RequestMapping(value = "/user-type", method = RequestMethod.GET)
    public List<User> getUserByType(@RequestParam("type") String type) {

        return userManager.getUsersByUserType(type);
    }
    
    
}
