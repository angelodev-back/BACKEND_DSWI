package com.idat.samsung.mapper;

import com.idat.samsung.Dto.UsuarioDto;
import com.idat.samsung.Entity.Usuario;

public class UsuarioMapper {
    private UsuarioMapper() {}

    public static UsuarioDto toDto(Usuario entity) {
        if (entity == null) return null;
        UsuarioDto dto = new UsuarioDto();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setEstado(entity.getEstado());
        dto.setFechaRegistro(entity.getFechaRegistro());
        return dto;
    }
}
