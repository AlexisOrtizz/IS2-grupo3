package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Backlog")
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBacklog;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con Sprint */
    @OneToMany(mappedBy = "backlog",  cascade = CascadeType.ALL)
    private Set<Sprint> sprints = new HashSet<>();

    /* Relacion con Proyecto */
    @OneToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
}
