package com.upiiz.equipo_cliente.controllers;

import com.upiiz.equipo_cliente.entities.PedidosEntity;
import com.upiiz.equipo_cliente.services.PedidosServicelmpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosServicelmpl pedidosServicelmpl;

    @GetMapping
    public String pedidos(Model model){
        List<PedidosEntity> listadoPedidos = pedidosServicelmpl.listarPedidos();
        model.addAttribute("pedidos", listadoPedidos);
        return "listado-pedidos";
    }

    @GetMapping ("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        PedidosEntity pedido = new PedidosEntity();
        model.addAttribute("pedido", pedido);
        return "agregar-pedidos";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedido") PedidosEntity pedido) {
        pedidosServicelmpl.guardarPedidos(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        PedidosEntity pedido = pedidosServicelmpl.listarPedidos().stream().filter(p -> p.getId_pedido().equals(id)).findFirst().orElse(null);
        model.addAttribute("pedido", pedido);
        return "actualizar-pedidos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id) {
        pedidosServicelmpl.eliminarPedidos(id);
        return "redirect:/pedidos";
    }
}
