package com.tssi.pueblo_pelicula.model;

import com.tssi.pueblo_pelicula.constant.MovieType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String poster;

    private String resume;

    public Movie(String name, int duration, String poster, String resume) {
        this.name = name;
        this.duration = duration;
        this.poster = poster;
        this.resume = resume;
    }

    public int getDuration() {
        return duration;
    }

    public abstract MovieType getMovieType();
}
