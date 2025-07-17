package com.duoc.backend.Combo;

import com.duoc.backend.Producto.Producto;
import com.duoc.backend.Producto.ProductoRepoSimulado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComboService {

    private ComboRepoSimulado comboRepo; // Simulación para test
    private ProductoRepoSimulado productoRepository;

    @Autowired
    private ComboRepository comboRepository;

    public List<Combo> listar() {
        return (comboRepo != null) ? comboRepo.findAll() : comboRepository.findAll();
    }

    public Optional<Combo> buscarPorId(Long id) {
        return (comboRepo != null) ? comboRepo.findById(id) : comboRepository.findById(id);
    }

    public Combo guardar(Combo combo) {
        List<Producto> productosCompletos = combo.getProductos().stream()
                .map(producto -> productoRepository.findById(producto.getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + producto.getId())))
                .toList();
        combo.setProductos(productosCompletos);

        return (comboRepo != null) ? comboRepo.save(combo) : comboRepository.save(combo);
    }

    public void eliminar(Long id) {
        if (comboRepo != null) {
            comboRepo.deleteById(id);
        } else {
            comboRepository.deleteById(id);
        }
    }

    // Setters para pruebas unitarias
    public void setComboRepo(ComboRepoSimulado repo) {
        this.comboRepo = repo;
    }

    public void setProductoRepository(ProductoRepoSimulado repo) {
        this.productoRepository = repo;
    }
}
