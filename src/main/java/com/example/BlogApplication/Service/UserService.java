package com.example.BlogApplication.Service;


import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.DatabaseException;
import com.example.BlogApplication.Exception.InvalidDataException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Failed to retrieve blogs: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while retrieving users: " + e.getMessage());
        }
    }

    public User getUserById(int id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not Found"));
    }

    public void createUser(User user) {
        try {
            userRepository.save(user);
        }catch (InvalidDataException e){
            throw new InvalidDataException("Entity was not created" + e);
        }
    }

    public void updateUser(int id, User userDetails) throws ResourceNotFoundException {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

            user.setUsername(userDetails.getUsername());
            user.setAge(userDetails.getAge());

            userRepository.save(user);
        } catch (DatabaseException e){
            throw new DatabaseException("Entity was not updated, pls check code" + e);
        }
    }

    public void deleteUser(int id) {
        try {
            userRepository.deleteById(id);
        } catch (DatabaseException e){
            throw new DatabaseException("Delete was not successful " + e);
        }
    }
}