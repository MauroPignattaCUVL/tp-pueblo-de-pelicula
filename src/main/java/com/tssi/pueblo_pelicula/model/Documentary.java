package com.tssi.pueblo_pelicula.model;

import com.tssi.pueblo_pelicula.constant.DocumentaryTheme;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "movie_id")
public class Documentary extends Movie {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DocumentaryTheme documentaryTheme;

    public Documentary(String name, int duration, String poster, String resume, DocumentaryTheme documentaryTheme) {
        super(name, duration, poster, resume);
        this.documentaryTheme = documentaryTheme;
    }

}
