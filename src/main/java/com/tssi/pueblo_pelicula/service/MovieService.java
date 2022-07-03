package com.tssi.pueblo_pelicula.service;

import com.tssi.pueblo_pelicula.dto.MovieDTO;
import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO movieDTO);

    MovieDTO getMovieById(Long id);

    List<MovieDTO> getMovies();

    void deleteById(Long id);

}