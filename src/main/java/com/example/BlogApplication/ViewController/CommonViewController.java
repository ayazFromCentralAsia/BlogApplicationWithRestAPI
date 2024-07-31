package com.example.BlogApplication.ViewController;


import com.example.BlogApplication.Entity.Blog;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Service.UserService;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/view")
public class CommonViewController {

    @Autowired
    private final UserService userService;

    public CommonViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model){
        RestTemplate restTemplate = new RestTemplate();
        User[] users = restTemplate.getForObject("http://localhost:8080/user", User[].class);
        model.addAttribute("users", users);
        return "commonView";
    }

    @PostMapping("/add")
    public String saveBlog(@RequestParam String title,
                         @RequestParam String content){
        RestTemplate restTemplate = new RestTemplate();
        Blog blog = new Blog();
        User user = new User();
        user.setId(3);
        blog.setTitle(title);
        blog.setText(content);
        blog.setUser(user);
        blog.setLocalDateTime(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Blog> request = new HttpEntity<>(blog,headers);
        ResponseEntity<Blog> entity = restTemplate.postForEntity("http://localhost:8080/blog",request,Blog.class);
        return "redirect:/view";
    }
}
