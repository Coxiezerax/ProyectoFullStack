package com.duoc.backend.controller;

import com.duoc.backend.Producto.Producto;
import com.duoc.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listar();
    }

    @Operation(summary = "Obtener un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    @PostMapping("/crear")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @Operation(summary = "Actualizar un producto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto nuevo) {
        return productoService.buscarPorId(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(nuevo.getNombre());
                    productoExistente.setDescripcion(nuevo.getDescripcion());
                    productoExistente.setTipo(nuevo.getTipo());
                    productoExistente.setUnidadMedida(nuevo.getUnidadMedida());
                    productoExistente.setStock(nuevo.getStock());
                    productoExistente.setPrecioUnitario(nuevo.getPrecioUnitario());
                    productoService.guardar(productoExistente);
                    return ResponseEntity.ok(productoExistente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        if (productoService.buscarPorId(id).isPresent()) {
            productoService.eliminar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Listar productos por tipo")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrados por tipo")
    @GetMapping("/tipo/{tipo}")
    public List<Producto> listarPorTipo(@PathVariable String tipo) {
        return productoService.buscarPorTipo(tipo);
    }
}
