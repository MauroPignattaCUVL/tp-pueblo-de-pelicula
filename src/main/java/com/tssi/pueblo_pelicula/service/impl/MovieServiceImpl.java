package com.tssi.pueblo_pelicula.service.impl;

import com.tssi.pueblo_pelicula.constant.MovieType;
import com.tssi.pueblo_pelicula.dto.MovieDTO;
import com.tssi.pueblo_pelicula.error.Input;
import com.tssi.pueblo_pelicula.error.exception.BusinessException;
import com.tssi.pueblo_pelicula.mapper.MovieMapper;
import com.tssi.pueblo_pelicula.model.Commercial;
import com.tssi.pueblo_pelicula.model.Documentary;
import com.tssi.pueblo_pelicula.model.Movie;
import com.tssi.pueblo_pelicula.repository.MovieRepository;
import com.tssi.pueblo_pelicula.service.MovieService;
import com.tssi.pueblo_pelicula.util.MovieUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

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
    public List<MovieDTO> getMovies() {
        List<Movie> movies = movieRepository.findAll(Sort.by("name"));
        return MovieMapper.toMovieDTOList(movies);
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
