package com.example.BlogApplication.RestController;

import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Service.BlogService;
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
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
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
    public ResponseEntity<HttpStatus> createUser(@RequestBody Blog blog){
        blog.setLocalDateTime(LocalDateTime.now());
        blogService.createBlog(blog);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> editUser(@PathVariable int id, @RequestBody Blog blog) throws ResourceNotFoundException {
        blogService.updateBlog(id,blog);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id){
        blogService.deleteBlog(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
