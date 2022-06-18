package com.tssi.pueblo_pelicula.model;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents the date and time which a movie will be player at.
 */
@Entity
public class Screening {

    /** The time that the first screening starts. */
    public static final LocalTime FIRST_SCREENING_OF_DAY = LocalTime.of(10, 0);

    /** The time that the last screening starts. */
    public static final LocalTime LAST_SCREENING_OF_DAY = LocalTime.of(2, 0);

    /** The minutes that must pass between a screening and another one. */
    public static final int MINUTES_BETWEEN_SCREENINGS = 15;

    /** The entity's id. Might be null if not persisted yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The date and time that the screening starts. Never null. */
    @Column(nullable = false)
    private LocalDateTime time;

    /** The movie to be played in the screening. Never null. */
    @OneToOne
    private Movie movie;

    /** Orm's required constructor. */
    Screening() {
    }

    /** Constructor.
     *
     * @param startTime The date and time that the screening starts. Cannot be null.
     * Must be between 10:00 AM and 02:00 AM.
     *
     * @param movie The movie to be played in the screening. Cannot be null.
     */
    public Screening(LocalDateTime startTime, Movie movie) {
        Validate.notNull(startTime, "The startTime cannot be null.");
        Validate.notNull(movie, "The movie cannot be null.");

        LocalTime time = startTime.toLocalTime();
        Validate.isTrue(time.isBefore(LAST_SCREENING_OF_DAY) || time.isAfter(FIRST_SCREENING_OF_DAY),
            "The time must be between 10:00 AM and 02:00 AM.");

        this.time = startTime;
        this.movie = movie;
    }

    /** Indicates if the screening belongs to a given date.
     *
     * The screening belongs to a given date when:
     *   Is from the same date, after 10:00 am.
     *   Is from the next date, until 02:00 am.
     *
     * @param date The date to compare the screening to. Cannot be null.
     *
     * @return true if the screening belongs to the given date, false otherwise.
     */
    public boolean isForDate(LocalDate date) {
        Validate.notNull(date, "The date cannot be null.");

        LocalDate tomorrow = date.plusDays(1);
        return time.isAfter(date.atTime(FIRST_SCREENING_OF_DAY)) &&
            time.isBefore(tomorrow.atTime(LAST_SCREENING_OF_DAY));
    }

    /** Returns the time that the movie start.
     *
     * @return The time that the movie starts. Never null.
     */
    public LocalDateTime getStartTime() {
        return time;
    }

    /** Returns the time that the movie ends.
     *
     * @return The time that the movie ends. Never null.
     */
    public LocalDateTime getEndTime() {
        return time.plus(movie.getDuration(), ChronoUnit.MINUTES);
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

        Screening screening = (Screening) o;

        return new EqualsBuilder().append(time, screening.time).append(movie, screening.movie).isEquals();
    }

    /** Returns a hash code value for this instance.
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(time).append(movie).toHashCode();
    }
}
