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



}
