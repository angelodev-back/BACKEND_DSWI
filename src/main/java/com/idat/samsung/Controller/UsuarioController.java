package com.idat.samsung.Controller;

import com.idat.samsung.Dto.UsuarioDto;
import com.idat.samsung.Services.UsuarioService;
import java.util.List;
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
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    /**
     * NUEVO ENDPOINT: Login con validación de contraseña
     * POST /api/usuarios/login
     */
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        log.info("Intento de login para usuario");
        String email = credenciales.get("email");
        String contraseña = credenciales.get("contraseña");
        
        if (email == null || contraseña == null) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Email y contraseña son requeridos")
            );
        }
        
        try {
            UsuarioDto usuario = usuarioService.login(email, contraseña);
            
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(401).body(
                    Map.of("error", "Credenciales inválidas")
                );
            }
        } catch (Exception e) {
            log.warn("Error al autenticar usuario {}: {}", email, e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", "Error en el servidor"));
        }
    }
    
    
    @GetMapping("/users")
    public ResponseEntity<List<UsuarioDto>> listarTodos() {
        log.info("GET /api/v1/listar/usuarios");
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    
    @GetMapping("/users/active")
    public ResponseEntity<List<UsuarioDto>> listarActivos() {
        log.info("GET /api/v1/listar/usuarios/activos");
        return ResponseEntity.ok(usuarioService.listarActivos());
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Integer id) {
        log.info("GET /api/v1/usuario/{}", id);
        UsuarioDto usuario = usuarioService.buscarPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/users/email/{email}")
    public ResponseEntity<UsuarioDto> buscarPorEmail(@PathVariable String email) {
        log.info("GET /api/v1/usuario/email/{}", email);
        UsuarioDto usuario = usuarioService.buscarPorEmail(email);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/users/register")
    public ResponseEntity<?> registrar(@RequestBody Map<String, Object> datos) {
        log.info("POST /api/v1/usuarios/registrar");
        try {
            UsuarioDto dto = new UsuarioDto();
            dto.setNombre((String) datos.get("nombre"));
            dto.setApellido((String) datos.get("apellido"));
            dto.setEmail((String) datos.get("email"));
            dto.setTelefono((String) datos.get("telefono"));
            dto.setDireccion((String) datos.get("direccion"));
            
            String contraseña = (String) datos.get("contraseña");
            
            UsuarioDto registrado = usuarioService.registrar(dto, contraseña);
            return ResponseEntity.status(201).body(registrado);
        } catch (RuntimeException e) {
            log.warn("Error de validación al registrar usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                Map.of("error", e.getMessage())
            );
        }
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<UsuarioDto> actualizar(@PathVariable Integer id, @RequestBody UsuarioDto dto) {
        log.info("PUT /api/v1/usuario/{}", id);
        UsuarioDto actualizado = usuarioService.actualizar(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("DELETE /api/v1/usuario/{}", id);
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}