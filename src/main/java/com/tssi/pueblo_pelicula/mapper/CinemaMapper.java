package com.tssi.pueblo_pelicula.mapper;

import com.tssi.pueblo_pelicula.dto.CinemaDTO;
import com.tssi.pueblo_pelicula.dto.CinemaNameAndIdDTO;
import com.tssi.pueblo_pelicula.dto.TheaterDTO;
import com.tssi.pueblo_pelicula.model.Cinema;
import com.tssi.pueblo_pelicula.model.Theater;

import java.util.List;
import java.util.stream.Collectors;

public class CinemaMapper {

  public static CinemaNameAndIdDTO toCinemaKeyDTO(final Cinema cinema) {
    CinemaNameAndIdDTO cinemaKey = new CinemaNameAndIdDTO();
    cinemaKey.setId(cinema.getId());
    cinemaKey.setName(cinema.getName());

    return cinemaKey;
  }

  public static CinemaDTO toCinemaDto(final Cinema cinema) {
    CinemaDTO cinemaDto = new CinemaDTO();
    cinemaDto.setId(cinema.getId());
    cinemaDto.setName(cinema.getName());

    List<TheaterDTO> theaterDTOS = cinema.getTheaters().stream()
        .map(TheaterMapper::toDto).collect(Collectors.toList());
    cinemaDto.setTheaters(theaterDTOS);

    return cinemaDto;
  }
}
