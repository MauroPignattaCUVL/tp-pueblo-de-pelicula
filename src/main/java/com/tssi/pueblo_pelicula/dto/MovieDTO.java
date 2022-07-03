package com.tssi.pueblo_pelicula.dto;

import com.tssi.pueblo_pelicula.constant.DocumentaryTheme;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class MovieDTO implements Serializable {

    @NotBlank(message = "The movie name is mandatory.")
    private String name;
    @NotNull
    @Range(min = 1, max = 360, message = "The duration must be a number between 1 and 360.")
    private int duration;
    @NotBlank(message = "The movie image is mandatory.")
    private String image;
    private String resume;
    private List<String> actors;
    private DocumentaryTheme documentaryTheme;

}
