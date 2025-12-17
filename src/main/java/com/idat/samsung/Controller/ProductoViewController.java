package com.idat.samsung.Controller;

import com.idat.samsung.Dto.ProductoDto;
import com.idat.samsung.Services.CategoriaService;
import com.idat.samsung.Services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductoViewController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping("/productos")
    public String listar(Model model) {
        model.addAttribute("products", productoService.listarTodos());
        return "productos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("product", new ProductoDto());
        model.addAttribute("categories", categoriaService.listarActivas());
        return "producto-form";
    }

    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productoService.buscarPorId(id));
        model.addAttribute("categories", categoriaService.listarActivas());
        return "producto-form";
    }

    @PostMapping("/productos/guardar")
    public String guardar(@ModelAttribute("product") ProductoDto dto) {
        productoService.guardar(dto);
        return "redirect:/productos";
    }

    @PostMapping("/productos/actualizar/{id}")
    public String actualizar(@PathVariable Integer id, @ModelAttribute("product") ProductoDto dto) {
        productoService.actualizar(id, dto);
        return "redirect:/productos";
    }

    @PostMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
}
