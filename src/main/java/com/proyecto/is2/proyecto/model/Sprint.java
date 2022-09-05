package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Sprint")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSprint;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "fechaInicio")
    private String fechaInicio;

    @Column(name = "fechaFin")
    private String fechaFin;

    @Column(name = "estado")
    private String estado;

    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con UserStory */
    @OneToMany(mappedBy = "sprint",  cascade = CascadeType.ALL)
    private Set<UserStory> userStories = new HashSet<>();

    /**/
    @ManyToOne
    @JoinColumn(name = "backlog_id", referencedColumnName = "idBacklog")
    private Backlog backlog;
}
