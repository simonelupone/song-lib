package com.java.final_project.song_lib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.final_project.song_lib.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    public List<Author> findByNameContainingIgnoreCase(String name);
}
