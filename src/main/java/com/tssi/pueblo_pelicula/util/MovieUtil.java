package com.tssi.pueblo_pelicula.util;

import com.tssi.pueblo_pelicula.constant.MovieType;
import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.error.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.tssi.pueblo_pelicula.constant.MovieType.COMMERCIAL;
import static com.tssi.pueblo_pelicula.constant.MovieType.DOCUMENTARY;

public class MovieUtil {

    private static final int RESUME_MAX_LENGTH = 500;
    private static final int ACTOR_NAME_MAX_LENGTH = 60;

    public static MovieType getMovieType(final MovieDTO movieDTO) {
        MovieType movieType = MovieType.valueOf(movieDTO.getMovieType().toString().toUpperCase());
        if (COMMERCIAL.equals(movieType) && (CollectionUtils.isEmpty(movieDTO.getActors()) || !actorsAreValid(movieDTO.getActors())) ||
                DOCUMENTARY.equals(movieType) && movieDTO.getDocumentaryTheme() == null) {
            throw new BusinessException(
                "The list of actor or documentary theme does not match with the movie type entered.", HttpStatus.BAD_REQUEST);
        }

        return movieType;
    }

    public static void checkSynopsis(final MovieDTO movieDTO) {
        if (movieDTO.getSynopsis() != null && movieDTO.getSynopsis().length() > RESUME_MAX_LENGTH) {
            throw new BusinessException(
                "The synopsis of the movie is invalid.", HttpStatus.BAD_REQUEST);
        }
    }

    private static boolean actorsAreValid(List<String> actors) {
        return actors.stream().noneMatch(actor -> actor.length() > ACTOR_NAME_MAX_LENGTH);
    }
}
