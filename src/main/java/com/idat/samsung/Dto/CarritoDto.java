package com.idat.samsung.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDto {
    private Integer idCarrito;
    private Integer idUsuario;
    private LocalDateTime fechaCreacion;
    private List<CarritoDetalleDto> detalles;
}
