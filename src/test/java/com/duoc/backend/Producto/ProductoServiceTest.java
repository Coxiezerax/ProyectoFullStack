package com.duoc.backend.Producto;

import com.duoc.backend.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoServiceTest {

    private ProductoService productoService;
    private FakeProductoRepository fakeRepo;

    @BeforeEach
    public void setUp() {
        fakeRepo = new FakeProductoRepository();
        productoService = new ProductoService();
        productoService.setProductoRepo(fakeRepo);
    }

    @Test
    public void testGuardarProducto() {
        Producto prod = new Producto();
        prod.setNombre("CocaCola");
        prod.setTipo("bebida");
        prod.setPrecioUnitario(1200.0);

        Producto guardado = productoService.guardar(prod);

        assertNotNull(guardado.getId());
        assertEquals("CocaCola", guardado.getNombre());
    }

    @Test
    public void testBuscarProductoPorIdExistente() {
        Producto prod = new Producto();
        prod.setNombre("Pan");
        fakeRepo.save(prod);

        Optional<Producto> resultado = productoService.buscarPorId(prod.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Pan", resultado.get().getNombre());
    }

    @Test
    public void testEliminarProducto() {
        Producto prod = new Producto();
        fakeRepo.save(prod);
        Long id = prod.getId();

        productoService.eliminar(id);

        assertFalse(fakeRepo.findById(id).isPresent());
    }

    @Test
    public void testBuscarPorTipo() {
        Producto p1 = new Producto();
        p1.setNombre("Sprite");
        p1.setTipo("bebida");
        fakeRepo.save(p1);

        Producto p2 = new Producto();
        p2.setNombre("Fanta");
        p2.setTipo("bebida");
        fakeRepo.save(p2);

        List<Producto> bebidas = productoService.buscarPorTipo("bebida");

        assertEquals(2, bebidas.size());
    }
}
