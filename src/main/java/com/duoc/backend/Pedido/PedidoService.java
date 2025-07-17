package com.duoc.backend.Pedido;

import com.duoc.backend.Combo.Combo;
import com.duoc.backend.Combo.ComboRepoSimulado;
import com.duoc.backend.Combo.ComboRepository;
import com.duoc.backend.Producto.Producto;
import com.duoc.backend.Producto.ProductoRepoSimulado;
import com.duoc.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired(required = false)
    private PedidoRepository pedidoRepository;

    private PedidoRepoSimulado pedidoRepoSimulado;

    @Autowired(required = false)
    private ProductoRepository productoRepository;

    private ProductoRepoSimulado productoRepoSimulado;

    @Autowired(required = false)
    private ComboRepository comboRepository;

    private ComboRepoSimulado comboRepoSimulado;

    public void setPedidoRepo(PedidoRepoSimulado repo) {
        this.pedidoRepoSimulado = repo;
    }

    public void setProductoRepository(ProductoRepoSimulado repo) {
        this.productoRepoSimulado = repo;
    }

    public void setComboRepository(ComboRepoSimulado repo) {
        this.comboRepoSimulado = repo;
    }

    private PedidoRepoSimulado getPedidoRepo() {
        if (pedidoRepoSimulado != null) {
            return pedidoRepoSimulado;
        }
        if (pedidoRepository != null) {
            return new PedidoRepoAdapter(pedidoRepository);
        }
        throw new IllegalStateException("No hay repositorio de pedidos disponible");
    }

    public List<Pedido> listar() {
        return getPedidoRepo().findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return getPedidoRepo().findById(id);
    }

    public Pedido guardar(Pedido pedido) {
        return getPedidoRepo().save(pedido);
    }

    public void eliminar(Long id) {
        getPedidoRepo().deleteById(id);
    }

    public Pedido crearPedido(Pedido pedido) {
        double total = 0.0;

        if (pedido.getProductos() != null) {
            for (Producto p : pedido.getProductos()) {
                Producto productoBD = (productoRepoSimulado != null
                        ? productoRepoSimulado.findById(p.getId())
                        : productoRepository.findById(p.getId()))
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Producto no encontrado: ID " + p.getId()));

                if (productoBD.getStock() <= 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Producto sin stock: " + productoBD.getNombre());
                }

                total += productoBD.getPrecioUnitario();
                productoBD.setStock(productoBD.getStock() - 1);

                if (productoRepoSimulado != null) {
                    productoRepoSimulado.save(productoBD);
                } else {
                    productoRepository.save(productoBD);
                }
            }
        }

        if (pedido.getCombos() != null) {
            for (Combo c : pedido.getCombos()) {
                Combo comboBD = (comboRepoSimulado != null
                        ? comboRepoSimulado.findById(c.getId())
                        : comboRepository.findById(c.getId()))
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Combo no encontrado: ID " + c.getId()));

                total += comboBD.getPrecio();

                for (Producto prod : comboBD.getProductos()) {
                    Producto productoCombo = (productoRepoSimulado != null
                            ? productoRepoSimulado.findById(prod.getId())
                            : productoRepository.findById(prod.getId()))
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Producto del combo no encontrado: ID " + prod.getId()));

                    if (productoCombo.getStock() <= 0) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Producto sin stock en combo: " + productoCombo.getNombre());
                    }

                    productoCombo.setStock(productoCombo.getStock() - 1);

                    if (productoRepoSimulado != null) {
                        productoRepoSimulado.save(productoCombo);
                    } else {
                        productoRepository.save(productoCombo);
                    }
                }
            }
        }

        pedido.setMontoTotal(total);
        return guardar(pedido);
    }
}
