package com.duoc.backend.model;

import com.duoc.backend.Producto.Producto;
import org.springframework.hateoas.RepresentationModel;

public class ProductoModel extends RepresentationModel<ProductoModel> {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String unidadMedida;
    private Integer stock;
    private Double precioUnitario;

    public ProductoModel(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.tipo = producto.getTipo();
        this.unidadMedida = producto.getUnidadMedida();
        this.stock = producto.getStock();
        this.precioUnitario = producto.getPrecioUnitario();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }
}
