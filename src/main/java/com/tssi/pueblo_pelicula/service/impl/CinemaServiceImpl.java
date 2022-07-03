package com.tssi.pueblo_pelicula.service.impl;

import com.tssi.pueblo_pelicula.model.Cinema;
import com.tssi.pueblo_pelicula.repository.CinemaRepository;
import com.tssi.pueblo_pelicula.service.CinemaService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CinemaServiceImpl implements CinemaService {

    private CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository){
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public List<Cinema> getAll() {
        return cinemaRepository.findAll();
    }

}
