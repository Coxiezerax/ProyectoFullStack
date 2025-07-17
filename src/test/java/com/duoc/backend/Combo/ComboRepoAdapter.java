
package com.duoc.backend.Combo;

import java.util.List;
import java.util.Optional;

public class ComboRepoAdapter implements ComboRepoSimulado {

    private final ComboRepository comboRepository;

    public ComboRepoAdapter(ComboRepository comboRepository) {
        this.comboRepository = comboRepository;
    }

    @Override
    public List<Combo> findAll() {
        return comboRepository.findAll();
    }

    @Override
    public Optional<Combo> findById(Long id) {
        return comboRepository.findById(id);
    }

    @Override
    public Combo save(Combo combo) {
        return comboRepository.save(combo);
    }

    @Override
    public void deleteById(Long id) {
        comboRepository.deleteById(id);
    }
}
