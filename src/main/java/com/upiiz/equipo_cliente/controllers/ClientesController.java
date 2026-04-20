package com.upiiz.equipo_cliente.controllers;

import com.upiiz.equipo_cliente.entities.ClientesEntity;
import com.upiiz.equipo_cliente.services.ClientesServicelmpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/clientes")
public class ClientesController {

    @Autowired
    private ClientesServicelmpl clientesServicelmpl;

    @GetMapping
    public String listarclientes(Model model){
        List<ClientesEntity> listadoClientes = clientesServicelmpl.listarClientes();
        model.addAttribute("clientes", listadoClientes );
        return "listado-clientes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        ClientesEntity cliente = new ClientesEntity();
        model.addAttribute("cliente", cliente);
        return "agregar-cliente";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") ClientesEntity cliente) {
        clientesServicelmpl.guardarClientes(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
      ClientesEntity cliente = clientesServicelmpl.listarClientes().stream().filter(c -> c.getId_cliente().equals(id)).findFirst().orElse(null);
        model.addAttribute("cliente", cliente);
        return "actualizar-cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id){
        clientesServicelmpl.eliminarClientes(id);
        return "redirect:/clientes";
    }

}
