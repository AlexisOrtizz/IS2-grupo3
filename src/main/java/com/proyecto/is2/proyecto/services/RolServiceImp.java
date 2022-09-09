package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.RolDTO;
import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class RolServiceImp implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoServiceImp permisoService;

    public Rol crearAdmin() {
        permisoService.crearPermisos();

        Rol admin = new Rol("admin", "Super usuario del sistema");

        /* asignacion de todos los permisos al admin */
        for(Permiso permiso : permisoService.listar()) {
            admin.getPermisos().add(permiso);
        }

        return rolRepository.save(admin);

    }

    @Override
    public Rol existeRol(Long id) {
        return rolRepository.findByIdRol(id);
    }

    @Override
    public void convertirDTO(Rol rol, RolDTO objetoDTO) {
        rol.setNombre(objetoDTO.getNombre());
        rol.setDescripcion(objetoDTO.getDescripcion());
        return;
    }

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    @Override
    public void eliminarRol(Rol rol) {
        rolRepository.delete(rol);
    }

}
