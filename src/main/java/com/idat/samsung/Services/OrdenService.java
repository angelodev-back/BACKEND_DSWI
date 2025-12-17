package com.idat.samsung.Services;

import com.idat.samsung.Dto.OrdenDetalleDto;
import com.idat.samsung.Dto.OrdenDto;
import com.idat.samsung.Enum.EstadoOrden;
import java.util.List;

public interface OrdenService {
    List<OrdenDto> listarTodas();
    List<OrdenDto> listarPorUsuario(Integer idUsuario);
    OrdenDto buscarPorId(Integer id);
    OrdenDto crearOrden(Integer idUsuario, List<OrdenDetalleDto> detalles);
    OrdenDto actualizarEstado(Integer id, EstadoOrden nuevoEstado);
}