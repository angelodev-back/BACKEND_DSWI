package com.idat.samsung.Entity;

import com.idat.samsung.Enum.EstadoOrden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrden")
    private Integer idOrden;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @Column(name = "fechaOrden", nullable = false)
    private LocalDateTime fechaOrden = LocalDateTime.now();
    
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;
    
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<OrdenDetalle> detalles;
}