package com.duoc.backend.controller;

import com.duoc.backend.Producto.Producto;
import com.duoc.backend.assemblers.ProductoModelAssembler;
import com.duoc.backend.model.ProductoModel;
import com.duoc.backend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/producto/v2")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoModelAssembler;

    // GET /producto/v2
    @GetMapping
    public CollectionModel<ProductoModel> listarProductosHateoas() {
        List<Producto> productos = productoService.listar();

        List<ProductoModel> modelos = productos.stream()
                .map(productoModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(modelos,
                linkTo(methodOn(ProductoControllerV2.class).listarProductosHateoas()).withSelfRel());
    }

    // GET /producto/v2/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> obtenerProducto(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(producto -> ResponseEntity.ok(productoModelAssembler.toModel(producto)))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /producto/v2/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> actualizarProducto(@PathVariable Long id, @RequestBody Producto nuevo) {
        return productoService.buscarPorId(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(nuevo.getNombre());
                    productoExistente.setDescripcion(nuevo.getDescripcion());
                    productoExistente.setTipo(nuevo.getTipo());
                    productoExistente.setUnidadMedida(nuevo.getUnidadMedida());
                    productoExistente.setStock(nuevo.getStock());
                    productoExistente.setPrecioUnitario(nuevo.getPrecioUnitario());
                    productoService.guardar(productoExistente);
                    return ResponseEntity.ok(productoModelAssembler.toModel(productoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /producto/v2/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        return productoService.buscarPorId(id).map(p -> {
            productoService.eliminar(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
