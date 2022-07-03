package com.tssi.pueblo_pelicula.model;

import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
public class Screening {
    public static final LocalTime FIRST_SCREENING_OF_DAY = LocalTime.of(10, 0);
    public static final LocalTime LAST_SCREENING_OF_DAY = LocalTime.of(2, 0);
    public static final int MINUTES_BETWEEN_SCREENINGS = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne
    private Theater theater;

    @ManyToOne
    private Movie movie;


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


}
