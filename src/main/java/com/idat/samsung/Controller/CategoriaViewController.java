package com.idat.samsung.Controller;

import com.idat.samsung.Dto.CategoriaDto;
import com.idat.samsung.Services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CategoriaViewController {

    private final CategoriaService categoriaService;

    @GetMapping("/categorias")
    public String listar(Model model) {
        model.addAttribute("categories", categoriaService.listarTodas());
        return "categorias";
    }

    @GetMapping("/categorias/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("category", new CategoriaDto());
        return "categoria-form";
    }

    @GetMapping("/categorias/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoriaService.buscarPorId(id));
        return "categoria-form";
    }

    @PostMapping("/categorias/guardar")
    public String guardar(@ModelAttribute("category") CategoriaDto dto) {
        categoriaService.guardar(dto);
        return "redirect:/categorias";
    }

    @PostMapping("/categorias/actualizar/{id}")
    public String actualizar(@PathVariable Integer id, @ModelAttribute("category") CategoriaDto dto) {
        categoriaService.actualizar(id, dto);
        return "redirect:/categorias";
    }

    @PostMapping("/categorias/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return "redirect:/categorias";
    }
}
