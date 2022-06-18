package com.tssi.pueblo_pelicula.model;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "movie_id")
public class Documentary extends Movie {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DocumentaryTheme documentaryTheme;

    /** Orm's required constructor. */
    Documentary() {
    }

    /** Constructor.
     *
     * @param name The name of the movie. Cannot be blank.
     *
     * @param duration The duration in minutes. Must be a number between 1 and 360.
     *
     * @param poster The poster of the movie. Cannot be blank.
     *
     * @param documentaryTheme The theme of the documentary. Cannot be null.
     */
    public Documentary(String name, int duration, String poster, DocumentaryTheme documentaryTheme) {
        super(name, duration, poster);
        Validate.notNull(documentaryTheme, "The documentaryTheme cannot be null.");

        this.documentaryTheme = documentaryTheme;
    }

    public DocumentaryTheme getDocumentaryTheme() {
        return documentaryTheme;
    }
}
