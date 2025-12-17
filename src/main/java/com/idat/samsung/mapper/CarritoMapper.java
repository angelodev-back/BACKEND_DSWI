package com.idat.samsung.mapper;

import com.idat.samsung.Dto.CarritoDetalleDto;
import com.idat.samsung.Dto.CarritoDto;
import com.idat.samsung.Entity.Carrito;
import com.idat.samsung.Entity.CarritoDetalle;
import java.util.stream.Collectors;

public class CarritoMapper {
    private CarritoMapper() {}

    public static CarritoDto toDto(Carrito entity) {
        if (entity == null) return null;
        CarritoDto dto = new CarritoDto();
        dto.setIdCarrito(entity.getIdCarrito());
        dto.setIdUsuario(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null);
        dto.setFechaCreacion(entity.getFechaCreacion());
        if (entity.getDetalles() != null) {
            dto.setDetalles(entity.getDetalles().stream()
                    .map(CarritoMapper::toDetalleDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static CarritoDetalleDto toDetalleDto(CarritoDetalle detalle) {
        if (detalle == null) return null;
        CarritoDetalleDto dto = new CarritoDetalleDto();
        dto.setIdDetalle(detalle.getIdDetalle());
        dto.setIdProducto(detalle.getProducto() != null ? detalle.getProducto().getIdProducto() : null);
        dto.setNombreProducto(detalle.getProducto() != null ? detalle.getProducto().getNombre() : null);
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        return dto;
    }
}
