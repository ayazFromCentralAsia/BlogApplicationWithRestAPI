package com.example.BlogApplication.Repository;

import com.example.BlogApplication.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    //Blog Repo
}
