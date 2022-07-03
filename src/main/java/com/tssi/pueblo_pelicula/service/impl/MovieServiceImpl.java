package com.tssi.pueblo_pelicula.service.impl;

import com.tssi.pueblo_pelicula.constant.MovieType;
import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.exception.MovieNotFoundException;
import com.tssi.pueblo_pelicula.mapper.MovieMapper;
import com.tssi.pueblo_pelicula.model.Cinema;
import com.tssi.pueblo_pelicula.model.Commercial;
import com.tssi.pueblo_pelicula.model.Documentary;
import com.tssi.pueblo_pelicula.model.Movie;
import com.tssi.pueblo_pelicula.repository.MovieRepository;
import com.tssi.pueblo_pelicula.service.CinemaService;
import com.tssi.pueblo_pelicula.service.MovieService;
import com.tssi.pueblo_pelicula.util.MovieUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private CinemaService cinemaService;

    public MovieServiceImpl(MovieRepository movieRepository,

                            CinemaService cinemaService) {
        this.movieRepository = movieRepository;
        this.cinemaService = cinemaService;
    }

    @Override
    public MovieDTO save(final MovieDTO movieDTO) {

        MovieUtil.checkResume(movieDTO);
        MovieType type = MovieUtil.getMovieType(movieDTO);
        switch (type) {
            case DOCUMENTARY:
                Documentary documentary = new Documentary(movieDTO.getName(), movieDTO.getDuration(), movieDTO.getPoster(), movieDTO.getSynopsis(),
                        movieDTO.getDocumentaryTheme());
                movieRepository.save(documentary);
                break;
            case COMMERCIAL:
                Commercial commercial = new Commercial(movieDTO.getName(), movieDTO.getDuration(), movieDTO.getPoster(), movieDTO.getSynopsis(),
                        movieDTO.getActors());
                movieRepository.save(commercial);
                break;
        }

        return movieDTO;
    }

    @Override
    public MovieDTO getMovieById(final Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new MovieNotFoundException(String.format("There isn´t a movie with id : %s", id));
        }

        return MovieMapper.toDTO(movie.get());
    }

    @Override
    public List<MovieDTO> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        return MovieMapper.toMovieDTOList(movies);
    }

    @Override
    public void deleteById(final Long id) {

        if (movieRepository.findById(id).isPresent()) {
            List<Cinema> cinemas = cinemaService.getAll();
            boolean founded = cinemas.stream()
                    .anyMatch(cinema -> cinema.getTheaters()
                            .stream()
                            .anyMatch(theater -> theater.getScreenings()
                                    .stream()
                                    .anyMatch(screening -> screening.getMovie().getId().equals(id))
                            )
                    );

            if (!founded) {
                movieRepository.deleteById(id);
            } else {
                throw new RuntimeException("La pelicula no puede borrarse porque sigue en funciones");
            }

        }

    }

}