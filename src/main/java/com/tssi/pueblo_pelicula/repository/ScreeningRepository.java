package com.tssi.pueblo_pelicula.repository;

import com.tssi.pueblo_pelicula.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

  @Query("SELECT s FROM Cinema c INNER JOIN c.theaters t " +
        "INNER JOIN t.screenings s INNER JOIN s.movie m " +
        "WHERE m.id = ?1")
  List<Screening> getScreeningsForMovie(Long movieId);

}
