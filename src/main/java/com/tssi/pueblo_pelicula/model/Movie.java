package com.tssi.pueblo_pelicula.model;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Movie {

    /** The entity's id. Might be null if not persisted yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the movie. Never blank. */
    @Column(nullable = false)
    private String name;

    /** The duration of the movie in minutes. */
    @Column(nullable = false)
    private int duration;

    /** The poster of the movie. Never blank. */
    @Column(nullable = false)
    private String poster;

    /** Orm's required constructor. */
    Movie() {
    }

    /** Constructor.
     *
     * @param name The name of the movie. Cannot be blank.
     *
     * @param duration The duration in minutes. Must be a number between 1 and 360.
     *
     * @param poster The poster of the movie. Cannot be blank.
     */
    public Movie(String name, int duration, String poster) {
        Validate.notBlank(name, "The name cannot be blank.");
        Validate.inclusiveBetween(1, 360, duration, "The duration must be a number between 1 and 360.");
        Validate.notBlank(poster, "The poster cannot be null.");

        this.name = name;
        this.duration = duration;
        this.poster = poster;
    }

    /** Gets the duration in minutes.
     *
     * @return The duration in minutes.
     */
    public int getDuration() {
        return duration;
    }

    /** Indicates if a given object is equal to this instance.
     *
     * @param o The object to compare with this instance. Can be null.
     *
     * @return true if the object is equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return new EqualsBuilder().append(duration, movie.duration)
            .append(name, movie.name).append(poster, movie.poster).isEquals();
    }

    /** Returns a hash code value for this instance.
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name)
            .append(duration)
            .append(poster).toHashCode();
    }
}
