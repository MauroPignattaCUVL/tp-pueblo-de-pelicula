package com.tssi.pueblo_pelicula.service.impl;

import com.tssi.pueblo_pelicula.dto.CinemaDTO;
import com.tssi.pueblo_pelicula.model.Cinema;
import com.tssi.pueblo_pelicula.repository.CinemaRepository;
import com.tssi.pueblo_pelicula.service.CinemaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<String> getNames() {
        return this.getAll().stream().map(cinema -> cinema.getName()).collect(Collectors.toList());
    }

    @Override
    public CinemaDTO getById(final Long id) {
        Optional<Cinema> cinema = cinemaRepository.findById(id);
        if(cinema.isPresent()){
            CinemaDTO cinemaDTO = new CinemaDTO();
        }

        return null;
    }
}
