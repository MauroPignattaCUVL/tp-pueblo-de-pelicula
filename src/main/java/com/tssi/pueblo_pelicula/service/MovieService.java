package com.tssi.pueblo_pelicula.service;

import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.dto.ScreeningForMovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO movieDTO);

    MovieDTO getMovieById(Long id);

    String getMoviePoster(Long id);

    List<MovieDTO> getMovies(Long cinemaId, String filter);

    List<ScreeningForMovieDTO> getScreeningsForMovie(Long id);

    void deleteById(Long id);
}