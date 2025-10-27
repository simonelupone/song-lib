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
import com.java.final_project.song_lib.service.AuthorService;
import com.java.final_project.song_lib.service.GenreService;
import com.java.final_project.song_lib.service.SongService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String index(@RequestParam(name = "title", required = false) String title, Model model) {
        List<Song> songs;
        if (title != null) {
            songs = songService.findByTitle(title);
        } else {
            songs = songService.findAll();
        }
        model.addAttribute("songs", songs);
        model.addAttribute("searchQuery", title);
        return "songs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Song song = songService.getByID(id);
        model.addAttribute("song", song);
        model.addAttribute("authors", song.getAuthors());
        return "songs/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("song", new Song());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "songs/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Song song, BindingResult bindingResult,
            @RequestParam(name = "authorIds", required = false) List<Integer> authorIds,
            @RequestParam(name = "genreId", required = false) Integer genreId,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "songs/create";
        }

        if (genreId != null) {
            song.setGenre(genreService.getByID(genreId));
        }

        if (authorIds != null && !authorIds.isEmpty()) {
            song.setAuthors(authorService.findAllById(authorIds));
        }

        songService.create(song);

        return "redirect:/songs";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("song", songService.getByID(id));
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "songs/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Song song, BindingResult bindingResult,
            @RequestParam(name = "authorIds", required = false) List<Integer> authorIds,
            @RequestParam(name = "genreId", required = false) Integer genreId,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "songs/edit";
        }

        if (genreId != null) {
            song.setGenre(genreService.getByID(genreId));
        }

        if (authorIds != null && !authorIds.isEmpty()) {
            song.setAuthors(authorService.findAllById(authorIds));
        }

        songService.update(song);

        return "redirect:/songs";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        songService.deleteById(id);
        return "redirect:/songs";
    }
}
