package com.tssi.pueblo_pelicula.repository;

import com.tssi.pueblo_pelicula.constant.DocumentaryTheme;
import com.tssi.pueblo_pelicula.model.Commercial;
import com.tssi.pueblo_pelicula.model.Documentary;
import com.tssi.pueblo_pelicula.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  @Query("FROM Movie m WHERE m.name LIKE %?1%")
  Set<Movie> getMoviesByName(String name);

  @Query("FROM Commercial c, IN (c.actors) actor WHERE actor LIKE %?1%")
  Set<Commercial> getCommercialMovieByActor(String actorName);

  @Query("FROM Documentary d WHERE d.documentaryTheme LIKE %?1%")
  Set<Documentary> getDocumentaryByTheme(DocumentaryTheme theme);

  @Query("SELECT s.movie FROM Cinema c INNER JOIN c.theaters t INNER JOIN t.screenings s " +
      "WHERE c.id = ?1")
  Set<Movie> getMoviesScheduledInCinema(Long cinemaId);
}
