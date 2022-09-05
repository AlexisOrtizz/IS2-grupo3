package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.RolDTO;
import com.proyecto.is2.proyecto.model.Rol;

import java.util.List;

public interface RolService {

    /**
     * Este método mapea los datos de un formulario para convertirlos
     * a un objeto de tipo Rol
     * @param objetoDTO los datos del formulario
     * @return el objeto Rol creado
     */
    public Rol convertirDTO(RolDTO objetoDTO);

    /**
     * Persiste un objeto del tipo Usuario
     * @param rol objeto a persistir
     * @return el objeto persistido
     */
    public Rol guardar(Rol rol);

    /**
     * Lista todos los roles que existen
     * @return
     */
    public List<Rol> listar();

    /**
     * Realiza una busqueda entre los roles creados
     * @param id identificador del rol a buscar
     * @return Rol encontrado o null.
     */
    public Rol existeRol(Long id);

    public void eliminarRol(Rol rol);

}
