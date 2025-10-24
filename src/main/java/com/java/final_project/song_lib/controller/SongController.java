package com.java.final_project.song_lib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.repository.SongRepository;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongRepository repository;

    @GetMapping
    public String index(Model model) {
        List<Song> songs = repository.findAll(); // select * from `songs`
        model.addAttribute("songs", songs);
        return "songs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Song song = repository.findById(id).get(); // select * from `songs` where `id` = ?
        model.addAttribute("song", song);
        return "songs/show";
    }
}
