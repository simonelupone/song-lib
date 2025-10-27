package com.java.final_project.song_lib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.final_project.song_lib.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    public List<Genre> findByNameContainingIgnoreCase(String name);
}
