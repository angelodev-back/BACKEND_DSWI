package com.idat.samsung.Services;

import com.idat.samsung.Dto.CategoriaDto;
import java.util.List;

public interface CategoriaService {
    List<CategoriaDto> listarTodas();
    List<CategoriaDto> listarActivas();
    CategoriaDto buscarPorId(Integer id);
    CategoriaDto guardar(CategoriaDto dto);
    CategoriaDto actualizar(Integer id, CategoriaDto dto);
    void eliminar(Integer id);
}