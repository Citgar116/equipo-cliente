package com.upiiz.equipo_cliente.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {return "login-v2";}

    @GetMapping("/register")
    public String register(){
        return "register-v2";
    }

    @GetMapping("/forgot")
    public String forgot() {return "forgot-password-v2"; }

    @GetMapping("/menu")
    public String menu(){return "menu";}

    @GetMapping("/estadisticas")
    public String estadisticas(){return "estadisticas";}

    @GetMapping("/listado-clientes")
    public String listadocli(){return "listado-clientes";}

    @GetMapping("/listado-pedidos")
    public String listadoped(){return "listado-pedidos"; }

    @GetMapping("/actualizar-ciente")
    public String actualizarcli(){return "actualizar-cliente";}

    @GetMapping("/actualizar-pedido")
    public String actualizarped(){return "actualizar-pedido";}

    @GetMapping("/agregar-cliente")
    public String agregarcli(){return "agregar-cliente";}

    @GetMapping("/agregar-pedido")
    public String agregarped(){return "agregar-pedido";}

    @GetMapping("/eliminar-ciente")
    public String eliminarcli(){return "eliminar-cliente";}

    @GetMapping("/eliminar-pedido")
    public String eliminarped(){return "eliminar-pedido";}
}
