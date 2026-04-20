package com.upiiz.equipo_cliente.services;

import com.upiiz.equipo_cliente.entities.ClientesEntity;

import java.util.List;

public interface ClientesService {
    ClientesEntity guardarClientes(ClientesEntity clientes);
    List<ClientesEntity> listarClientes();
    ClientesEntity actualizarClientes(ClientesEntity clientes);
    void eliminarClientes(Long id_cliente);
}
