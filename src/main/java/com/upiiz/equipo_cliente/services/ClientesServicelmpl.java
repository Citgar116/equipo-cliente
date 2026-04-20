package com.upiiz.equipo_cliente.services;

import com.upiiz.equipo_cliente.entities.ClientesEntity;
import com.upiiz.equipo_cliente.repositories.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesServicelmpl implements ClientesService{

    @Autowired
    private ClientesRepository clientesRepository;

    @Override
    public ClientesEntity actualizarClientes(ClientesEntity clientes) {
        return clientesRepository.save(clientes);
    }

    @Override
    public ClientesEntity guardarClientes(ClientesEntity clientes) {
        return clientesRepository.save(clientes);
    }

    @Override
    public List<ClientesEntity> listarClientes() {
        return clientesRepository.findAll();
    }

    @Override
    public void eliminarClientes(Long id_cliente) {

        clientesRepository.deleteById(id_cliente);
    }
}
