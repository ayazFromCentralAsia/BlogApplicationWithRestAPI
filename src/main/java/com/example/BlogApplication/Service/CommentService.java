package com.example.BlogApplication.Service;


import com.example.BlogApplication.Entity.Comment;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(int id) throws ResourceNotFoundException{
        return commentRepository.findById(id);
    }

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

    public Comment updateComment(int id, Comment commentDetails) throws ResourceNotFoundException{
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + id));

        comment.setComment(commentDetails.getComment());
        comment.setLocalDateTime(commentDetails.getLocalDateTime());
        comment.setUser(commentDetails.getUser());
        comment.setBlog(commentDetails.getBlog());

        return commentRepository.save(comment);
    }
}