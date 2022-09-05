package com.proyecto.is2.proyecto.controller.dto;

import lombok.Data;

@Data
public class ProyectoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String observacion;
    private String estado;
    private String fechaInicio;
    private String fechaFin;
}
