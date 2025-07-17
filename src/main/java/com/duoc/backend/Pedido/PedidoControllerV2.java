package com.duoc.backend.Pedido;

import com.duoc.backend.assemblers.PedidoModelAssembler;
import com.duoc.backend.model.PedidoModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/pedido/v2")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listar();
    }

    @GetMapping("/hateoas")
    public CollectionModel<PedidoModel> listarPedidosHateoas() {
        List<Pedido> pedidos = pedidoService.listar();
        List<PedidoModel> modelos = pedidos.stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(modelos,
                linkTo(methodOn(PedidoControllerV2.class).listarPedidosHateoas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> obtenerPedido(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(pedido -> ResponseEntity.ok(assembler.toModel(pedido)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<PedidoModel> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevo = pedidoService.crearPedido(pedido); // ✅ calcula montoTotal, valida stock
        return ResponseEntity.ok(assembler.toModel(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> actualizarPedido(@PathVariable Long id, @RequestBody Pedido nuevoPedido) {
        return pedidoService.buscarPorId(id)
                .map(pedidoExistente -> {
                    pedidoExistente.setDescripcion(nuevoPedido.getDescripcion());
                    pedidoExistente.setMontoTotal(nuevoPedido.getMontoTotal());
                    pedidoExistente.setFecha(nuevoPedido.getFecha());
                    pedidoExistente.setMetodoPago(nuevoPedido.getMetodoPago());
                    Pedido actualizado = pedidoService.guardar(pedidoExistente);
                    return ResponseEntity.ok(assembler.toModel(actualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        if (pedidoService.buscarPorId(id).isPresent()) {
            pedidoService.eliminar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
