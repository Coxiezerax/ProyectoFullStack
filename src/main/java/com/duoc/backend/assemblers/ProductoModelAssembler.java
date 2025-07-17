package com.duoc.backend.assemblers;

import com.duoc.backend.Producto.Producto;
import com.duoc.backend.controller.ProductoControllerV2;
import com.duoc.backend.model.ProductoModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, ProductoModel> {

        @Override
        @NonNull
        public ProductoModel toModel(@NonNull Producto producto) {
                ProductoModel model = new ProductoModel(producto);

                model.add(linkTo(methodOn(ProductoControllerV2.class)
                                .obtenerProducto(producto.getId())).withSelfRel());

                model.add(linkTo(methodOn(ProductoControllerV2.class)
                                .listarProductosHateoas()).withRel("todos"));

                model.add(linkTo(methodOn(ProductoControllerV2.class)
                                .actualizarProducto(producto.getId(), null)).withRel("editar"));

                model.add(linkTo(methodOn(ProductoControllerV2.class)
                                .eliminarProducto(producto.getId())).withRel("eliminar"));

                return model;
        }
}
