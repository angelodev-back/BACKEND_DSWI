package com.idat.samsung.Dto;

import com.idat.samsung.Enum.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {
    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private Estado estado;
}