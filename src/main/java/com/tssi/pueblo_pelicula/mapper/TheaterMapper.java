package com.tssi.pueblo_pelicula.mapper;

import com.tssi.pueblo_pelicula.dto.ScreeningDTO;
import com.tssi.pueblo_pelicula.dto.TheaterDTO;
import com.tssi.pueblo_pelicula.model.Theater;

import java.util.List;
import java.util.stream.Collectors;

public class TheaterMapper {

  public static TheaterDTO toDto(final Theater theater) {
    TheaterDTO theaterDTO = new TheaterDTO();
    theaterDTO.setId(theater.getId());
    theaterDTO.setNumber(theater.getNumber());
    theaterDTO.setTheaterType(theater.getTheaterType());

    List<ScreeningDTO> screeningDTOS = theater.getScreenings().stream()
        .map(ScreeningMapper::toDto).collect(Collectors.toList());
    theaterDTO.setScreenings(screeningDTOS);

    return theaterDTO;
  }
}
