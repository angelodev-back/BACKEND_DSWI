package com.idat.samsung.mapper;

import com.idat.samsung.Dto.ProductoDto;
import com.idat.samsung.Entity.Categoria;
import com.idat.samsung.Entity.Producto;

public class ProductoMapper {
    private ProductoMapper() {}

    public static ProductoDto toDto(Producto entity) {
        if (entity == null) return null;
        ProductoDto dto = new ProductoDto();
        dto.setIdProducto(entity.getIdProducto());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setStock(entity.getStock());
        dto.setIdCategoria(entity.getCategoria() != null ? entity.getCategoria().getIdCategoria() : null);
        dto.setNombreCategoria(entity.getCategoria() != null ? entity.getCategoria().getNombre() : null);
        dto.setImagen(entity.getImagen());
        dto.setEstado(entity.getEstado());
        dto.setFechaRegistro(entity.getFechaRegistro());
        return dto;
    }

    public static Producto toEntity(ProductoDto dto, Categoria categoria) {
        if (dto == null) return null;
        Producto entity = new Producto();
        entity.setIdProducto(dto.getIdProducto());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setStock(dto.getStock());
        entity.setCategoria(categoria);
        entity.setImagen(dto.getImagen());
        entity.setEstado(dto.getEstado());
        entity.setFechaRegistro(dto.getFechaRegistro());
        return entity;
    }
}
