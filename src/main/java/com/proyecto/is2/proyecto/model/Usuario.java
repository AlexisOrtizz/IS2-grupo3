package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public String toString() {
        return email;
    }

    /* RELACIONES DE BASE DE DATOS */

    /** Relacion con Rol **/
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "idRol")
    private Rol rol;

    /* Relacion con UserStory */
    @OneToMany(mappedBy = "usuario")
    private Set<UserStory> userStories = new HashSet<>();

} /* Se crea un relacion tambien con Proyecto */
