package com.example.BlogApplication.ViewController;

import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Entity.Comment;
import com.example.BlogApplication.Exception.CommonViewSavingException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Service.BlogService;
import com.example.BlogApplication.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class CommonViewForUser {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;


    @GetMapping("/userView")
    public String getAllUsers(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Blog[] blogs = restTemplate.getForObject("http://localhost:8080/blog", Blog[].class);
        List<Comment> commentList = commentService.getAllComments();
        for (Blog blog : blogs) {
            for (Comment comment : commentList) {
                if (blog.getId() == comment.getBlog().getId()) {
                    blog.getComments().add(comment);
                }
            }
        }
        model.addAttribute("blogs", blogs);
        return "viewForUser";
    }

    @PostMapping("/userView/comment/{id}")
    public String saveComment(@PathVariable int id, @RequestParam String content, @RequestParam int blogId) throws ResourceNotFoundException {
        try {
            Comment comment = new Comment();
            Blog blog = blogService.getBlogById(blogId);
            comment.setId(id);
            comment.setComment(content);
            comment.setBlog(blog);
            commentService.createComment(comment);
        } catch (CommonViewSavingException e ){
            throw new CommonViewSavingException(e);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
        return "redirect:/userView";
    }
}
