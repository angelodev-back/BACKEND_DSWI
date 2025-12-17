package com.idat.samsung.Services.impl;

import com.idat.samsung.Dto.CarritoDto;
import com.idat.samsung.Entity.Carrito;
import com.idat.samsung.Entity.CarritoDetalle;
import com.idat.samsung.Entity.Producto;
import com.idat.samsung.Entity.Usuario;
import com.idat.samsung.Repository.CarritoRepository;
import com.idat.samsung.Repository.ProductoRepository;
import com.idat.samsung.Repository.UsuarioRepository;
import com.idat.samsung.Services.CarritoService;
import com.idat.samsung.mapper.CarritoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public CarritoDto obtenerPorUsuario(Integer idUsuario) {
        log.info("Obteniendo carrito para usuario {}", idUsuario);
        Carrito carrito = carritoRepository.findByUsuarioIdUsuario(idUsuario)
                .orElseGet(() -> crearCarritoParaUsuario(idUsuario));
        return CarritoMapper.toDto(carrito);
    }

    @Override
    @Transactional
    public CarritoDto agregarItem(Integer idUsuario, Integer idProducto, Integer cantidad) {
        log.info("Agregando producto {} (cant {}) al carrito de usuario {}", idProducto, cantidad, idUsuario);
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        Carrito carrito = carritoRepository.findByUsuarioIdUsuario(idUsuario)
                .orElseGet(() -> crearCarritoParaUsuario(idUsuario));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Si ya existe el producto en el carrito, solo incrementa cantidad
        Optional<CarritoDetalle> existente = carrito.getDetalles().stream()
                .filter(d -> d.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        if (existente.isPresent()) {
            CarritoDetalle detalle = existente.get();
            detalle.setCantidad(detalle.getCantidad() + cantidad);
        } else {
            CarritoDetalle detalle = new CarritoDetalle();
            detalle.setCarrito(carrito);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecio());
            carrito.getDetalles().add(detalle);
        }

        Carrito guardado = carritoRepository.save(carrito);
        return CarritoMapper.toDto(guardado);
    }

    @Override
    @Transactional
    public CarritoDto eliminarItem(Integer idUsuario, Integer idDetalle) {
        log.info("Eliminando item {} del carrito de usuario {}", idDetalle, idUsuario);
        Carrito carrito = carritoRepository.findByUsuarioIdUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        boolean removed = carrito.getDetalles().removeIf(det -> det.getIdDetalle().equals(idDetalle));
        if (!removed) {
            throw new IllegalArgumentException("Detalle no encontrado en el carrito");
        }

        Carrito guardado = carritoRepository.save(carrito);
        return CarritoMapper.toDto(guardado);
    }

    @Override
    @Transactional
    public CarritoDto vaciar(Integer idUsuario) {
        log.info("Vaciando carrito de usuario {}", idUsuario);
        Carrito carrito = carritoRepository.findByUsuarioIdUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        carrito.getDetalles().clear();
        Carrito guardado = carritoRepository.save(carrito);
        return CarritoMapper.toDto(guardado);
    }

    private Carrito crearCarritoParaUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carrito.setDetalles(new ArrayList<>());
        return carritoRepository.save(carrito);
    }
}
