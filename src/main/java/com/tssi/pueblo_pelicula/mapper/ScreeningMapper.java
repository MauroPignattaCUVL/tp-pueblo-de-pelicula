package com.tssi.pueblo_pelicula.mapper;

import com.tssi.pueblo_pelicula.dto.ScreeningDTO;
import com.tssi.pueblo_pelicula.model.Screening;

public class ScreeningMapper {

  public static ScreeningDTO toDto(Screening screening) {
    ScreeningDTO screeningDTO = new ScreeningDTO();
    screeningDTO.setId(screening.getId());
    screeningDTO.setBeginsAt(screening.getStartTime());
    screeningDTO.setMovieId(screening.getMovie().getId());

    return screeningDTO;
  }

}
