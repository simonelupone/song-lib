package com.java.final_project.song_lib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.final_project.song_lib.model.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
    public List<Song> findByTitleContainingIgnoreCase(String title);
}
