package com.java.final_project.song_lib.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.final_project.song_lib.model.Genre;
import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.repository.GenreRepository;
import com.java.final_project.song_lib.repository.SongRepository;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SongRepository songRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public List<Genre> findByName(String name) {
        return genreRepository.findByNameContainingIgnoreCase(name);
    }

    public Genre getByID(Integer id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isEmpty()) {
            throw new IllegalArgumentException("Genre with id " + id + " not found");
        }
        return genre.get();
    }

    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre update(Genre genre) {
        return genreRepository.save(genre);
    }

    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }

    public void deleteById(Integer id) {
        Genre genre = getByID(id);
        if (genre.getSongs() != null) {
            for (Song song : genre.getSongs()) {
                songRepository.delete(song);
            }
        }
        genreRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return genreRepository.existsById(id);
    }
}
