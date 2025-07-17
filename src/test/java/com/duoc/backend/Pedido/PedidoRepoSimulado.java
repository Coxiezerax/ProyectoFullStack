package com.duoc.backend.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepoSimulado {
    List<Pedido> findAll();

    Optional<Pedido> findById(Long id);

    Pedido save(Pedido pedido);

    void deleteById(Long id);
}
