package com.tssi.pueblo_pelicula.dto;

import com.tssi.pueblo_pelicula.constant.DocumentaryTheme;
import com.tssi.pueblo_pelicula.constant.MovieType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieDTO {

    @NotBlank(message = "The movie name is mandatory.")
    private String name;
    @Range(min = 1, max = 360, message = "The duration must be a number between 1 and 360.")
    private int duration;
    @NotBlank(message = "The movie poster is mandatory.")
    private String poster;
    private String synopsis;
    private List<String> actors;
    private DocumentaryTheme documentaryTheme;

    @NotNull(message= "The movie type is mandatory.")
    private MovieType movieType;
}
