package com.java.final_project.song_lib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.service.SongService;

@RestController
@RequestMapping("/api/songs")
public class SongRestController {

    @Autowired
    private SongService songService;

    @GetMapping
    public List<Song> index() {
        List<Song> songs = songService.findAll();
        return songs;
    }

    @GetMapping("/{id}")
    public Song show(@PathVariable Integer id) {
        Song song = songService.getByID(id);
        return song;
    }

    @PostMapping("/create")
    public Song store(@RequestBody Song song) {
        return songService.create(song);
    }

    @PutMapping("/{id}")
    public Song update(@RequestBody Song song, @PathVariable Integer id) {
        song.setId(id);
        return songService.update(song);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        songService.deleteById(id);
    }
}
