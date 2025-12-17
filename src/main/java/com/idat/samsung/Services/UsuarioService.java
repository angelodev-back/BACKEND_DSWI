package com.idat.samsung.Services;

import com.idat.samsung.Dto.UsuarioDto;
import java.util.List;

public interface UsuarioService {
    UsuarioDto login(String email, String contraseña);
    List<UsuarioDto> listarTodos();
    List<UsuarioDto> listarActivos();
    UsuarioDto buscarPorId(Integer id);
    UsuarioDto buscarPorEmail(String email);
    UsuarioDto registrar(UsuarioDto dto, String contraseña);
    UsuarioDto actualizar(Integer id, UsuarioDto dto);
    void eliminar(Integer id);
}