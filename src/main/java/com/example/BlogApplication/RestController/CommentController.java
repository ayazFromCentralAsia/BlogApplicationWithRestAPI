package com.example.BlogApplication.RestController;

import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Entity.Comment;
import com.example.BlogApplication.Exception.DatabaseException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.RequestDTO.CommentRequest;
import com.example.BlogApplication.Service.BlogService;
import com.example.BlogApplication.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @Autowired
    private final BlogService blogService;

    public CommentController(CommentService commentService, BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }


    @GetMapping
    public List<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable int id) throws ResourceNotFoundException {
        try {
            return commentService.getCommentById(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Entity was not found " + e);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createComment(@RequestBody CommentRequest commentRequest) throws ResourceNotFoundException {
        Comment comment = new Comment();
        Blog blog = blogService.getBlogById(commentRequest.getBlog_id());
        comment.setLocalDateTime(LocalDateTime.now());
        comment.setBlog(blog);
        comment.setComment(commentRequest.getComment());
        commentService.createComment(comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> editComment(@PathVariable int id, @RequestBody Comment comment) throws ResourceNotFoundException, DatabaseException {
        try {
        commentService.updateComment(id,comment);
        } catch (ResourceNotFoundException | DatabaseException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable int id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
