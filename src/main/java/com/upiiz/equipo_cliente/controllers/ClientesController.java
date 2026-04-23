package com.upiiz.equipo_cliente.controllers;

import com.upiiz.equipo_cliente.entities.ClientesEntity;
import com.upiiz.equipo_cliente.entities.UsuarioEntity;
import com.upiiz.equipo_cliente.services.ClientesServicelmpl;
import jakarta.servlet.http.HttpSession;
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
    public String listarclientes(Model model, HttpSession session){
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        List<ClientesEntity> listadoClientes = clientesServicelmpl.listarClientes();
        model.addAttribute("clientes", listadoClientes );
        return "listado-clientes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
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
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
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
