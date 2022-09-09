package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.PermisoDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Vista;
import com.proyecto.is2.proyecto.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermisoServiceImp implements PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private VistaServiceImp vistaService;

    public void crearPermisos() {
        vistaService.crearVistas();

        List<Permiso> permisos = new ArrayList<>();

        permisos.add(new Permiso("conectarse", "Permiso para conectase al sistema"));
        permisos.add(new Permiso("asignar-rol-usuario", "Permite asignar roles con permiso a un Usuario"));
        permisos.add(new Permiso("asignar-permisos-rol", "Permite asignar permisos a un rol"));
        permisos.add(new Permiso("agregar-miembro-proyecto", "Permite agregar nuevos miembros al proyecto"));
        permisos.add(new Permiso("eliminar-miembro-proyecto", "Permite eliminar miembros del proyecto"));

        for(Vista vista : vistaService.listar()) {
            Permiso p1 = new Permiso("crear", "Permiso de creacion");
            p1.setVista(vista);
            Permiso p2 = new Permiso("actualizar", "Permiso de actualizacion");
            p2.setVista(vista);
            Permiso p3 = new Permiso("eliminar", "Permiso de eliminacion");
            p3.setVista(vista);
            Permiso p4 = new Permiso("consultar", "Permiso de consulta");
            p4.setVista(vista);

            permisos.addAll(Arrays.asList(p1, p2, p3, p4));
        }

        permisoRepository.saveAll(permisos);
    }

    @Override
    public void convertirDTO(Permiso permiso, PermisoDTO objetoDTO) {
        permiso.setNombre(objetoDTO.getNombre());
        permiso.setDescripcion(objetoDTO.getDescripcion());
        return;
    }

    @Override
    public Permiso guardar(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @Override
    public List<Permiso> listar() {
        return permisoRepository.findAll();
    }

    @Override
    public Permiso existePermiso(Long id) {
        return permisoRepository.findByIdPermiso(id);
    }

    @Override
    public void eliminarPermiso(Permiso permiso) {
        permisoRepository.delete(permiso);
    }
}
