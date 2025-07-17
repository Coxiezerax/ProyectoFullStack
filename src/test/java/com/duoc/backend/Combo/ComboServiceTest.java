package com.duoc.backend.Combo;

import com.duoc.backend.Producto.Producto;
import com.duoc.backend.Producto.FakeProductoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ComboServiceTest {

    private ComboService comboService;
    private FakeComboRepository fakeComboRepo;
    private FakeProductoRepository fakeProductoRepo;

    @BeforeEach
    public void setUp() {
        comboService = new ComboService();

        fakeComboRepo = new FakeComboRepository();
        fakeProductoRepo = new FakeProductoRepository();

        comboService.setComboRepo(fakeComboRepo);

        comboService.setProductoRepository(fakeProductoRepo);

        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Pan");
        p1.setStock(10);
        p1.setPrecioUnitario(500.0);
        fakeProductoRepo.save(p1);

        Producto p2 = new Producto();
        p2.setId(2L);
        p2.setNombre("Salchicha");
        p2.setStock(8);
        p2.setPrecioUnitario(800.0);
        fakeProductoRepo.save(p2);
    }

    @Test
    public void testGuardarCombo() {
        Combo combo = new Combo();
        combo.setNombre("Combo Básico");
        combo.setDescripcion("Pan y salchicha");
        combo.setPrecio(1200.0);

        Producto pan = new Producto();
        pan.setId(1L);
        Producto salchicha = new Producto();
        salchicha.setId(2L);

        combo.setProductos(Arrays.asList(pan, salchicha));

        Combo guardado = comboService.guardar(combo);

        assertNotNull(guardado);
        assertEquals(2, guardado.getProductos().size());
        assertEquals("Combo Básico", guardado.getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Combo combo = new Combo();
        combo.setId(1L);
        combo.setNombre("Combo de prueba");
        combo.setDescripcion("Desc");
        combo.setPrecio(1000.0);
        combo.setProductos(List.of());

        fakeComboRepo.save(combo);

        Optional<Combo> resultado = comboService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Combo de prueba", resultado.get().getNombre());
    }

    @Test
    public void testEliminarCombo() {
        Combo combo = new Combo();
        combo.setId(10L);
        combo.setNombre("Eliminarme");
        combo.setProductos(List.of());
        combo.setPrecio(999.0);
        fakeComboRepo.save(combo);

        comboService.eliminar(10L);
        Optional<Combo> resultado = fakeComboRepo.findById(10L);

        assertFalse(resultado.isPresent());
    }

    @Test
    public void testListarCombos() {
        Combo c1 = new Combo();
        c1.setId(1L);
        c1.setNombre("Combo 1");
        c1.setProductos(List.of());
        c1.setPrecio(1000.0);

        Combo c2 = new Combo();
        c2.setId(2L);
        c2.setNombre("Combo 2");
        c2.setProductos(List.of());
        c2.setPrecio(2000.0);

        fakeComboRepo.save(c1);
        fakeComboRepo.save(c2);

        List<Combo> combos = comboService.listar();

        assertEquals(2, combos.size());
    }
}
