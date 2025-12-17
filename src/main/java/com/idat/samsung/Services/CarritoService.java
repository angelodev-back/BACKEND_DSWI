package com.idat.samsung.Services;

import com.idat.samsung.Dto.CarritoDto;

public interface CarritoService {
    CarritoDto obtenerPorUsuario(Integer idUsuario);
    CarritoDto agregarItem(Integer idUsuario, Integer idProducto, Integer cantidad);
    CarritoDto eliminarItem(Integer idUsuario, Integer idDetalle);
    CarritoDto vaciar(Integer idUsuario);
}
