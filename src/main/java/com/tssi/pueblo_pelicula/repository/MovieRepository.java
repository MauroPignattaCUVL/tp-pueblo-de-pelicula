package com.tssi.pueblo_pelicula.repository;

import com.tssi.pueblo_pelicula.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
