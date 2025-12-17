package com.idat.samsung.Controller;

import com.idat.samsung.Services.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CarritoViewController {

    private final CarritoService carritoService;

    @GetMapping("/usuarios/{idUsuario}/carrito")
    public String verCarrito(@PathVariable Integer idUsuario, Model model) {
        model.addAttribute("cart", carritoService.obtenerPorUsuario(idUsuario));
        model.addAttribute("idUsuario", idUsuario);
        return "carrito";
    }

    @PostMapping("/usuarios/{idUsuario}/carrito/agregar")
    public String agregarItem(@PathVariable Integer idUsuario,
                              @RequestParam Integer idProducto,
                              @RequestParam Integer cantidad) {
        carritoService.agregarItem(idUsuario, idProducto, cantidad);
        return "redirect:/usuarios/" + idUsuario + "/carrito";
    }

    @PostMapping("/usuarios/{idUsuario}/carrito/eliminar/{idDetalle}")
    public String eliminarItem(@PathVariable Integer idUsuario, @PathVariable Integer idDetalle) {
        carritoService.eliminarItem(idUsuario, idDetalle);
        return "redirect:/usuarios/" + idUsuario + "/carrito";
    }

    @PostMapping("/usuarios/{idUsuario}/carrito/vaciar")
    public String vaciar(@PathVariable Integer idUsuario) {
        carritoService.vaciar(idUsuario);
        return "redirect:/usuarios/" + idUsuario + "/carrito";
    }
}
