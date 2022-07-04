package com.tssi.pueblo_pelicula.repository;

import com.tssi.pueblo_pelicula.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

}
