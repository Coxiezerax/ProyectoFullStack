package com.duoc.backend.Pedido;

import java.util.*;

public class FakePedidoRepository implements PedidoRepoSimulado {

    private final Map<Long, Pedido> pedidos = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public List<Pedido> findAll() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public Pedido save(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setId(nextId++);
        }
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public void deleteById(Long id) {
        pedidos.remove(id);
    }
}
