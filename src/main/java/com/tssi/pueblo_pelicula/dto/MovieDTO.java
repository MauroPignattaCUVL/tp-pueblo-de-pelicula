package com.tssi.pueblo_pelicula.dto;

import com.tssi.pueblo_pelicula.constant.DocumentaryTheme;
import com.tssi.pueblo_pelicula.constant.MovieType;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class MovieDTO implements Serializable {

    private Long id;

    @NotNull(message = "The name cannot be null.")
    @NotEmpty(message = "The movie name is empty.")
    private String name;

    @NotNull(message = "The duration cannot be null.")
    @Range(min = 1, max = 360, message = "The duration must last between 1 and 360 minutes.")
    private Integer duration;

    @NotBlank(message = "The movie poster is mandatory.")
    private String poster;

    @NotEmpty(message = "The synopsis cannot be empty.")
    private String synopsis;

    private List<String> actors;

    private DocumentaryTheme documentaryTheme;

    @NotNull(message= "The movie type cannot be null.")
    private MovieType movieType;
}
