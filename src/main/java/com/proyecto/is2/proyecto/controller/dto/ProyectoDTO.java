package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ProyectoDTO {
    private Integer idProyecto;
    private String nombre;
    private String descripcion;
    private String observacion;
    private String estado;
    private String fechaInicio;
    private String fechaFin;
}
