package com.upiiz.equipo_cliente.services;

import com.upiiz.equipo_cliente.entities.PedidosEntity;
import com.upiiz.equipo_cliente.repositories.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosServicelmpl implements PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Override
    public PedidosEntity actualizarPedidos(PedidosEntity pedidos){
        return pedidosRepository.save(pedidos);
    }

    @Override
    public PedidosEntity guardarPedidos(PedidosEntity pedidos){
        return pedidosRepository.save(pedidos);}

    @Override
    public List<PedidosEntity> listarPedidos(){

        return pedidosRepository.findAll();
    }

    @Override
    public void eliminarPedidos(Long id_pedido){
        pedidosRepository.deleteById(id_pedido);

    }
}
