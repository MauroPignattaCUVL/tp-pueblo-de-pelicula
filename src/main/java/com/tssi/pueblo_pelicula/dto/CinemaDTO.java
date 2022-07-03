package com.tssi.pueblo_pelicula.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class CinemaDTO implements Serializable {

  private Long id;

  @NotNull(message = "The name cannot be null.")
  @NotEmpty(message = "The name cannot be empty.")
  private String name;

  private List<TheaterDTO> theaters;
}
