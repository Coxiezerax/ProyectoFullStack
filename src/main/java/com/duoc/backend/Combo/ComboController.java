package com.duoc.backend.Combo;

import com.duoc.backend.Producto.Producto;
import com.duoc.backend.repository.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combo")
public class ComboController {

    @Autowired
    private ComboService comboService;

    @Autowired
    private ProductoRepository productoRepository;

    @Operation(summary = "Listar todos los combos disponibles")
    @ApiResponse(responseCode = "200", description = "Listado de combos exitoso")
    @GetMapping
    public List<Combo> listar() {
        return comboService.listar();
    }

    @Operation(summary = "Crear un nuevo combo")
    @ApiResponse(responseCode = "201", description = "Combo creado exitosamente")
    @PostMapping("/crear")
    public Combo crear(@RequestBody Combo combo) {
        return comboService.guardar(combo);
    }

    @Operation(summary = "Obtener un combo por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combo encontrado"),
            @ApiResponse(responseCode = "404", description = "Combo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Combo> obtener(@PathVariable Long id) {
        return comboService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un combo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Combo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Combo> actualizar(@PathVariable Long id, @RequestBody Combo nuevoCombo) {
        return comboService.buscarPorId(id)
                .map(comboExistente -> {
                    comboExistente.setNombre(nuevoCombo.getNombre());
                    comboExistente.setDescripcion(nuevoCombo.getDescripcion());
                    comboExistente.setPrecio(nuevoCombo.getPrecio());

                    List<Producto> productosCompletos = nuevoCombo.getProductos().stream()
                            .map(p -> productoRepository.findById(p.getId())
                                    .orElseThrow(
                                            () -> new RuntimeException("Producto no encontrado con ID: " + p.getId())))
                            .toList();

                    comboExistente.setProductos(productosCompletos);

                    comboService.guardar(comboExistente);
                    return ResponseEntity.ok(comboExistente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un combo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Combo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Combo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (comboService.buscarPorId(id).isPresent()) {
            comboService.eliminar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
