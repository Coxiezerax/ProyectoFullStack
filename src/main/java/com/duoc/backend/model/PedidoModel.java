package com.duoc.backend.model;

import com.duoc.backend.Pedido.Pedido;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class PedidoModel extends RepresentationModel<PedidoModel> {

    private Long id;
    private String descripcion;
    private Double montoTotal;
    private String metodoPago;
    private LocalDateTime fecha;

    public PedidoModel(Pedido pedido) {
        this.id = pedido.getId();
        this.descripcion = pedido.getDescripcion();
        this.montoTotal = pedido.getMontoTotal();
        this.metodoPago = pedido.getMetodoPago();
        this.fecha = pedido.getFecha();
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
