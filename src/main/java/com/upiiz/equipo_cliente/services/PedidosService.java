package com.upiiz.equipo_cliente.services;

import com.upiiz.equipo_cliente.entities.PedidosEntity;

import java.util.List;

public interface PedidosService {
    PedidosEntity guardarPedidos(PedidosEntity pedidos);
    List<PedidosEntity> listarPedidos();
    PedidosEntity actualizarPedidos(PedidosEntity pedidos);
    void eliminarPedidos(Long id_pedido);
}
