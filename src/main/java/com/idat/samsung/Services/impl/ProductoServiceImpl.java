package com.idat.samsung.Services.impl;

import com.idat.samsung.Dto.ProductoDto;
import com.idat.samsung.Entity.Categoria;
import com.idat.samsung.Entity.Producto;
import com.idat.samsung.Enum.Estado;
import com.idat.samsung.Repository.CategoriaRepository;
import com.idat.samsung.Repository.ProductoRepository;
import com.idat.samsung.Services.ProductoService;
import com.idat.samsung.mapper.ProductoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<ProductoDto> listarTodos() {
        log.info("Listando todos los productos");
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDto> listarActivos() {
        log.info("Listando productos activos");
        return productoRepository.findByEstado(Estado.ACTIVO)
                .stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDto> listarPorCategoria(Integer idCategoria) {
        log.info("Listando productos por categoría {}", idCategoria);
        return productoRepository.findByCategoriaIdCategoria(idCategoria)
                .stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDto buscarPorId(Integer id) {
        log.info("Buscando producto {}", id);
        return productoRepository.findById(id)
                .map(ProductoMapper::toDto)
                .orElse(null);
    }

    @Override
    public ProductoDto guardar(ProductoDto dto) {
        log.info("Creando producto {}", dto.getNombre());
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Producto producto = ProductoMapper.toEntity(dto, categoria);
        if (producto.getEstado() == null) {
            producto.setEstado(Estado.ACTIVO);
        }
        Producto guardado = productoRepository.save(producto);
        return ProductoMapper.toDto(guardado);
    }

    @Override
    public ProductoDto actualizar(Integer id, ProductoDto dto) {
        log.info("Actualizando producto {}", id);
        return productoRepository.findById(id)
                .map(existing -> {
                    Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                            .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
                    existing.setNombre(dto.getNombre());
                    existing.setDescripcion(dto.getDescripcion());
                    existing.setPrecio(dto.getPrecio());
                    existing.setStock(dto.getStock());
                    existing.setCategoria(categoria);
                    existing.setImagen(dto.getImagen());
                    existing.setEstado(dto.getEstado());
                    return ProductoMapper.toDto(productoRepository.save(existing));
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        log.info("Eliminando producto {}", id);
        productoRepository.deleteById(id);
    }
}
