package com.upiiz.equipo_cliente.services;

import com.upiiz.equipo_cliente.entities.UsuarioEntity;
import com.upiiz.equipo_cliente.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity registrarUsuario(UsuarioEntity usuario) throws Exception {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new Exception("Ya existe un usuario con este correo electrónico.");
        }
        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity autenticar(String correo, String contrasena) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            UsuarioEntity usuario = usuarioOpt.get();
            if (usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        return null;
    }
    
    public String recuperarContrasena(String correo) throws Exception {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get().getContrasena();
        } else {
            throw new Exception("No se encontró ningún usuario con ese correo.");
        }
    }
}
