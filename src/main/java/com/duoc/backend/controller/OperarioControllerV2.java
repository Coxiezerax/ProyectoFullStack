package com.duoc.backend.controller;

import com.duoc.backend.Operario.Operario;

import com.duoc.backend.model.OperarioModel;
import com.duoc.backend.assemblers.OperarioModelAssembler;
import com.duoc.backend.service.OperarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/operario/v2")
public class OperarioControllerV2 {

    @Autowired
    private OperarioService operarioService;

    @Autowired
    private OperarioModelAssembler operarioModelAssembler;

    @GetMapping
    public CollectionModel<OperarioModel> listarOperariosHateoas() {
        List<OperarioModel> modelos = operarioService.listar().stream()
                .map(operarioModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(modelos,
                linkTo(methodOn(OperarioControllerV2.class).listarOperariosHateoas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperarioModel> obtenerOperario(@PathVariable Long id) {
        return operarioService.buscarPorId(id)
                .map(operario -> ResponseEntity.ok(operarioModelAssembler.toModel(operario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperarioModel> actualizarOperario(@PathVariable Long id,
            @RequestBody Operario nuevo) {
        return operarioService.buscarPorId(id)
                .map(operarioExistente -> {
                    operarioExistente.setNombre(nuevo.getNombre());
                    operarioExistente.setApellido(nuevo.getApellido());
                    operarioExistente.setEdad(nuevo.getEdad());
                    operarioExistente.setCorreo(nuevo.getCorreo());
                    operarioExistente.setTelefono(nuevo.getTelefono());
                    operarioExistente.setUbicacion(nuevo.getUbicacion());
                    operarioService.guardar(operarioExistente);
                    return ResponseEntity.ok(operarioModelAssembler.toModel(operarioExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOperario(@PathVariable Long id) {
        if (operarioService.buscarPorId(id).isPresent()) {
            operarioService.eliminar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
