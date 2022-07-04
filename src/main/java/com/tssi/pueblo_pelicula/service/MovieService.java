package com.tssi.pueblo_pelicula.service;

import com.tssi.pueblo_pelicula.dto.MovieDTO;
import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO movieDTO);

    MovieDTO getMovieById(Long id);

    String getMoviePoster(Long id);

    List<MovieDTO> getMovies(Long cinemaId, String filter);

    void deleteById(Long id);
}