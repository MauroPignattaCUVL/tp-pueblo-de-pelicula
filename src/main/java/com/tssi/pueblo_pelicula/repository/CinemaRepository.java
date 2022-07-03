package com.tssi.pueblo_pelicula.repository;

import com.tssi.pueblo_pelicula.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

}
