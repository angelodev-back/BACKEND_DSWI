package com.idat.samsung.Services.impl;

import com.idat.samsung.Dto.CategoriaDto;
import com.idat.samsung.Entity.Categoria;
import com.idat.samsung.Enum.Estado;
import com.idat.samsung.Repository.CategoriaRepository;
import com.idat.samsung.Services.CategoriaService;
import com.idat.samsung.mapper.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaDto> listarTodas() {
        log.info("Listando categorías");
        return categoriaRepository.findAll().stream()
                .map(CategoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriaDto> listarActivas() {
        log.info("Listando categorías activas");
        return categoriaRepository.findByEstado(Estado.ACTIVO).stream()
                .map(CategoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDto buscarPorId(Integer id) {
        log.info("Buscando categoría {}", id);
        return categoriaRepository.findById(id)
                .map(CategoriaMapper::toDto)
                .orElse(null);
    }

    @Override
    public CategoriaDto guardar(CategoriaDto dto) {
        log.info("Creando categoría {}", dto.getNombre());
        Categoria categoria = CategoriaMapper.toEntity(dto);
        if (categoria.getEstado() == null) {
            categoria.setEstado(Estado.ACTIVO);
        }
        Categoria guardada = categoriaRepository.save(categoria);
        return CategoriaMapper.toDto(guardada);
    }

    @Override
    public CategoriaDto actualizar(Integer id, CategoriaDto dto) {
        log.info("Actualizando categoría {}", id);
        return categoriaRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(dto.getNombre());
                    existing.setDescripcion(dto.getDescripcion());
                    existing.setEstado(dto.getEstado());
                    return CategoriaMapper.toDto(categoriaRepository.save(existing));
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        log.info("Eliminando categoría {}", id);
        categoriaRepository.deleteById(id);
    }
}
