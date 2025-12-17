package com.idat.samsung.Controller;

import com.idat.samsung.Dto.ProductoDto;
import com.idat.samsung.Services.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class ProductoController {
    
    private final ProductoService productoService;
    
    @GetMapping("/products")
    public ResponseEntity<List<ProductoDto>> listarTodos() {
        log.info("Listando todos los productos");
        return ResponseEntity.ok(productoService.listarTodos());
    }
    
    @GetMapping("/products/active")
    public ResponseEntity<List<ProductoDto>> listarActivos() {
        log.info("Listando productos activos");
        return ResponseEntity.ok(productoService.listarActivos());
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductoDto> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando producto ID: {}", id);
        ProductoDto producto = productoService.buscarPorId(id);
        return producto != null ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/products/category/{idCategoria}")
    public ResponseEntity<List<ProductoDto>> listarPorCategoria(@PathVariable Integer idCategoria) {
        log.info("Listando productos de la categoría ID: {}", idCategoria);
        return ResponseEntity.ok(productoService.listarPorCategoria(idCategoria));
    }
    
    @PostMapping("/products")
    public ResponseEntity<ProductoDto> crear(@RequestBody ProductoDto dto) {
        log.info("Creando nuevo producto: {}", dto.getNombre());
        try {
            ProductoDto creado = productoService.guardar(dto);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear producto: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductoDto> actualizar(@PathVariable Integer id, @RequestBody ProductoDto dto) {
        log.info("Actualizando producto ID: {}", id);
        try {
            ProductoDto actualizado = productoService.actualizar(id, dto);
            return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.warn("Error al actualizar producto {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando producto ID: {}", id);
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}