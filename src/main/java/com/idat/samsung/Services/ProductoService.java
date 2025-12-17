package com.idat.samsung.Services;

import com.idat.samsung.Dto.ProductoDto;
import java.util.List;

public interface ProductoService {
    List<ProductoDto> listarTodos();
    List<ProductoDto> listarActivos();
    List<ProductoDto> listarPorCategoria(Integer idCategoria);
    ProductoDto buscarPorId(Integer id);
    ProductoDto guardar(ProductoDto dto);
    ProductoDto actualizar(Integer id, ProductoDto dto);
    void eliminar(Integer id);
}