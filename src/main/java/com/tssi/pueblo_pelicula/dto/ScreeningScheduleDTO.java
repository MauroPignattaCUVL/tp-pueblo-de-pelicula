package com.tssi.pueblo_pelicula.dto;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ScreeningScheduleDTO implements Serializable {

  @NotNull(message = "The cinema id cannot be null.")
  private Long cinemaId;

  @NotNull(message = "The theater id cannot be null.")
  private Long theaterId;

  @NotNull(message = "The movie id cannot be null.")
  private Long movieId;

  @NotNull(message = "The date cannot be null.")
  @Future(message = "Cannot schedule a screening in the past.")
  private LocalDate date;
}
