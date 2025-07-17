package com.duoc.backend.model;

import com.duoc.backend.Combo.Combo;
import org.springframework.hateoas.RepresentationModel;

public class ComboModel extends RepresentationModel<ComboModel> {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;

    public ComboModel(Combo combo) {
        this.id = combo.getId();
        this.nombre = combo.getNombre();
        this.descripcion = combo.getDescripcion();
        this.precio = combo.getPrecio();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }
}
