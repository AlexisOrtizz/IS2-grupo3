package com.proyecto.is2.proyecto.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Permite tener una plantilla de los metodos para
 * realizar un CRUD en un controlador.
 * @param <T> Es un objeto DTO
 */
public interface CRUD<T> {

    /**
     * Retorna la vista donde se encuentran los formularios
     * para las operaciones CRUD.
     * @return vista de operaciones CRUD
     */
    public String mostrarCRUDTemplate(Model model);

    /**
     * Retorna la vista donde se encuentran todos
     * los objetos creados.
     * @return vista de tabla de objetos
     */
    public String mostrarObjetos();

    /**
     * Request para crear un objeto
     * @param objetoDTO donde se encuentra los datos del formulario
     * @return vista de operaciones CRUD
     */
    public String crearObjeto(T objetoDTO);

    /**
     * Request para eliminar un objeto
     * @param id del objeto a eliminar
     * @return vista de operaciones CRUD
     */
    public String eliminarObjeto(Integer id);

    /**
     * Request para actualizar un objeto
     * @param objetoDTO donde se encuentra los datos del formulario
     * @return vista de operaciones CRUD
     */
    public String actualizarObjeto(T objetoDTO);
}
