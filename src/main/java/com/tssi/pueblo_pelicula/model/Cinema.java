package com.tssi.pueblo_pelicula.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Cinema {

    /** The entity's id. Might be null if not persisted yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the cinema. Is usually related to where is located. Never null. */
    @Column(nullable = false, unique = true)
    private String name;

    /** The theaters that belong to the cine. Never null nor empty. */
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private Set<Theater> theaters;
}
