//package com.example.BlogApplication.Controller;
//
//import com.example.BlogApplication.Entity.Comment;
//import com.example.BlogApplication.Exception.ResourceNotFoundException;
//import com.example.BlogApplication.Service.CommentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/comment")
//public class CommentController {
//    @Autowired
//    private final CommentService commentService;
//
//    public CommentController(CommentService commentService) {
//        this.commentService = commentService;
//    }
//
//
//    @GetMapping
//    public List<Comment> getAllComments(){
//        return commentService.getAllComments();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Comment> getComment(@PathVariable int id){
//        return commentService.getCommentById(id);
//    }
//
//    @PostMapping
//    public ResponseEntity<HttpStatus> createComment(@RequestBody Comment comment){
//        comment.setLocalDateTime(LocalDateTime.now());
//        commentService.createComment(comment);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<HttpStatus> editComment(@PathVariable int id, @RequestBody Comment comment) throws ResourceNotFoundException {
//        commentService.updateComment(id,comment);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deleteComment(@PathVariable int id){
//        commentService.deleteComment(id);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//}
