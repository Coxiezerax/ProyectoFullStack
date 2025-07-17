package com.duoc.backend.repository;

import com.duoc.backend.Producto.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByTipo(String tipo);
}
