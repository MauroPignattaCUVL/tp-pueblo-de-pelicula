package com.tssi.pueblo_pelicula.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;

import static com.tssi.pueblo_pelicula.model.Screening.*;
import com.tssi.pueblo_pelicula.constant.TheaterType;
import com.tssi.pueblo_pelicula.error.Input;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** Represents the movie theater, that groups and manages the screenings. */
@Entity
@Getter
@NoArgsConstructor
public class Theater {

    /** The entity's id. Might be null if not persisted yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The number that identifies the theater in the cinema. Never null */
    @Column(nullable = false)
    private int number;

    /** The type of the theater. Never null. */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TheaterType theaterType;

    @ManyToOne(optional = false)
    private Cinema cinema;

    /** The scheduled screenings. Never null.
     * Might be empty if no screenings are scheduled. */
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Screening> screenings;

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

        screenings.add(new Screening(screeningTime, movie, this));
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
        if (!screeningTime.toLocalDate().equals(screeningDate)) {
            Input.isTrue(screeningTime.toLocalTime().isBefore(LAST_SCREENING_OF_DAY),
                "All screenings are occupied at the given date.");
        }

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
