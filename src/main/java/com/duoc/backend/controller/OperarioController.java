package com.duoc.backend.controller;

import com.duoc.backend.Operario.Operario;
import com.duoc.backend.service.OperarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operarios")
public class OperarioController {

    @Autowired
    private OperarioService operarioService;

    @Operation(summary = "Listar todos los operarios")
    @ApiResponse(responseCode = "200", description = "Listado exitoso")
    @GetMapping
    public List<Operario> listarOperarios() {
        return operarioService.listar();
    }

    @Operation(summary = "Obtener operario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operario encontrado"),
            @ApiResponse(responseCode = "404", description = "Operario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Operario> obtenerOperario(@PathVariable Long id) {
        return operarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo operario")
    @ApiResponse(responseCode = "201", description = "Operario creado exitosamente")
    @PostMapping("/crear")
    public Operario crearOperario(@RequestBody Operario operario) {
        return operarioService.guardar(operario);
    }

    @Operation(summary = "Actualizar un operario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Operario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Operario> actualizarOperario(
            @PathVariable Long id,
            @RequestBody Operario nuevoOperario) {
        return operarioService.buscarPorId(id)
                .map(operarioExistente -> {
                    operarioExistente.setNombre(nuevoOperario.getNombre());
                    operarioExistente.setApellido(nuevoOperario.getApellido());
                    operarioExistente.setEdad(nuevoOperario.getEdad());
                    operarioExistente.setCorreo(nuevoOperario.getCorreo());
                    operarioExistente.setTelefono(nuevoOperario.getTelefono());
                    operarioExistente.setUbicacion(nuevoOperario.getUbicacion());
                    operarioService.guardar(operarioExistente);
                    return ResponseEntity.ok(operarioExistente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un operario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Operario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOperario(@PathVariable Long id) {
        operarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
