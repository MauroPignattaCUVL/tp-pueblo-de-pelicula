package com.tssi.pueblo_pelicula.controller;

import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<MovieDTO>> getMovies(@RequestParam @Nullable Long cinema_id,
                                                    @RequestParam @Nullable String filter) {
        return new ResponseEntity<>(movieService.getMovies(cinema_id, filter),HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MovieDTO> postMovie(@Valid @RequestBody MovieDTO movieDTO) {
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
