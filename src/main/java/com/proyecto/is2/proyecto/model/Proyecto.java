package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyecto;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fechaInicio")
    private String fechaInicio;

    @Column(name = "fechaFin")
    private String fechaFin;

    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con Backlog */
    @OneToMany(mappedBy = "proyecto",  cascade = CascadeType.ALL)
    private Set<Backlog> backlogs = new HashSet<>();

    /* Relacion con Usuario para formar equipos */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "proyecto_usuario",
            joinColumns = {@JoinColumn(name = "proyecto_id")},
            inverseJoinColumns = {@JoinColumn(name = "usuario_id")})
    private Set<Usuario> equipo = new HashSet<>();
}
