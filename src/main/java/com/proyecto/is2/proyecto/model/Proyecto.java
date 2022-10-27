package com.proyecto.is2.proyecto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    private LocalDate fechaInicio;

    @Column(name = "fechaFin")
    private LocalDate fechaFin;

    public String toString() {
        return this.titulo + " ("+ this.estado + ")";
    }

    /* RELACIONES DE BASE DE DATOS */

    /* Relacion con Backlog */
    @OneToOne//(mappedBy = "proyecto")
    private Backlog backlog;

    /* Relacion con Usuario para formar equipos */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "proyecto_usuario",
            joinColumns = {@JoinColumn(name = "proyecto_id")},
            inverseJoinColumns = {@JoinColumn(name = "usuario_id")})
    private Set<Usuario> equipo = new HashSet<>();
}
