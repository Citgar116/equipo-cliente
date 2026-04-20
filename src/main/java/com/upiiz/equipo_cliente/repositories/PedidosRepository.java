package com.upiiz.equipo_cliente.repositories;

import com.upiiz.equipo_cliente.entities.PedidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepository extends JpaRepository<PedidosEntity,Long> {

}
