package com.idat.samsung.mapper;

import com.idat.samsung.Dto.OrdenDetalleDto;
import com.idat.samsung.Dto.OrdenDto;
import com.idat.samsung.Entity.Orden;
import com.idat.samsung.Entity.OrdenDetalle;
import java.util.stream.Collectors;

public class OrdenMapper {
    private OrdenMapper() {}

    public static OrdenDto toDto(Orden entity) {
        if (entity == null) return null;
        OrdenDto dto = new OrdenDto();
        dto.setIdOrden(entity.getIdOrden());
        dto.setIdUsuario(entity.getUsuario() != null ? entity.getUsuario().getIdUsuario() : null);
        dto.setNombreUsuario(entity.getUsuario() != null
                ? (entity.getUsuario().getNombre() + " " + entity.getUsuario().getApellido())
                : null);
        dto.setFechaOrden(entity.getFechaOrden());
        dto.setTotal(entity.getTotal());
        dto.setEstado(entity.getEstado());
        if (entity.getDetalles() != null) {
            dto.setDetalles(entity.getDetalles().stream()
                    .map(OrdenMapper::toDetalleDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static OrdenDetalleDto toDetalleDto(OrdenDetalle detalle) {
        if (detalle == null) return null;
        OrdenDetalleDto dto = new OrdenDetalleDto();
        dto.setIdProducto(detalle.getProducto() != null ? detalle.getProducto().getIdProducto() : null);
        dto.setNombreProducto(detalle.getProducto() != null ? detalle.getProducto().getNombre() : null);
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
