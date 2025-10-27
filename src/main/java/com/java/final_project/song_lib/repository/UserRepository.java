package com.java.final_project.song_lib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.final_project.song_lib.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
