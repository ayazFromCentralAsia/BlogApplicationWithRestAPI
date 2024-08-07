package com.example.BlogApplication.Service;


import com.example.BlogApplication.Entity.Comment;
import com.example.BlogApplication.Exception.DatabaseException;
import com.example.BlogApplication.Exception.InvalidDataException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments(){
        try {
            return commentRepository.findAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Failed to retrieve blogs: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while retrieving comments: " + e.getMessage());
        }
    }

    public Comment getCommentById(int id) throws ResourceNotFoundException{
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not Found"));
    }

    public void createComment(Comment comment) {
        try {
            commentRepository.save(comment);
        }catch (InvalidDataException e){
            throw new InvalidDataException("Entity was not created" + e);
        }
    }

    public void deleteComment(int id) {
        try {
            commentRepository.deleteById(id);
        } catch (DatabaseException e){
            throw new DatabaseException("Delete was not successful " + e);
        }
    }

    public void updateComment(int id, Comment commentDetails) throws ResourceNotFoundException{
        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + id));

            comment.setComment(commentDetails.getComment());
            comment.setLocalDateTime(commentDetails.getLocalDateTime());
            comment.setUser(commentDetails.getUser());
            comment.setBlog(commentDetails.getBlog());

            commentRepository.save(comment);
        } catch (DatabaseException e){
            throw new DatabaseException("Entity was not updated, pls check code" + e);
        }
    }
}