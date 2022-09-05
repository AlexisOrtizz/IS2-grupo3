package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Permiso")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermiso;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    public Permiso() {

    }

    public Permiso(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String toString() {
        return this.nombre + "-" + this.getVista();
    }

    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con Vista */
    @ManyToOne
    @JoinColumn(name = "vista_id", referencedColumnName = "idVista")
    private Vista vista;

} /* Se crea un relacion tambien con Rol */
