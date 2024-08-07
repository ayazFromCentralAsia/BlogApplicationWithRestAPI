package com.example.BlogApplication.Service;

import com.example.BlogApplication.Entity.Blog;

import com.example.BlogApplication.Exception.DatabaseException;
import com.example.BlogApplication.Exception.InvalidDataException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public List<Blog> getAllBlog(){
        try {
            return blogRepository.findAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Failed to retrieve blogs: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while retrieving blogs: " + e.getMessage());
        }
    }

    public Blog getBlogById(int id) throws ResourceNotFoundException {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not Found"));
        return blog;
    }

    public void createBlog(Blog blog) {
        try {
            blogRepository.save(blog);
        }catch (InvalidDataException e){
            throw new InvalidDataException("Entity was not created" + e);
        }
    }

    public void deleteBlog(int id) {
        try {
            blogRepository.deleteById(id);
        } catch (DatabaseException e){
            throw new DatabaseException("Delete was not successful " + e);
        }
    }

    public void updateBlog(int id, Blog blogDetails) throws ResourceNotFoundException {
        try {
            Blog blog = blogRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id :: " + id));

            blog.setTitle(blogDetails.getTitle());
            blog.setText(blogDetails.getText());
            blog.setUser(blogDetails.getUser());

            blogRepository.save(blog);
        } catch (DatabaseException e){
            throw new DatabaseException("Entity was not updated, pls check code" + e);
        }
    }
}
