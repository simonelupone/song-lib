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
import com.java.final_project.song_lib.service.AuthorService;
import com.java.final_project.song_lib.service.SongService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private SongService songService;

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String index(@RequestParam(name = "name", required = false) String name, Model model) {
        List<Author> authors;
        if (name != null) {
            authors = authorService.findByName(name.trim());
        } else {
            authors = authorService.findAll();
        }
        model.addAttribute("authors", authors);
        model.addAttribute("searchQuery", name);
        return "authors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorService.getByID(id));
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
        authorService.create(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorService.getByID(id));
        return "authors/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }
        authorService.update(author);
        return "redirect:/authors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // delete all songs linked to this author
        for (Song song : authorService.getByID(id).getSongs()) {
            songService.delete(song);
        }
        authorService.deleteById(id);
        return "redirect:/authors";
    }

}
