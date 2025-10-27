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

import com.java.final_project.song_lib.model.Genre;
import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.service.GenreService;
import com.java.final_project.song_lib.service.SongService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private SongService songService;

    @GetMapping
    public String index(@RequestParam(name = "name", required = false) String name, Model model) {
        List<Genre> genres;
        if (name != null) {
            genres = genreService.findByName(name.trim());
        } else {
            genres = genreService.findAll();
        }
        model.addAttribute("genres", genres);
        model.addAttribute("searchQuery", name);
        return "genres/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Genre genre = genreService.getByID(id);
        model.addAttribute("genre", genre);
        return "genres/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("genre", new Genre());
        return "genres/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Genre genre, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "genres/create";
        }

        genreService.create(genre);

        return "redirect:/genres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("genre", genreService.getByID(id));
        return "genres/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Genre genre, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "genres/edit";
        }

        genreService.update(genre);

        return "redirect:/genres";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Genre genre = genreService.getByID(id);

        for (Song song : genre.getSongs()) {
            songService.delete(song);
        }

        genreService.deleteById(id);
        return "redirect:/genres";
    }
}
