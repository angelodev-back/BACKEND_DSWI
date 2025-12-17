package com.idat.samsung.Dto;

import com.idat.samsung.Enum.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer idCategoria;
    private String nombreCategoria;
    private String imagen;
    private Estado estado;
    private LocalDateTime fechaRegistro;
}