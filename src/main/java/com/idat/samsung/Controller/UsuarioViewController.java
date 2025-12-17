package com.idat.samsung.Controller;

import com.idat.samsung.Services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("users", usuarioService.listarTodos());
        return "usuarios";
    }

    @GetMapping("/usuarios/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        model.addAttribute("user", usuarioService.buscarPorId(id));
        return "usuario-detalle";
    }
}
