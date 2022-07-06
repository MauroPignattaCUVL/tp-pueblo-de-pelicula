package com.tssi.pueblo_pelicula.mapper;

import com.tssi.pueblo_pelicula.dto.ScreeningDTO;
import com.tssi.pueblo_pelicula.dto.ScreeningForMovieDTO;
import com.tssi.pueblo_pelicula.model.Screening;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScreeningMapper {

  public static ScreeningDTO toDto(Screening screening) {
    ScreeningDTO screeningDTO = new ScreeningDTO();
    screeningDTO.setId(screening.getId());
    screeningDTO.setBeginsAt(screening.getStartTime());
    screeningDTO.setMovieId(screening.getMovie().getId());

    return screeningDTO;
  }

  public static List<ScreeningForMovieDTO> toScreeningsForMovieDTO(Map<Long, List<Screening>> screeningsForMovie) {
    List<ScreeningForMovieDTO> screeningForMovieDTOS = new ArrayList<>();
    for (Map.Entry<Long, List<Screening>> entry : screeningsForMovie.entrySet()) {
      ScreeningForMovieDTO screeningForMovieDTO = new ScreeningForMovieDTO();
      screeningForMovieDTO.setCinemaId(entry.getKey());

      List<ScreeningDTO> screenings = entry.getValue().stream()
          .map(ScreeningMapper::toDto).collect(Collectors.toList());
      screeningForMovieDTO.setScreenings(screenings);

      screeningForMovieDTOS.add(screeningForMovieDTO);
    }

    return screeningForMovieDTOS;
  }
}
