package com.tssi.pueblo_pelicula.service.impl;

import com.tssi.pueblo_pelicula.dto.*;
import com.tssi.pueblo_pelicula.error.Input;
import com.tssi.pueblo_pelicula.mapper.CinemaMapper;
import com.tssi.pueblo_pelicula.mapper.ScreeningMapper;
import com.tssi.pueblo_pelicula.model.Cinema;
import com.tssi.pueblo_pelicula.model.Movie;
import com.tssi.pueblo_pelicula.model.Screening;
import com.tssi.pueblo_pelicula.model.Theater;
import com.tssi.pueblo_pelicula.repository.CinemaRepository;
import com.tssi.pueblo_pelicula.repository.MovieRepository;
import com.tssi.pueblo_pelicula.service.CinemaService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CinemaServiceImpl implements CinemaService {

  private CinemaRepository cinemaRepository;

  private MovieRepository movieRepository;

  @Override
  public List<CinemaNameAndIdDTO> getCinemasNamesAndIds() {
    List<CinemaNameAndIdDTO> cinemaNameAndIdDtos = new ArrayList<>();
    List<Cinema> cinemas = cinemaRepository.findAll();

    for (Cinema cinema : cinemas) {
      cinemaNameAndIdDtos.add(CinemaMapper.toCinemaKeyDTO(cinema));
    }

    return cinemaNameAndIdDtos;
  }

  @Override
  public CinemaDTO getCinemaById(final Long id) {
    Validate.notNull(id, "The id cannot be null.");
    Cinema cinema = cinemaRepository.findById(id).orElse(null);

    Input.found(cinema, "No cinema found with id: " + id);

    return CinemaMapper.toCinemaDto(cinema);
  }

  @Override
  public void scheduleScreening(final ScreeningScheduleDTO screeningScheduleDTO) {
    long cinemaId = screeningScheduleDTO.getCinemaId();
    long theaterId = screeningScheduleDTO.getTheaterId();
    long movieId = screeningScheduleDTO.getMovieId();
    LocalDate date = screeningScheduleDTO.getDate();

    Movie movie = movieRepository.findById(movieId).orElse(null);
    Input.found(movie, "No movie found with id: " + movieId);

    Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);
    Input.found(cinema, "No cinema found with id: " + cinemaId);

    Theater theater = cinema.getTheaters().stream()
        .filter(th -> th.getId().equals(theaterId))
        .findFirst().orElse(null);
    Input.notNull(theater, "The theater does not belong to the cinema.");

    theater.addScreening(movie, date);
  }

  @Override
  public void replanning(ScreeningReplanningDTO screeningReplanningDTO) {
    long cinemaId = screeningReplanningDTO.getCinemaId();
    long theaterId = screeningReplanningDTO.getTheaterId();
    LocalDate date = screeningReplanningDTO.getDate();

    Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);
    Input.found(cinema, "No cinema found with id: " + cinemaId);

    Theater theater = cinema.getTheaters().stream()
        .filter(th -> th.getId().equals(theaterId))
        .findFirst().orElse(null);
    Input.notNull(theater, "The theater does not belong to the cinema.");

    List<Screening> screeningsToRemove = theater.getScreenings().stream()
        .filter(s -> s.isForDate(date))
        .collect(Collectors.toList());
    Input.notEmpty(screeningsToRemove, "No screening scheduled for that date.");

    screeningsToRemove.forEach(theater.getScreenings()::remove);
  }

  @Override
  public List<ScreeningDTO> getScreeningsForDate(long cinemaId, long theaterId, LocalDate date) {
    Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);
    Input.found(cinema, "No cinema found with id: " + cinemaId);

    Theater theater = cinema.getTheaters().stream()
        .filter(th -> th.getId().equals(theaterId))
        .findFirst().orElse(null);
    Input.notNull(theater, "The theater does not belong to the cinema.");

    List<Screening> screeningsForDate = theater.getScreenings().stream()
        .filter(s -> s.isForDate(date))
        .collect(Collectors.toList());

    return screeningsForDate.stream().map(ScreeningMapper::toDto)
        .sorted(Comparator.comparing(ScreeningDTO::getBeginsAt))
        .collect(Collectors.toList());
  }
}
