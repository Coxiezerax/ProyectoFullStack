package com.duoc.backend.assemblers;

import com.duoc.backend.Pedido.Pedido;
import com.duoc.backend.model.PedidoModel;
import com.duoc.backend.Pedido.PedidoControllerV2;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, PedidoModel> {

        @Override
        @NonNull
        public PedidoModel toModel(@NonNull Pedido pedido) {
                PedidoModel model = new PedidoModel(pedido);

                model.add(linkTo(methodOn(PedidoControllerV2.class)
                                .obtenerPedido(pedido.getId())).withSelfRel());

                model.add(linkTo(methodOn(PedidoControllerV2.class)
                                .listarPedidosHateoas()).withRel("todos"));

                model.add(linkTo(methodOn(PedidoControllerV2.class)
                                .actualizarPedido(pedido.getId(), null)).withRel("editar"));

                model.add(linkTo(methodOn(PedidoControllerV2.class)
                                .eliminarPedido(pedido.getId())).withRel("eliminar"));

                return model;
        }
}
