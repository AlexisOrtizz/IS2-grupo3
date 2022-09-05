package com.proyecto.is2.proyecto.services;

import com.proyecto.is2.proyecto.controller.dto.UsuarioDTO;
import com.proyecto.is2.proyecto.model.Permiso;
import com.proyecto.is2.proyecto.model.Rol;
import com.proyecto.is2.proyecto.model.Usuario;
import com.proyecto.is2.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolServiceImp rolService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario convertirDTO(UsuarioDTO objetoDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(objetoDTO.getUser());
        usuario.setEmail(objetoDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(objetoDTO.getPassword()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Del formulario Login trae el username, luego busca en la base de datos para
     * agregar el usuario como Current User en el Spring Security con su respectos
     * permisos.
     * @param username correo electronico del usuario
     * @return Spring Security User
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);

        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o password invalidos");
        }

        return new User(usuario.getEmail(), usuario.getPassword(), mapearPermisos(usuario.getRol().getPermisos()));
    }

    @Override
    public List<Usuario> listarUsuarios() {
        /* retorna una lista con todos los usuarios */
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario existeUsuario(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public boolean camibarRol(Usuario usuario, Rol rol) {
        return false;
    }

    @Override
    public void crearAdmin() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUser("admin");
        usuario.setEmail("admin@gmail.com");
        usuario.setPassword("12345678");

        Usuario user = convertirDTO(usuario);
        user.setRol(rolService.crearAdmin());
        usuarioRepository.save(user);

        System.out.println(user);

    }

    @Override
    public Set<Permiso> verPermisosUsuarioActual() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = this.existeUsuario(username);

        if(usuario == null) {
            return null;
        }

        return usuario.getRol().getPermisos();
    }

    @Override
    public boolean tienePermiso(String permiso) {
        for(Permiso aux : this.verPermisosUsuarioActual()) {
            if(permiso.equals(aux)) return true;
        }
        return false;
    }

    @Override
    public Usuario obtenerUsuario(Long id) {
        return usuarioRepository.findByIdUsuario(id);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
        return;
    }

    private Collection<? extends GrantedAuthority> mapearPermisos(Set<Permiso> permisos) {
        /* Mapea cada permiso del usuario a una lista para que Spring security reconozca como
         * permiso en el sistema.
         * */
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for(Permiso permiso : permisos) {
            authorities.add(new SimpleGrantedAuthority(permiso.toString()));
        }

        System.out.println("=== Roles ===");
        System.out.println(authorities);
        System.out.println("=== fin ===");

        return authorities;

    }
}
