package com.idat.samsung.Repository;

import com.idat.samsung.Entity.Usuario;
import com.idat.samsung.Enum.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByEstado(Estado estado);
    boolean existsByEmail(String email);
}