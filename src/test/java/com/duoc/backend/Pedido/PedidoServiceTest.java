package com.duoc.backend.Pedido;

import com.duoc.backend.Combo.Combo;
import com.duoc.backend.Combo.FakeComboRepository;
import com.duoc.backend.Producto.FakeProductoRepository;
import com.duoc.backend.Producto.Producto;
import com.duoc.backend.Operario.Operario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoServiceTest {

    private PedidoService pedidoService;
    private PedidoRepoSimulado fakePedidoRepo;
    private FakeProductoRepository fakeProductoRepo;
    private FakeComboRepository fakeComboRepo;

    @BeforeEach
    public void setUp() {
        fakePedidoRepo = new FakePedidoRepository();
        fakeProductoRepo = new FakeProductoRepository();
        fakeComboRepo = new FakeComboRepository();

        pedidoService = new PedidoService();
        pedidoService.setPedidoRepo(fakePedidoRepo);
        pedidoService.setProductoRepository(fakeProductoRepo);
        pedidoService.setComboRepository(fakeComboRepo);
    }

    @Test
    public void testGuardarPedidoConProductos() {
        Producto p = new Producto();
        p.setNombre("CocaCola");
        p.setTipo("bebida");
        p.setPrecioUnitario(1200.0);
        p.setStock(10);
        fakeProductoRepo.save(p);

        Pedido pedido = new Pedido();
        pedido.setDescripcion("Pedido 1");
        pedido.setMetodoPago("Efectivo");
        pedido.setProductos(List.of(p));

        Pedido guardado = pedidoService.guardar(pedido);

        assertNotNull(guardado.getId());
        assertEquals(1, guardado.getProductos().size());
    }

    @Test
    public void testGuardarPedidoConCombo() {
        Producto p = new Producto();
        p.setNombre("Salchicha");
        p.setTipo("ingrediente");
        p.setPrecioUnitario(800.0);
        p.setStock(5);
        fakeProductoRepo.save(p);

        Combo c = new Combo();
        c.setNombre("Combo 1");
        c.setDescripcion("Salchicha + Bebida");
        c.setPrecio(2500.0);
        c.setProductos(List.of(p));
        fakeComboRepo.save(c);

        Pedido pedido = new Pedido();
        pedido.setDescripcion("Pedido combo");
        pedido.setMetodoPago("Débito");
        pedido.setCombos(List.of(c));

        Pedido guardado = pedidoService.guardar(pedido);

        assertNotNull(guardado.getId());
        assertEquals(1, guardado.getCombos().size());
    }

    @Test
    public void testBuscarPedidoPorId() {
        Pedido pedido = new Pedido();
        pedido.setDescripcion("Pedido buscado");
        Pedido guardado = pedidoService.guardar(pedido);

        Optional<Pedido> encontrado = pedidoService.buscarPorId(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Pedido buscado", encontrado.get().getDescripcion());
    }

    @Test
    public void testEliminarPedido() {
        Pedido pedido = new Pedido();
        pedido.setDescripcion("Pedido a eliminar");
        Pedido guardado = pedidoService.guardar(pedido);

        pedidoService.eliminar(guardado.getId());

        Optional<Pedido> encontrado = pedidoService.buscarPorId(guardado.getId());
        assertTrue(encontrado.isEmpty());
    }

    @Test
    public void testGuardarPedidoConOperario() {

        Operario op = new Operario();
        op.setNombre("Juan");
        op.setApellido("Pérez");

        Pedido pedido = new Pedido();
        pedido.setDescripcion("Pedido de operario");
        pedido.setOperario(op); // Asignar el operario al pedido

        // Guardar y validar
        Pedido guardado = pedidoService.guardar(pedido);
        assertEquals("Juan", guardado.getOperario().getNombre());
    }

}
