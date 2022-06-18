package com.tssi.pueblo_pelicula.model;

import org.apache.commons.lang3.Validate;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "movie_id")
public class Commercial extends Movie {

  @ElementCollection
  private List<String> actors;

  /** Orm's required constructor. */
  Commercial() {
  }

  /** Constructor.
   *
   * @param name The name of the movie. Cannot be blank.
   *
   * @param duration The duration in minutes. Must be a number between 1 and 360.
   *
   * @param poster The poster of the movie. Cannot be blank.
   *
   * @param actors The actors that participate in the movie. Cannot be null.
   */
  public Commercial(String name, int duration, String poster, List<String> actors) {
    super(name, duration, poster);
    Validate.notEmpty(actors, "The actors cannot be empty.");

    this.actors = actors;
  }
}
