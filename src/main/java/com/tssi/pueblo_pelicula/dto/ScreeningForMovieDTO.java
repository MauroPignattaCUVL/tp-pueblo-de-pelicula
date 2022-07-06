package com.tssi.pueblo_pelicula.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ScreeningForMovieDTO {

  @NotNull(message = "The cinema id cannot be null.")
  private Long cinemaId;

  @NotNull(message = "The screenings cannot be null.")
  @NotEmpty(message = "The screenings cannot be empty.")
  private List<ScreeningDTO> screenings;
}
