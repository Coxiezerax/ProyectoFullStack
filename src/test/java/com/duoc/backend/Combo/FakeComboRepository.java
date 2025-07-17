package com.duoc.backend.Combo;

import java.util.*;

public class FakeComboRepository implements ComboRepoSimulado {

    private final Map<Long, Combo> combos = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public List<Combo> findAll() {
        return new ArrayList<>(combos.values());
    }

    @Override
    public Optional<Combo> findById(Long id) {
        return Optional.ofNullable(combos.get(id));
    }

    @Override
    public Combo save(Combo combo) {
        if (combo.getId() == null) {
            combo.setId(nextId++);
        }
        combos.put(combo.getId(), combo);
        return combo;
    }

    @Override
    public void deleteById(Long id) {
        combos.remove(id);
    }
}
