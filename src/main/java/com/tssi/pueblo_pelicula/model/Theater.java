package com.tssi.pueblo_pelicula.model;

import com.tssi.cinema.infrastructure.Input;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static com.tssi.cinema.domain.Screening.*;

/** Represents the movie theater, that groups and manages the screenings. */
@Entity
public class Theater {

    /** The entity's id. Might be null if not persisted yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The number that identifies the theater in the cinema. */
    @Column(nullable = false)
    private int number;

    /** The type of the theater. Never null. */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TheaterType theaterType;

    /** The scheduled screenings. Never null.
     * Might be empty if no screenings are scheduled. */
    @OneToMany
    private Set<Screening> screenings;

    /** Orm's required constructor. */
    Theater() {
    }

    /** Constructor.
     *
     * @param number The number that identifies the theater in the cinema.
     *
     * @param theaterType The type of the theater. Cannot be null.
     */
    public Theater(int number, TheaterType theaterType) {
        Validate.notNull(theaterType, "The theaterType cannot be null.");

        this.number = number;
        this.theaterType = theaterType;
        this.screenings = new HashSet<>();
    }

    /** Adds a screening to the theater.
     *
     * @param movie The movie to be played at the screening. Cannot be null.
     *
     * @param screeningDate the date on which the movie theater is to be played. Cannot be null.
     */
    public void addScreening(Movie movie, LocalDate screeningDate) {
        Validate.notNull(movie, "The movie cannot be null.");
        Validate.notNull(screeningDate, "The screeningDate cannot be null.");
        Input.isTrue(theaterType.supports(movie), "High definition theaters do not support documentaries.");

        LocalDateTime screeningTime = resolveNextScreeningTime(screeningDate);

        screenings.add(new Screening(screeningTime, movie));
    }

    /** Resolves the hour that the next screening should be scheduled.
     *
     * @param screeningDate the date on which the movie theater is to be played. Cannot be null.
     *
     * @return The hour that the next screening time should be scheduled. Never null.
     */
    private LocalDateTime resolveNextScreeningTime(LocalDate screeningDate) {
        Screening lastScreening = getLastScreening(screeningDate);

        if (lastScreening == null) {
            return LocalDateTime.of(screeningDate, FIRST_SCREENING_OF_DAY);
        }

        LocalDateTime screeningTime = lastScreening.getEndTime().plusMinutes(MINUTES_BETWEEN_SCREENINGS);
        Input.isTrue(screeningTime.toLocalTime().isBefore(LAST_SCREENING_OF_DAY),
            "All screenings are occupied at the given date.");

        return screeningTime;
    }

    /** Returns the last screening from the same day that this method is called on.
     *
     * @param screeningDate the date on which the movie theater is to be played. Cannot be null.
     *
     * @return The last screening of the given date. Can be null if no screenings are programmed for the date.
     */
    private Screening getLastScreening(LocalDate screeningDate) {
        return screenings.stream().filter(s -> s.isForDate(screeningDate))
            .max(Comparator.comparing(Screening::getStartTime))
            .orElse(null);
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

        Theater theater = (Theater) o;

        return new EqualsBuilder().append(number, theater.number)
            .append(theaterType, theater.theaterType)
            .append(screenings, theater.screenings).isEquals();
    }

    /** Returns a hash code value for this instance.
     *
     * @return the hash code value for this instance
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(number).append(theaterType).append(screenings).toHashCode();
    }
}
