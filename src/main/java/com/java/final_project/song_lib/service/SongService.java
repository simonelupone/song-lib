package com.java.final_project.song_lib.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.java.final_project.song_lib.model.Song;
import com.java.final_project.song_lib.repository.SongRepository;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<Song> findAll() {
        return songRepository.findAll();
    }

    public List<Song> findAllSortedByTitle() {
        return songRepository.findAll(Sort.by("title"));
    }

    public Song getByID(Integer id) {
        Optional<Song> song = songRepository.findById(id);

        if (song.isEmpty()) {
            throw new IllegalArgumentException("Song with id " + id + " not found");
        }

        return song.get();
    }

    public List<Song> findByTitle(String title) {
        return songRepository.findByTitleContainingIgnoreCase(title);
    }

    public Song create(Song song) {
        return songRepository.save(song);
    }

    public Song update(Song song) {
        return songRepository.save(song);
    }

    public void delete(Song song) {
        songRepository.delete(song);
    }

    public void deleteById(Integer id) {
        Song song = getByID(id);
        songRepository.delete(song);
    }

    public boolean existsById(Integer id) {
        return songRepository.existsById(id);
    }

    public boolean exist(Song song) {
        return existsById(song.getId());
    }
}
