package com.duoc.backend.assemblers;

import com.duoc.backend.Combo.Combo;
import com.duoc.backend.controller.ComboControllerV2;
import com.duoc.backend.model.ComboModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ComboModelAssembler implements RepresentationModelAssembler<Combo, ComboModel> {

        @Override
        public @NonNull ComboModel toModel(@NonNull Combo combo) {
                ComboModel model = new ComboModel(combo);

                model.add(linkTo(methodOn(ComboControllerV2.class)
                                .obtenerCombo(combo.getId())).withSelfRel());

                model.add(linkTo(methodOn(ComboControllerV2.class)
                                .listarCombosHateoas()).withRel("todos"));

                model.add(linkTo(methodOn(ComboControllerV2.class)
                                .actualizarCombo(combo.getId(), null)).withRel("editar"));

                model.add(linkTo(methodOn(ComboControllerV2.class)
                                .eliminarCombo(combo.getId())).withRel("eliminar"));

                return model;
        }
}
