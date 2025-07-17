package com.duoc.backend.Pedido;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Listar todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Pedidos listados correctamente")
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listar();
    }

    @Operation(summary = "Obtener un pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo pedido con lógica de productos, combos y stock")
    @ApiResponse(responseCode = "201", description = "Pedido creado correctamente")
    @PostMapping("/crear")
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.crearPedido(pedido);
    }

    @Operation(summary = "Actualizar un pedido existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido nuevoPedido) {
        return pedidoService.buscarPorId(id)
                .map(pedidoExistente -> {
                    pedidoExistente.setDescripcion(nuevoPedido.getDescripcion());
                    pedidoExistente.setFecha(nuevoPedido.getFecha());
                    pedidoExistente.setMetodoPago(nuevoPedido.getMetodoPago());
                    pedidoExistente.setOperario(nuevoPedido.getOperario());
                    pedidoExistente.setProductos(nuevoPedido.getProductos());
                    pedidoExistente.setCombos(nuevoPedido.getCombos());

                    Pedido actualizado = pedidoService.crearPedido(pedidoExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        if (pedidoService.buscarPorId(id).isPresent()) {
            pedidoService.eliminar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
