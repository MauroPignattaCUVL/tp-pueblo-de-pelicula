package com.tssi.pueblo_pelicula.dto;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ScreeningReplanningDTO implements Serializable {

  @NotNull(message = "The cinema id cannot be null.")
  private Long cinemaId;

  @NotNull(message = "The theater id cannot be null.")
  private Long theaterId;

  @NotNull(message = "The date cannot be null.")
  @Future(message = "You can only make a replanning for future screenings.")
  private LocalDate date;
}
