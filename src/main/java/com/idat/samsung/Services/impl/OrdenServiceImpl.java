package com.idat.samsung.Services.impl;

import com.idat.samsung.Dto.OrdenDetalleDto;
import com.idat.samsung.Dto.OrdenDto;
import com.idat.samsung.Entity.Orden;
import com.idat.samsung.Entity.OrdenDetalle;
import com.idat.samsung.Entity.Producto;
import com.idat.samsung.Entity.Usuario;
import com.idat.samsung.Enum.EstadoOrden;
import com.idat.samsung.Repository.OrdenRepository;
import com.idat.samsung.Repository.ProductoRepository;
import com.idat.samsung.Repository.UsuarioRepository;
import com.idat.samsung.Services.OrdenService;
import com.idat.samsung.mapper.OrdenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<OrdenDto> listarTodas() {
        log.info("Listando órdenes");
        return ordenRepository.findAll().stream()
                .map(OrdenMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenDto> listarPorUsuario(Integer idUsuario) {
        log.info("Listando órdenes por usuario {}", idUsuario);
        return ordenRepository.findByUsuarioIdUsuario(idUsuario).stream()
                .map(OrdenMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrdenDto buscarPorId(Integer id) {
        log.info("Buscando orden {}", id);
        return ordenRepository.findById(id)
                .map(OrdenMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public OrdenDto crearOrden(Integer idUsuario, List<OrdenDetalleDto> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La orden debe incluir detalles");
        }

        log.info("Creando orden para usuario {} con {} detalles", idUsuario, detalles.size());
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setEstado(EstadoOrden.PENDIENTE);
        orden.setDetalles(new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;

        for (OrdenDetalleDto detalleDto : detalles) {
            Producto producto = productoRepository.findById(detalleDto.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            OrdenDetalle detalle = new OrdenDetalle();
            detalle.setOrden(orden);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDto.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalleDto.getCantidad())));

            orden.getDetalles().add(detalle);
            total = total.add(detalle.getSubtotal());
        }

        orden.setTotal(total);
        Orden guardada = ordenRepository.save(orden);
        return OrdenMapper.toDto(guardada);
    }

    @Override
    public OrdenDto actualizarEstado(Integer id, EstadoOrden nuevoEstado) {
        log.info("Actualizando estado de orden {} a {}", id, nuevoEstado);
        return ordenRepository.findById(id)
                .map(orden -> {
                    orden.setEstado(nuevoEstado);
                    return OrdenMapper.toDto(ordenRepository.save(orden));
                })
                .orElse(null);
    }
}
