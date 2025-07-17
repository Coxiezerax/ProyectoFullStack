
package com.duoc.backend.Combo;

import java.util.List;
import java.util.Optional;

public interface ComboRepoSimulado {
    List<Combo> findAll();

    Optional<Combo> findById(Long id);

    Combo save(Combo combo);

    void deleteById(Long id);
}
