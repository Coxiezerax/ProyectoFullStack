package com.duoc.backend.assemblers;

import org.springframework.lang.NonNull;

import com.duoc.backend.Operario.Operario;
import com.duoc.backend.controller.OperarioControllerV2;
import com.duoc.backend.model.OperarioModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OperarioModelAssembler implements RepresentationModelAssembler<Operario, OperarioModel> {

        @Override
        @NonNull
        public OperarioModel toModel(@NonNull Operario operario) {
                OperarioModel model = new OperarioModel(operario);

                model.add(linkTo(methodOn(OperarioControllerV2.class)
                                .obtenerOperario(operario.getId())).withSelfRel());

                model.add(linkTo(methodOn(OperarioControllerV2.class)
                                .listarOperariosHateoas()).withRel("todos"));

                model.add(linkTo(methodOn(OperarioControllerV2.class)
                                .actualizarOperario(operario.getId(), null)).withRel("editar"));

                model.add(linkTo(methodOn(OperarioControllerV2.class)
                                .eliminarOperario(operario.getId())).withRel("eliminar"));

                return model;
        }
}
