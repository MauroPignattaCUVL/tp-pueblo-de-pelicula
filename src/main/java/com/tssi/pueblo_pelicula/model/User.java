package com.tssi.pueblo_pelicula.model;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    /** The entity's id. Might be null if not persisted yet. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    /** Orm's required constructor. */
    User() {
    }

    public User(String username, String password) {
        Validate.notBlank(username, "Username cannot be blank.");
        Validate.notBlank(password, "Password cannot be blank.");

        this.username = username;
        this.password = password;
    }
}
