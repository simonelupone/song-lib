package com.java.final_project.song_lib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.repository.SongRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongRepository repository;

    @GetMapping
    public String index(@RequestParam(name = "title", required = false) String title, Model model) {
        List<Song> songs;
        if (title != null) {
            songs = repository.findByTitleContainingIgnoreCase(title.trim());
        } else {
            songs = repository.findAll();
        }
        model.addAttribute("songs", songs);
        model.addAttribute("searchQuery", title);
        return "songs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Song song = repository.findById(id).get();
        model.addAttribute("song", song);
        return "songs/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("song", new Song());
        return "songs/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Song song, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "songs/create";
        }

        repository.save(song);

        return "redirect:/songs";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("song", repository.findById(id).get());
        return "songs/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Song song, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "songs/edit";
        }

        repository.save(song);

        return "redirect:/songs";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/songs";
    }
}
