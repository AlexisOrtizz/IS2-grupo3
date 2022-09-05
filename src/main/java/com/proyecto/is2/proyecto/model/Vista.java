package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Vista")
public class Vista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVista;

    @Column(name = "nombre")
    private String nombre;

    public Vista() {

    }

    public Vista(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return nombre;
    }

    /* RELACIONES DE BASE DE DATOS */
    @OneToMany(mappedBy = "vista")
    private Set<Permiso> permisos = new HashSet<>();
}
