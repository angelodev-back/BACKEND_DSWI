package com.idat.samsung.mapper;

import com.idat.samsung.Dto.CategoriaDto;
import com.idat.samsung.Entity.Categoria;

public class CategoriaMapper {
    private CategoriaMapper() {}

    public static CategoriaDto toDto(Categoria entity) {
        if (entity == null) return null;
        CategoriaDto dto = new CategoriaDto();
        dto.setIdCategoria(entity.getIdCategoria());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEstado(entity.getEstado());
        return dto;
    }

    public static Categoria toEntity(CategoriaDto dto) {
        if (dto == null) return null;
        Categoria entity = new Categoria();
        entity.setIdCategoria(dto.getIdCategoria());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEstado(dto.getEstado());
        return entity;
    }
}
