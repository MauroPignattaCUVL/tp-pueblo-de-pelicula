package com.tssi.pueblo_pelicula.controller;

import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<?> postMovie(@Valid @RequestBody MovieDTO movieDTO){
       return new ResponseEntity<>(movieService.save(movieDTO),HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id){
        movieService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
