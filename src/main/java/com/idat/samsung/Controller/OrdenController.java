package com.idat.samsung.Controller;

import com.idat.samsung.Dto.OrdenDto;
import com.idat.samsung.Dto.OrdenDetalleDto;
import com.idat.samsung.Enum.EstadoOrden;
import com.idat.samsung.Services.OrdenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class OrdenController {
    
    private final OrdenService ordenService;
    
    @GetMapping("/orders")
    public ResponseEntity<List<OrdenDto>> listarTodas() {
        log.info("Listando todas las órdenes");
        return ResponseEntity.ok(ordenService.listarTodas());
    }
    
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrdenDto> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando orden ID: {}", id);
        OrdenDto orden = ordenService.buscarPorId(id);
        return orden != null ? ResponseEntity.ok(orden) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/orders/user/{idUsuario}")
    public ResponseEntity<List<OrdenDto>> listarPorUsuario(@PathVariable Integer idUsuario) {
        log.info("Listando órdenes del usuario ID: {}", idUsuario);
        return ResponseEntity.ok(ordenService.listarPorUsuario(idUsuario));
    }
    
    @PostMapping("/orders")
    public ResponseEntity<OrdenDto> crear(@RequestBody Map<String, Object> datos) {
        log.info("Creando nueva orden");
        try {
            Integer idUsuario = (Integer) datos.get("idUsuario");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> detallesMap = (List<Map<String, Object>>) datos.get("detalles");

            List<OrdenDetalleDto> detalles = detallesMap.stream()
                    .map(d -> {
                        OrdenDetalleDto dto = new OrdenDetalleDto();
                        dto.setIdProducto((Integer) d.get("idProducto"));
                        dto.setCantidad((Integer) d.get("cantidad"));
                        return dto;
                    })
                    .toList();

            OrdenDto creada = ordenService.crearOrden(idUsuario, detalles);
            return ResponseEntity.status(201).body(creada);
        } catch (IllegalArgumentException e) {
            log.warn("Error de validación al crear orden: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/orders/{id}/estado")
    public ResponseEntity<OrdenDto> actualizarEstado(@PathVariable Integer id, @RequestBody Map<String, String> datos) {
        log.info("PATCH /api/v1/orden/{}/estado", id);
        try {
            EstadoOrden nuevoEstado = EstadoOrden.valueOf(datos.get("estado"));
            OrdenDto actualizada = ordenService.actualizarEstado(id, nuevoEstado);
            return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.warn("Error de validación al actualizar estado de orden {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}