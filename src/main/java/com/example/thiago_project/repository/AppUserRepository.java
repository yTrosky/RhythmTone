package com.example.thiago_project.repository;

import com.example.thiago_project.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    public AppUser findByEmail(String email);
}
