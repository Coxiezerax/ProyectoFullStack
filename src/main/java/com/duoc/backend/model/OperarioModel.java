package com.duoc.backend.model;

import com.duoc.backend.Operario.Operario;
import org.springframework.hateoas.RepresentationModel;

public class OperarioModel extends RepresentationModel<OperarioModel> {

    private Long id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String correo;
    private String telefono;
    private String ubicacion;

    public OperarioModel(Operario operario) {
        this.id = operario.getId();
        this.nombre = operario.getNombre();
        this.apellido = operario.getApellido();
        this.edad = operario.getEdad();
        this.correo = operario.getCorreo();
        this.telefono = operario.getTelefono();
        this.ubicacion = operario.getUbicacion();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}
