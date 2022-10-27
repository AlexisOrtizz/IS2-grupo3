package com.proyecto.is2.proyecto.controller.dto;

import com.proyecto.is2.proyecto.model.Proyecto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
public class ProyectoDTO {
    private Long idProyecto;
    private String titulo;
    private String descripcion;
    private String observacion;
    private String estado;
    private String fechaInicio;
    private String fechaFin;

    public ProyectoDTO() {}

    public ProyectoDTO(Long idProyecto, String titulo, String descripcion, String observacion, String estado,
                       String fechaInicio, String fechaFin) {
        this.idProyecto = idProyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
