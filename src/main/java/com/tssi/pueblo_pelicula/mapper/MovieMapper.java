package com.tssi.pueblo_pelicula.mapper;

import com.tssi.pueblo_pelicula.constant.MovieType;
import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.model.Commercial;
import com.tssi.pueblo_pelicula.model.Documentary;
import com.tssi.pueblo_pelicula.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {
    public static MovieDTO toDTO(final Movie movie) {

        if (movie.getMovieType().equals(MovieType.DOCUMENTARY)) {
            Documentary documentary = (Documentary) movie;
            return new MovieDTO(documentary.getName(), documentary.getDuration(), documentary.getPoster(), documentary.getResume(),
                    null, documentary.getDocumentaryTheme(), documentary.getMovieType());
        }

        Commercial commercial = (Commercial) movie;
        return new MovieDTO(commercial.getName(), commercial.getDuration(), commercial.getPoster(), commercial.getResume(),
                commercial.getActors(), null,commercial.getMovieType());
    }

    public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
        return movies.stream().map(movie -> toDTO(movie)).collect(Collectors.toList());
    }
}