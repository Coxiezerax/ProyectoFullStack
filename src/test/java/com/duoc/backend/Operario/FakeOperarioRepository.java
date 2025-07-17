package com.duoc.backend.Operario;

import com.duoc.backend.repository.OperarioRepoSimulado; // ✅ ESTE ES EL IMPORT QUE FALTABA

import java.util.*;

public class FakeOperarioRepository implements OperarioRepoSimulado {

    private final Map<Long, Operario> almacen = new HashMap<>();
    private long secuencia = 1L;

    @Override
    public List<Operario> findAll() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public Optional<Operario> findById(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public Operario save(Operario operario) {
        if (operario.getId() == null) {
            operario.setId(secuencia++);
        }
        almacen.put(operario.getId(), operario);
        return operario;
    }

    @Override
    public void deleteById(Long id) {
        almacen.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return almacen.containsKey(id);
    }
}
