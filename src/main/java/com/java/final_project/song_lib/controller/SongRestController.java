package com.java.final_project.song_lib.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Song> show(@PathVariable Integer id) {
        Optional<Song> song = songService.findById(id);

        if (song.isEmpty()) {
            return new ResponseEntity<Song>(HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<Song>(song.get(), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/create")
    public ResponseEntity<Song> store(@RequestBody Song song) {
        return new ResponseEntity<Song>(songService.create(song), HttpStatusCode.valueOf(201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> edit(@RequestBody Song song, @PathVariable Integer id) {

        if (songService.findById(id).isEmpty()) {
            return new ResponseEntity<Song>(HttpStatusCode.valueOf(404));
        }

        song.setId(id);
        return new ResponseEntity<Song>(songService.update(song), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Song> delete(@PathVariable Integer id) {

        if (songService.findById(id).isEmpty()) {
            return new ResponseEntity<Song>(HttpStatusCode.valueOf(404));
        }
        songService.deleteById(id);
        return new ResponseEntity<Song>(HttpStatusCode.valueOf(204));
    }
}
