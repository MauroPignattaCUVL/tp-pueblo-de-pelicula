package com.tssi.pueblo_pelicula.mapper;

import com.tssi.pueblo_pelicula.constant.MovieType;
import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.error.BusinessException;
import com.tssi.pueblo_pelicula.model.Commercial;
import com.tssi.pueblo_pelicula.model.Documentary;
import com.tssi.pueblo_pelicula.model.Movie;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    public static MovieDTO toDTOWithoutPoster(final Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setName(movie.getName());
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setSynopsis(movie.getSynopsis());
        movieDTO.setMovieType(movie.getMovieType());

        if (movie.getMovieType() == MovieType.DOCUMENTARY) {
            Documentary documentary = (Documentary) movie;
            movieDTO.setDocumentaryTheme(documentary.getDocumentaryTheme());

            return movieDTO;
        }

        if (movie.getMovieType() == MovieType.COMMERCIAL) {
            Commercial commercial = (Commercial) movie;
            movieDTO.setActors(commercial.getActors());

            return movieDTO;
        }

        throw new BusinessException("Unknown movie type.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static MovieDTO toDTO(final Movie movie) {
        MovieDTO movieDTO = toDTOWithoutPoster(movie);
        movieDTO.setPoster(movie.getPoster());

        return movieDTO;
    }

    public static List<MovieDTO> toMovieDTOS(Collection<Movie> movies) {
        return movies.stream().map(MovieMapper::toDTOWithoutPoster)
            .collect(Collectors.toList());
    }
}