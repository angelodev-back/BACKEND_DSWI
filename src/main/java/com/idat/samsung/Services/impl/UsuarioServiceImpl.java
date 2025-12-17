package com.idat.samsung.Services.impl;

import com.idat.samsung.Dto.UsuarioDto;
import com.idat.samsung.Entity.Usuario;
import com.idat.samsung.Enum.Estado;
import com.idat.samsung.Repository.UsuarioRepository;
import com.idat.samsung.Services.UsuarioService;
import com.idat.samsung.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto login(String email, String contraseña) {
        log.info("Login usuario {}", email);
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> usuario.getContraseña().equals(contraseña))
                .filter(usuario -> usuario.getEstado() == Estado.ACTIVO)
                .map(UsuarioMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<UsuarioDto> listarTodos() {
        log.info("Listando usuarios");
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> listarActivos() {
        log.info("Listando usuarios activos");
        return usuarioRepository.findByEstado(Estado.ACTIVO).stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto buscarPorId(Integer id) {
        log.info("Buscando usuario {}", id);
        return usuarioRepository.findById(id)
                .map(UsuarioMapper::toDto)
                .orElse(null);
    }

    @Override
    public UsuarioDto buscarPorEmail(String email) {
        log.info("Buscando usuario por email {}", email);
        return usuarioRepository.findByEmail(email)
                .map(UsuarioMapper::toDto)
                .orElse(null);
    }

    @Override
    public UsuarioDto registrar(UsuarioDto dto, String contraseña) {
        log.info("Registrando usuario {}", dto.getEmail());
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setContraseña(contraseña);
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setEstado(Estado.ACTIVO);
        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioMapper.toDto(guardado);
    }

    @Override
    public UsuarioDto actualizar(Integer id, UsuarioDto dto) {
        log.info("Actualizando usuario {}", id);
        return usuarioRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(dto.getNombre());
                    existing.setApellido(dto.getApellido());
                    existing.setTelefono(dto.getTelefono());
                    existing.setDireccion(dto.getDireccion());
                    existing.setEstado(dto.getEstado());
                    return UsuarioMapper.toDto(usuarioRepository.save(existing));
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        log.info("Eliminando usuario {}", id);
        usuarioRepository.deleteById(id);
    }
}
