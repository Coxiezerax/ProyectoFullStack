package com.duoc.backend.service;

import com.duoc.backend.Producto.Producto;
import com.duoc.backend.Producto.ProductoRepoSimulado;
import com.duoc.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private ProductoRepoSimulado productoRepoSimulado;
    private ProductoRepository productoRepository;
    private boolean usarRepositorioSimulado = false;

    public void setProductoRepo(ProductoRepoSimulado repo) {
        this.productoRepoSimulado = repo;
        this.usarRepositorioSimulado = true;
    }

    @Autowired
    public void setProductoRepository(ProductoRepository repo) {
        this.productoRepository = repo;
    }

    public List<Producto> listar() {
        return usarRepositorioSimulado
                ? productoRepoSimulado.findAll()
                : productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return usarRepositorioSimulado
                ? productoRepoSimulado.findById(id)
                : productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return usarRepositorioSimulado
                ? productoRepoSimulado.save(producto)
                : productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        if (usarRepositorioSimulado) {
            productoRepoSimulado.deleteById(id);
        } else {
            productoRepository.deleteById(id);
        }
    }

    public List<Producto> buscarPorTipo(String tipo) {
        return usarRepositorioSimulado
                ? productoRepoSimulado.findByTipo(tipo)
                : productoRepository.findByTipo(tipo);
    }
}
