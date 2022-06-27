package com.tssi.pueblo_pelicula.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany (fetch= FetchType.EAGER)
    private Set<Theater> theaters;

}
