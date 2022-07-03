package com.tssi.pueblo_pelicula.controller;

import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Valid
@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<MovieDTO>> getMovies() {
        return new ResponseEntity<>(movieService.getMovies(),HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MovieDTO> postMovie(@RequestBody MovieDTO movieDTO) {
       return new ResponseEntity<>(movieService.save(movieDTO),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/poster")
    @Transactional(readOnly = true)
    public ResponseEntity<String> getMoviePoster(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMoviePoster(id));
    }
}
