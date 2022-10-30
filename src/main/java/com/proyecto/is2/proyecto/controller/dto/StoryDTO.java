package com.proyecto.is2.proyecto.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryDTO {
    private Long idHistoria;
    private String descripcion;
    private String observacion;
    private String estado;
    private String prioridad;
    private Long idUsuario;

    public StoryDTO() {
    }

    public StoryDTO(Long idHistoria, String descripcion, String observacion, String estado, String prioridad, Long idUsuario) {
        this.idHistoria = idHistoria;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.idUsuario = idUsuario;
    }
}
