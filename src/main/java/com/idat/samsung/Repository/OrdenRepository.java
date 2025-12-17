package com.idat.samsung.Repository;

import com.idat.samsung.Entity.Orden;
import com.idat.samsung.Enum.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findByUsuarioIdUsuario(Integer idUsuario);
    List<Orden> findByEstado(EstadoOrden estado);
    List<Orden> findByUsuarioIdUsuarioAndEstado(Integer idUsuario, EstadoOrden estado);
}