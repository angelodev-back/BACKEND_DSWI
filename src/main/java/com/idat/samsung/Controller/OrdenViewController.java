package com.idat.samsung.Controller;

import com.idat.samsung.Services.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OrdenViewController {

    private final OrdenService ordenService;

    @GetMapping("/ordenes")
    public String listar(Model model) {
        model.addAttribute("orders", ordenService.listarTodas());
        model.addAttribute("idUsuario", null);
        return "ordenes";
    }

    @GetMapping("/ordenes/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        model.addAttribute("order", ordenService.buscarPorId(id));
        return "orden-detalle";
    }

    @GetMapping("/usuarios/{idUsuario}/ordenes")
    public String listarPorUsuario(@PathVariable Integer idUsuario, Model model) {
        model.addAttribute("orders", ordenService.listarPorUsuario(idUsuario));
        model.addAttribute("idUsuario", idUsuario);
        return "ordenes";
    }
}
