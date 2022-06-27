package com.tssi.pueblo_pelicula.constant;

import com.tssi.pueblo_pelicula.model.Documentary;
import com.tssi.pueblo_pelicula.model.Movie;
import org.apache.commons.lang3.Validate;

/** Enumerates the different types of theaters. */
public enum TheaterType {
    REGULAR, HIGH_DEFINITION;

    /** Indicates if this instance supports the given movie.
     *
     * @param movie The movie to check if it is supported. Cannot be null.
     *
     * @return true if the movie is supported, false otherwise.
     */
    public boolean supports(final Movie movie) {
        Validate.notNull(movie, "The movie cannot be null.");

        return !(this == HIGH_DEFINITION && movie.getClass().equals(Documentary.class));
    }
}
