package com.idat.samsung.Controller;

import com.idat.samsung.Dto.CarritoDto;
import com.idat.samsung.Services.CarritoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("/cart/user/{idUsuario}")
    public ResponseEntity<CarritoDto> obtener(@PathVariable Integer idUsuario) {
        log.info("Obteniendo carrito del usuario ID: {}", idUsuario);
        try {
            return ResponseEntity.ok(carritoService.obtenerPorUsuario(idUsuario));
        } catch (IllegalArgumentException e) {
            log.warn("Error al obtener carrito del usuario {}: {}", idUsuario, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cart/user/{idUsuario}/items")
    public ResponseEntity<CarritoDto> agregarItem(@PathVariable Integer idUsuario, @RequestBody Map<String, Object> body) {
        log.info("Agregando producto al carrito del usuario ID: {}", idUsuario);
        try {
            Integer idProducto = (Integer) body.get("idProducto");
            Integer cantidad = (Integer) body.get("cantidad");
            CarritoDto actualizado = carritoService.agregarItem(idUsuario, idProducto, cantidad);
            return ResponseEntity.status(201).body(actualizado);
        } catch (IllegalArgumentException e) {
            log.warn("Error al agregar producto al carrito del usuario {}: {}", idUsuario, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/cart/user/{idUsuario}/items/{idDetalle}")
    public ResponseEntity<CarritoDto> eliminarItem(@PathVariable Integer idUsuario, @PathVariable Integer idDetalle) {
        log.info("Eliminando item {} del carrito del usuario ID: {}", idDetalle, idUsuario);
        try {
            CarritoDto actualizado = carritoService.eliminarItem(idUsuario, idDetalle);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            log.warn("Error al eliminar item del carrito del usuario {}: {}", idUsuario, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/cart/user/{idUsuario}")
    public ResponseEntity<CarritoDto> vaciar(@PathVariable Integer idUsuario) {
        log.info("Vaciando carrito del usuario ID: {}", idUsuario);
        try {
            CarritoDto actualizado = carritoService.vaciar(idUsuario);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            log.warn("Error al vaciar carrito del usuario {}: {}", idUsuario, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
