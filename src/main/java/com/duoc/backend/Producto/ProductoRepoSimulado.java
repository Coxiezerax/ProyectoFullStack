package com.duoc.backend.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepoSimulado {
    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);

    List<Producto> findByTipo(String tipo);
}
