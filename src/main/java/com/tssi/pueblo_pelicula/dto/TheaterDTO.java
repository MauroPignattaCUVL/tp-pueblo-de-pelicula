package com.tssi.pueblo_pelicula.dto;

import com.tssi.pueblo_pelicula.constant.TheaterType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class TheaterDTO implements Serializable {

  private Long id;

  @NotNull(message = "The number cannot be null.")
  private Integer number;

  @NotNull(message = "The theaterType cannot be null.")
  private TheaterType theaterType;

  List<ScreeningDTO> screenings;
}
