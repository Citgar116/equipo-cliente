package com.upiiz.equipo_cliente.repositories;


import com.upiiz.equipo_cliente.entities.ClientesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends JpaRepository<ClientesEntity,Long> {

}
