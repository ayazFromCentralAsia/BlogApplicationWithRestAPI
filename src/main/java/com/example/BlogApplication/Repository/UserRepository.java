package com.example.BlogApplication.Repository;

import com.example.BlogApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    //User Repo
}
