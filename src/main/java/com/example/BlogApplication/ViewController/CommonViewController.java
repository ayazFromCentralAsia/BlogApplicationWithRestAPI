package com.example.BlogApplication.ViewController;

import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Entity.Comment;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.CommonViewAddException;
import com.example.BlogApplication.Exception.CommonViewSavingException;
import com.example.BlogApplication.Exception.CommonViewUpdateException;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.RequestDTO.BlogRequest;
import com.example.BlogApplication.Service.BlogService;
import com.example.BlogApplication.Service.CommentService;
import com.example.BlogApplication.Service.UserService;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view")
public class CommonViewController {
    @Autowired
    private final UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    public CommonViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model){
        RestTemplate restTemplate = new RestTemplate();
        Blog[] blogs = restTemplate.getForObject("http://localhost:8080/blog",Blog[].class);
        List<Comment> commentList = commentService.getAllComments();
        for (Blog blog : blogs){
            for (Comment comment :commentList){
                if (blog.getId() == comment.getBlog().getId()){
                    blog.getComments().add(comment);
                }
            }
        }
        model.addAttribute("blogs", blogs);
        return "commonView";
    }

    @PostMapping("/comment/{id}")
    public String saveComment(@PathVariable int id, @RequestParam String content, @RequestParam int blogId) throws ResourceNotFoundException {
        try {
            Comment comment = new Comment();
            Blog blog = blogService.getBlogById(blogId);
            comment.setComment(content);
            comment.setBlog(blog);
            comment.setUser(userService.getUserById(userService.getUserId()));
            commentService.createComment(comment);
        } catch (CommonViewSavingException e ){
            throw new CommonViewSavingException(e);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
        return "redirect:/view";
    }
    @PostMapping("/add")
    public String saveBlog(@RequestParam String title,
                         @RequestParam String content){
        try {
            RestTemplate restTemplate = new RestTemplate();
            BlogRequest blog = new BlogRequest();
            blog.setTitle(title);
            blog.setText(content);
            blog.setUser_id(userService.getUserId());
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<BlogRequest> request = new HttpEntity<>(blog, headers);
            restTemplate.postForEntity("http://localhost:8080/blog", request, BlogRequest.class).getStatusCode();
        }catch (CommonViewAddException e){
            throw new CommonViewAddException(e);
        }finally {
            return "redirect:/view";
        }
    }

    @PostMapping("/edit")
    public String editBlog(@RequestParam int id,
                           @RequestParam String title,
                           @RequestParam String content){
        try {
            RestTemplate restTemplate = new RestTemplate();
            Blog blog = restTemplate.getForObject("http://localhost:8080/blog/" + id, Blog.class);
            if (!title.isBlank()) {
                blog.setTitle(title);
            }
            if (!content.isBlank()) {
                blog.setText(content);
            }
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Blog> request = new HttpEntity<>(blog, headers);
            restTemplate.put("http://localhost:8080/blog/" + id, request, Blog.class);
        } catch (CommonViewUpdateException e){
            throw new CommonViewUpdateException(e);
        }finally {
            return "redirect:/view";
        }
    }

    @PostMapping("/delete")
    public String deleteBlog(@RequestParam int id){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/blog/"+id);
        return "redirect:/view";
    }
}
