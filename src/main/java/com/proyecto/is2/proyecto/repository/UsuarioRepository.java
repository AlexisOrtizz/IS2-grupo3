package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByEmail(String email);
    public Usuario findByIdUsuario(Long idUsuario);

}
