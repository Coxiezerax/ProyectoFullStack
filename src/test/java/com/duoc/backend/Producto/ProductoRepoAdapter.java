package com.duoc.backend.Producto;

import com.duoc.backend.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

public class ProductoRepoAdapter implements ProductoRepoSimulado {

    private final ProductoRepository repo;

    public ProductoRepoAdapter(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Producto> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Producto> findByTipo(String tipo) {
        return repo.findByTipo(tipo);
    }
}
