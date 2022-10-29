package com.proyecto.is2.proyecto.controller.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SprintDTO {
    private Long idSprint;
    private String titulo;
    private String duracion;
    private String fechaInicio;
    private String fechaFin;
    private String estado;

    public SprintDTO() {
    }

    public SprintDTO(Long idSprint, String titulo, String duracion, String fechaInicio, String fechaFin, String estado) {
        this.idSprint = idSprint;
        this.titulo = titulo;
        this.duracion = duracion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }
}
