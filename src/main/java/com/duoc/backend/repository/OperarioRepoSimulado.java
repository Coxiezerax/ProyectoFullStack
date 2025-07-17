package com.duoc.backend.repository;

import com.duoc.backend.Operario.Operario;
import java.util.List;
import java.util.Optional;

public interface OperarioRepoSimulado {
    List<Operario> findAll();

    Optional<Operario> findById(Long id);

    Operario save(Operario operario);

    void deleteById(Long id);

    boolean existsById(Long id);
}
