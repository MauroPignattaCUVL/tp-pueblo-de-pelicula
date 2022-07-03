package com.tssi.pueblo_pelicula.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ScreeningDTO implements Serializable {

  private Long id;

  private LocalDateTime beginsAt;

  private Long movieId;
}
