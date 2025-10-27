package com.java.final_project.song_lib.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.final_project.song_lib.model.Author;
import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.repository.AuthorRepository;
import com.java.final_project.song_lib.repository.SongRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private SongRepository songRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public List<Author> findByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Author> findAllById(Iterable<Integer> ids) {
        return authorRepository.findAllById(ids);
    }

    public Author getByID(Integer id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author with id " + id + " not found");
        }
        return author.get();
    }

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public Author update(Author author) {
        return authorRepository.save(author);
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public void deleteById(Integer id) {
        Author author = getByID(id);
        // delete songs associated with author
        if (author.getSongs() != null) {
            for (Song song : author.getSongs()) {
                songRepository.delete(song);
            }
        }
        authorRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return authorRepository.existsById(id);
    }
}
