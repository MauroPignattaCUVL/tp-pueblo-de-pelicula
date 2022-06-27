package com.tssi.pueblo_pelicula.service;


import com.tssi.pueblo_pelicula.dto.MovieDTO;

public interface MovieService {

    MovieDTO save(MovieDTO movieDTO);

    void deleteById(Long id);
}
