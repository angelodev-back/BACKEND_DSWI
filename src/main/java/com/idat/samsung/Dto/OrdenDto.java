package com.idat.samsung.Dto;

import com.idat.samsung.Enum.EstadoOrden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDto {
    private Integer idOrden;
    private Integer idUsuario;
    private String nombreUsuario;
    private LocalDateTime fechaOrden;
    private BigDecimal total;
    private EstadoOrden estado;
    private List<OrdenDetalleDto> detalles;
}