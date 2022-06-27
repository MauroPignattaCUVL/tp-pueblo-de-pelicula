package com.tssi.pueblo_pelicula.service;

import com.tssi.pueblo_pelicula.dto.CinemaDTO;
import com.tssi.pueblo_pelicula.model.Cinema;

import java.util.List;

public interface CinemaService {

    List<Cinema> getAll();

    List<String> getNames();

    CinemaDTO getById(Long id);
}
