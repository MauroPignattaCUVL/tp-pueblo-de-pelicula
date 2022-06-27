package com.tssi.pueblo_pelicula.util;

import com.tssi.pueblo_pelicula.constant.MovieType;
import com.tssi.pueblo_pelicula.dto.MovieDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.tssi.pueblo_pelicula.constant.MovieType.COMMERCIAL;
import static com.tssi.pueblo_pelicula.constant.MovieType.DOCUMENTARY;

public class MovieUtil {

    private static final int RESUME_MAX_LENGTH = 500;
    private static final int ACTOR_NAME_MAX_LENGTH = 60;

    public static MovieType getMovieType(final MovieDTO movieDTO) {
        MovieType movieType = null;

        if (CollectionUtils.isEmpty(movieDTO.getActors()) && movieDTO.getDocumentaryTheme() != null) {
            //chequear si tiene un documentary theme valido
            movieType = DOCUMENTARY;
        } else if (movieDTO.getDocumentaryTheme() == null && !CollectionUtils.isEmpty(movieDTO.getActors())
                && actorsAreValid(movieDTO.getActors())) {
            movieType = COMMERCIAL;
        }

        return movieType;
    }

    public static boolean checkResume(final MovieDTO movieDTO) {
        //esto podria tirar error
        return movieDTO.getResume() == null || movieDTO.getResume().length() <= RESUME_MAX_LENGTH;
    }

    private static boolean actorsAreValid(List<String> actors) {
        return actors.stream().noneMatch(actor -> actor.length() > ACTOR_NAME_MAX_LENGTH);
    }
}
