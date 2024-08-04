package com.example.BlogApplication.RestController;

import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repository.UserRepository;
import com.example.BlogApplication.RequestDTO.BlogRequest;
import com.example.BlogApplication.Service.BlogService;
import com.example.BlogApplication.Service.UserService;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    public BlogController() {

    }

    @GetMapping
    public List<Blog> blogList(){
        return blogService.getAllBlog();
    }

    @GetMapping("/{id}")
    public Optional<Blog> getBlog(@PathVariable int id){
        return blogService.getBlogById(id);
    }

    @PostMapping
    public void createBlog(@RequestBody BlogRequest blogRequest) throws ResourceNotFoundException {
        User user = userService.getUserById(blogRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Blog blog = new Blog();
        blog.setLocalDateTime(LocalDateTime.now());
        blog.setUser(user);
        blog.setText(blogRequest.getText());
        blog.setTitle(blogRequest.getTitle());
        blogService.createBlog(blog);
    }

    @PutMapping("/{id}")
    public void editBlog(@PathVariable int id, @RequestBody Blog blog) throws ResourceNotFoundException {
        blogService.updateBlog(id,blog);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        blogService.deleteBlog(id);
    }
}
