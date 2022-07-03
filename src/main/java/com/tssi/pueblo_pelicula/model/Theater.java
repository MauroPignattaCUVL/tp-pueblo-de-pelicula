package com.tssi.pueblo_pelicula.model;

import com.tssi.pueblo_pelicula.constant.TheaterType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;

import static com.tssi.pueblo_pelicula.model.Screening.FIRST_SCREENING_OF_DAY;
import static com.tssi.pueblo_pelicula.model.Screening.MINUTES_BETWEEN_SCREENINGS;

@Entity
@Data
@NoArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int theaterNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TheaterType theaterType;

    @ManyToOne
    private Cinema cinema;

    @OneToMany(mappedBy = "theater", fetch = FetchType.EAGER)
    private Set<Screening> screenings;

    public void addScreening(Movie movie, LocalDate screeningDate) {
        Validate.notNull(movie, "The movie cannot be null.");
        Validate.notNull(screeningDate, "The screeningDate cannot be null.");
        //Input.isTrue(theaterType.supports(movie), "High definition theaters do not support documentaries.");

        LocalDateTime screeningTime = resolveNextScreeningTime(screeningDate);

        screenings.add(new Screening(screeningTime, movie));
    }

    private LocalDateTime resolveNextScreeningTime(LocalDate screeningDate) {
        Screening lastScreening = getLastScreening(screeningDate);

        if (lastScreening == null) {
            return LocalDateTime.of(screeningDate, FIRST_SCREENING_OF_DAY);
        }

        LocalDateTime screeningTime = lastScreening.getEndTime().plusMinutes(MINUTES_BETWEEN_SCREENINGS);
        //Input.isTrue(screeningTime.toLocalTime().isBefore(LAST_SCREENING_OF_DAY), "All screenings are occupied at the given date.");

        return screeningTime;
    }

    private Screening getLastScreening(LocalDate screeningDate) {
        return screenings.stream().filter(s -> s.isForDate(screeningDate))
                .max(Comparator.comparing(Screening::getStartTime))
                .orElse(null);
    }

}
