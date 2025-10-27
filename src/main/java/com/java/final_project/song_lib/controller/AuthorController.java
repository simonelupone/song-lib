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

import com.java.final_project.song_lib.model.Author;
import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.repository.AuthorRepository;
import com.java.final_project.song_lib.repository.SongRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final SongRepository songRepository;

    @Autowired
    private AuthorRepository authorRepository;

    AuthorController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping
    public String index(@RequestParam(name = "name", required = false) String name, Model model) {
        List<Author> authors;
        if (name != null) {
            authors = authorRepository.findByNameContainingIgnoreCase(name.trim());
        } else {
            authors = authorRepository.findAll();
        }
        model.addAttribute("authors", authors);
        model.addAttribute("searchQuery", name);
        return "authors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorRepository.findById(id).get());
        return "authors/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("author", new Author());
        return "authors/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/create";
        }
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorRepository.findById(id).get());
        return "authors/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        for (Song song : authorRepository.findById(id).get().getSongs()) {
            songRepository.delete(song);
        }
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }

}
