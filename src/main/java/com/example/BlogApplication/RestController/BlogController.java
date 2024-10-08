package com.example.BlogApplication.RestController;

import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.DatabaseException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.RequestDTO.BlogRequest;
import com.example.BlogApplication.Service.BlogService;
import com.example.BlogApplication.Service.CommentService;
import com.example.BlogApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    public BlogController() {

    }

    @GetMapping
    public List<Blog> blogList(){
        return blogService.getAllBlog();
    }

    @GetMapping("/{id}")
    public Blog getBlog(@PathVariable int id) throws ResourceNotFoundException {
        try {
            return blogService.getBlogById(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Entity was not found " + e);
        }
    }

    @PostMapping
    public void createBlog(@RequestBody BlogRequest blogRequest) throws ResourceNotFoundException {
        User user = userService.getUserById(blogRequest.getUser_id());
        Blog blog = new Blog();
        blog.setLocalDateTime(LocalDateTime.now());
        blog.setUser(user);
        blog.setText(blogRequest.getText());
        blog.setTitle(blogRequest.getTitle());
        blogService.createBlog(blog);
    }

    @PutMapping("/{id}")
    public void editBlog(@PathVariable int id, @RequestBody Blog blog) {
        try {
            blogService.updateBlog(id,blog);
        } catch (ResourceNotFoundException | DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        blogService.deleteBlog(id);
    }
}
