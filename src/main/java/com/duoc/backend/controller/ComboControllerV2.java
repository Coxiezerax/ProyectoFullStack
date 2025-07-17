package com.duoc.backend.controller;

import com.duoc.backend.Combo.Combo;
import com.duoc.backend.Combo.ComboService;
import com.duoc.backend.assemblers.ComboModelAssembler;
import com.duoc.backend.model.ComboModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/combo/v2")
public class ComboControllerV2 {

    @Autowired
    private ComboService comboService;

    @Autowired
    private ComboModelAssembler comboModelAssembler;

    @GetMapping
    public CollectionModel<ComboModel> listarCombosHateoas() {
        List<ComboModel> modelos = comboService.listar().stream()
                .map(comboModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(modelos,
                linkTo(methodOn(ComboControllerV2.class).listarCombosHateoas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComboModel> obtenerCombo(@PathVariable Long id) {
        return comboService.buscarPorId(id)
                .map(combo -> ResponseEntity.ok(comboModelAssembler.toModel(combo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComboModel> actualizarCombo(@PathVariable Long id, @RequestBody Combo nuevo) {
        return comboService.buscarPorId(id)
                .map(comboExistente -> {
                    comboExistente.setNombre(nuevo.getNombre());
                    comboExistente.setDescripcion(nuevo.getDescripcion());
                    comboExistente.setPrecio(nuevo.getPrecio());
                    comboExistente.setProductos(nuevo.getProductos());
                    comboService.guardar(comboExistente);
                    return ResponseEntity.ok(comboModelAssembler.toModel(comboExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCombo(@PathVariable Long id) {
        if (comboService.buscarPorId(id).isPresent()) {
            comboService.eliminar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
