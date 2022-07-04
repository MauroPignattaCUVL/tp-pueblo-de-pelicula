package com.tssi.pueblo_pelicula.model;

import com.tssi.pueblo_pelicula.constant.MovieType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "movie_id")
public class Commercial extends Movie {

  @ElementCollection
  private List<String> actors;

  public Commercial(String name, int duration, String poster, String resume, List<String> actors) {
    super(name, duration, poster, resume);
    this.actors = actors;
  }

  @Override
  public MovieType getMovieType() {
    return MovieType.COMMERCIAL;
  }
}
