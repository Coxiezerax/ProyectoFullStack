package com.duoc.backend.Producto;

import java.util.*;

public class FakeProductoRepository implements ProductoRepoSimulado {

    private final Map<Long, Producto> productos = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(productos.values());
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return Optional.ofNullable(productos.get(id));
    }

    @Override
    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(nextId++);
        }
        productos.put(producto.getId(), producto);
        return producto;
    }

    @Override
    public void deleteById(Long id) {
        productos.remove(id);
    }

    @Override
    public List<Producto> findByTipo(String tipo) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos.values()) {
            if (p.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

}
