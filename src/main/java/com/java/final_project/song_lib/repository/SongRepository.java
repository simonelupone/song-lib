package com.java.final_project.song_lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.final_project.song_lib.model.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {

}
