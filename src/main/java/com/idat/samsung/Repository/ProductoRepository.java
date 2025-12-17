package com.idat.samsung.Repository;

import com.idat.samsung.Entity.Producto;
import com.idat.samsung.Enum.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByEstado(Estado estado);
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByCategoriaIdCategoria(Integer idCategoria);
    List<Producto> findByEstadoAndCategoriaIdCategoria(Estado estado, Integer idCategoria);
}