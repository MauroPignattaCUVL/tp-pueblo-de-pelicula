package com.tssi.pueblo_pelicula.model;

import org.apache.commons.lang3.Validate;
`
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cinema {

    /** The entity's id. Might be null if not persisted yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany
    private Set<Theater> theaters;

    /** Orm's required constructor. */
    Cinema() {
    }

    /** Constructor.
     *
     * @param name The name of the cinema. Cannot be blank.
     */
    public Cinema(String name) {
        Validate.notBlank(name, "The name cannot be blank.");

        this.name = name;
        this.theaters = new HashSet<>();
    }
}
