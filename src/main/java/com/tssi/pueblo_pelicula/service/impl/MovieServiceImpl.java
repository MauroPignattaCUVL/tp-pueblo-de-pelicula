package com.tssi.pueblo_pelicula.service.impl;

import com.tssi.pueblo_pelicula.constant.DocumentaryTheme;
import com.tssi.pueblo_pelicula.constant.MovieType;
import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.dto.ScreeningForMovieDTO;
import com.tssi.pueblo_pelicula.error.Input;
import com.tssi.pueblo_pelicula.error.BusinessException;
import com.tssi.pueblo_pelicula.mapper.MovieMapper;
import com.tssi.pueblo_pelicula.mapper.ScreeningMapper;
import com.tssi.pueblo_pelicula.model.Commercial;
import com.tssi.pueblo_pelicula.model.Documentary;
import com.tssi.pueblo_pelicula.model.Movie;
import com.tssi.pueblo_pelicula.model.Screening;
import com.tssi.pueblo_pelicula.repository.MovieRepository;
import com.tssi.pueblo_pelicula.repository.ScreeningRepository;
import com.tssi.pueblo_pelicula.service.MovieService;
import com.tssi.pueblo_pelicula.util.MovieUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final ScreeningRepository screeningRepository;

    @Override
    public MovieDTO save(final MovieDTO movieDTO) {
        MovieUtil.checkSynopsis(movieDTO);
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
        Movie movie = movieRepository.findById(id).orElse(null);
        Input.found(movie, "No movie found with id: " + id);

        return MovieMapper.toDTO(movie);
    }

    @Override
    public String getMoviePoster(final Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        Input.found(movie, "No movie found with id: " + id);

        return movie.getPoster();
    }

    @Override
    public List<MovieDTO> getMovies(Long cinemaId, String filter) {
        if (cinemaId == null && filter == null) {
            return MovieMapper.toMovieDTOS(movieRepository.findAll(Sort.by("name")));
        }

        Input.notNull(cinemaId, "The cinema id cannot be null.");
        Set<Movie> moviesInCinema = movieRepository.getMoviesScheduledInCinema(cinemaId);
        if (filter == null) {
            return MovieMapper.toMovieDTOS(moviesInCinema);
        }

        Set<Movie> filteredMovies = movieRepository.getMoviesByName(filter);
        filteredMovies.addAll(movieRepository.getCommercialMovieByActor(filter));
        DocumentaryTheme documentaryTheme = DocumentaryTheme.fromValue(filter);
        if (documentaryTheme != null) {
            filteredMovies.addAll(movieRepository.getDocumentaryByTheme(documentaryTheme));
        }

        Set<Movie> intersection = moviesInCinema.stream()
            .filter(filteredMovies::contains)
            .sorted(Comparator.comparing(Movie::getName))
            .collect(Collectors.toCollection(LinkedHashSet::new));

        return MovieMapper.toMovieDTOS(intersection);
    }

    @Override
    public List<ScreeningForMovieDTO> getScreeningsForMovie(Long id) {
        List<Screening> screeningsForMovie = screeningRepository.getScreeningsForMovie(id);
        Map<Long, List<Screening>> screeningsByCinemaId = new HashMap<>();
        for (Screening screening : screeningsForMovie) {
            Long cinemaId = screening.getTheater().getCinema().getId();

            List<Screening> screenings = screeningsByCinemaId.get(cinemaId);
            if (screenings != null) {
                screenings.add(screening);
            } else {
                screenings = new ArrayList<>(Collections.singletonList(screening));
                screeningsByCinemaId.put(cinemaId, screenings);
            }
        }

        return ScreeningMapper.toScreeningsForMovieDTO(screeningsByCinemaId);
    }

    @Override
    public void deleteById(final Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        Input.found(movie, "No movie found with id: " + id);

        try {
            movieRepository.delete(movie);
        } catch (ConstraintViolationException e) {
            throw new BusinessException(
                "The movie cant be deleted because is still in theaters.", HttpStatus.BAD_REQUEST);
        }
    }
}
