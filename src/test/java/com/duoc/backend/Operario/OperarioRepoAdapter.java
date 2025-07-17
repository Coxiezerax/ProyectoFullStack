
package com.duoc.backend.Operario;

import java.util.List;
import java.util.Optional;

public interface OperarioRepoAdapter {
    List<Operario> findAll();

    Optional<Operario> findById(Long id);

    Operario save(Operario operario);

    void deleteById(Long id);

    boolean existsById(Long id);
}
