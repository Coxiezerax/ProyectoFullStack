package com.duoc.backend.Operario;

import com.duoc.backend.repository.OperarioRepoSimulado;

import com.duoc.backend.service.OperarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OperarioServiceTest {

    private OperarioService operarioService;
    private OperarioRepoSimulado fakeRepo;

    @BeforeEach
    public void setUp() {
        fakeRepo = new FakeOperarioRepository();
        operarioService = new OperarioService(fakeRepo);
    }

    @Test
    public void testGuardarOperario() {
        Operario op = new Operario();
        op.setNombre("Luis");
        op.setApellido("Gómez");

        Operario guardado = operarioService.guardar(op);

        assertNotNull(guardado.getId());
        assertEquals("Luis", guardado.getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Operario op = new Operario();
        op.setNombre("Ana");
        op.setApellido("Pérez");

        Operario guardado = operarioService.guardar(op);

        Optional<Operario> resultado = operarioService.buscarPorId(guardado.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    public void testEliminarOperario() {
        Operario op = new Operario();
        op.setNombre("Carlos");
        op.setApellido("Mendoza");

        Operario guardado = operarioService.guardar(op);
        Long id = guardado.getId();

        operarioService.eliminar(id);

        assertFalse(operarioService.buscarPorId(id).isPresent());
    }

    @Test
    public void testListarOperarios() {
        Operario op1 = new Operario();
        op1.setNombre("Juan");
        op1.setApellido("López");

        Operario op2 = new Operario();
        op2.setNombre("Sofía");
        op2.setApellido("Martínez");

        operarioService.guardar(op1);
        operarioService.guardar(op2);

        assertEquals(2, operarioService.listar().size());
    }
}
