package com.idat.samsung.Controller;

import com.idat.samsung.Dto.CategoriaDto;
import com.idat.samsung.Services.CategoriaService;
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
public class CategoriaController {
    
    private final CategoriaService categoriaService;
    
    @GetMapping("/categories")
    public ResponseEntity<List<CategoriaDto>> listarTodas() {
        log.info("Listando todas las categorías");
        return ResponseEntity.ok(categoriaService.listarTodas());
    }
    
    @GetMapping("/categories/active")
    public ResponseEntity<List<CategoriaDto>> listarActivas() {
        log.info("Listando categorías activas");
        return ResponseEntity.ok(categoriaService.listarActivas());
    }
    
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoriaDto> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando categoría ID: {}", id);
        CategoriaDto categoria = categoriaService.buscarPorId(id);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/categories")
    public ResponseEntity<CategoriaDto> crear(@RequestBody CategoriaDto dto) {
        log.info("Creando nueva categoría: {}", dto.getNombre());
        try {
            CategoriaDto creada = categoriaService.guardar(dto);
            return ResponseEntity.status(201).body(creada);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear categoría: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoriaDto> actualizar(@PathVariable Integer id, @RequestBody CategoriaDto dto) {
        log.info("Actualizando categoría ID: {}", id);
        try {
            CategoriaDto actualizada = categoriaService.actualizar(id, dto);
            return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.warn("Error al actualizar categoría {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("DELETE /api/v1/categoria/{}", id);
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}