package com.example.BlogApplication.RestController;

import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.DatabaseException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws ResourceNotFoundException{
        try {
            return userService.getUserById(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Entity was not found " + e);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user){
        user.setLocalDateTime(LocalDateTime.now());
        userService.createUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
        public ResponseEntity<HttpStatus> editUser(@PathVariable int id, @RequestBody User user) throws ResourceNotFoundException {
        try {
            userService.updateUser(id,user);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (ResourceNotFoundException | DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
