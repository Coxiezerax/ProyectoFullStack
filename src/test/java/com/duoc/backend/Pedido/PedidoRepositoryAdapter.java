package com.duoc.backend.Pedido;

import java.util.List;
import java.util.Optional;

public class PedidoRepositoryAdapter implements PedidoRepoSimulado {

    private final FakePedidoRepository fake;

    public PedidoRepositoryAdapter(FakePedidoRepository fake) {
        this.fake = fake;
    }

    @Override
    public List<Pedido> findAll() {
        return fake.findAll();
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return fake.findById(id);
    }

    @Override
    public Pedido save(Pedido pedido) {
        return fake.save(pedido);
    }

    @Override
    public void deleteById(Long id) {
        fake.deleteById(id);
    }
}
