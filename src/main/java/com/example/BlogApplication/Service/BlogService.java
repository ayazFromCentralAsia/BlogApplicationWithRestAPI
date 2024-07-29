package com.example.BlogApplication.Service;

import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public List<Blog> getAllBlog(){
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(int id){
        return blogRepository.findById(id);
    }

    public Blog createBlog(Blog blog){
        return blogRepository.save(blog);
    }

    public void deleteBlog(int id) {
        blogRepository.deleteById(id);
    }

    public Blog updateBlog(int id, Blog blogDetails) throws ResourceNotFoundException {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id :: " + id));

        blog.setTitle(blogDetails.getTitle());
        blog.setText(blogDetails.getText());
        blog.setUser(blogDetails.getUser());

        return blogRepository.save(blog);
    }
}
