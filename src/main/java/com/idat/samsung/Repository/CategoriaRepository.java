package com.idat.samsung.Repository;

import com.idat.samsung.Entity.Categoria;
import com.idat.samsung.Enum.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByEstado(Estado estado);
    List<Categoria> findByNombreContaining(String nombre);
}