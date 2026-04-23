package com.upiiz.equipo_cliente.controllers;

import com.upiiz.equipo_cliente.entities.UsuarioEntity;
import com.upiiz.equipo_cliente.entities.PedidosEntity;
import com.upiiz.equipo_cliente.services.ClientesServicelmpl;
import com.upiiz.equipo_cliente.services.PedidosServicelmpl;
import com.upiiz.equipo_cliente.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClientesServicelmpl clientesService;

    @Autowired
    private PedidosServicelmpl pedidosService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/login")
    public String login() {
        return "login-v2";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correo") String correo,
                                @RequestParam("contrasena") String contrasena,
                                HttpSession session, Model model) {
        UsuarioEntity usuario = usuarioService.autenticar(correo, contrasena);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/auth/menu";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login-v2";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register-v2";
    }

    @PostMapping("/register")
    public String procesarRegistro(@RequestParam("nombre") String nombre,
                                   @RequestParam("correo") String correo,
                                   @RequestParam("contrasena") String contrasena,
                                   @RequestParam("confirmar_contrasena") String confirmarContrasena,
                                   Model model) {
        if (!contrasena.equals(confirmarContrasena)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "register-v2";
        }

        try {
            UsuarioEntity nuevoUsuario = new UsuarioEntity(nombre, correo, contrasena);
            usuarioService.registrarUsuario(nuevoUsuario);
            return "redirect:/auth/login?exito=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register-v2";
        }
    }


    @GetMapping("/forgot")
    public String forgot() {
        return "forgot-password-v2";
    }

    @PostMapping("/forgot")
    public String recuperarContrasena(@RequestParam("correo") String correo, Model model) {
        try {
            String contrasena = usuarioService.recuperarContrasena(correo);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Recuperación de Contraseña - RP Electronics");
            message.setText("Hola,\n\nTu contraseña es: " + contrasena + "\n\nSaludos,\nEl equipo de RP Electronics");
            
            mailSender.send(message);

            model.addAttribute("mensaje", "Se ha enviado un correo con tu contraseña.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "forgot-password-v2";
    }

    @GetMapping("/menu")
    public String menu(HttpSession session, Model model) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalClientes", clientesService.listarClientes().size());
        
        List<PedidosEntity> pedidos = pedidosService.listarPedidos();
        model.addAttribute("totalPedidos", pedidos.size());
        
        double ingresosMes = pedidos.stream()
                                    .filter(p -> p.getTotal() != null && "Pagado".equals(p.getEstado()))
                                    .mapToDouble(PedidosEntity::getTotal)
                                    .sum();
                                    
        long pedidosPendientes = pedidos.stream().filter(p -> "Pendiente".equals(p.getEstado())).count();
        
        model.addAttribute("ingresosMes", ingresosMes);
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        
        // Datos para las gráficas - simulando ventas en meses recientes
        // En una app real esto saldría de la BD agrupado por fecha
        model.addAttribute("ventasMes1", pedidos.size() * 10);
        model.addAttribute("ventasMes2", pedidos.size() * 15);
        model.addAttribute("ventasMes3", pedidos.size() * 8);
        model.addAttribute("ventasMes4", pedidos.size() * 20);
        model.addAttribute("ventasMes5", pedidos.size() * 25);
        model.addAttribute("ventasMes6", pedidos.size() * 12);
        
        return "menu";
    }

    @GetMapping("/estadisticas")
    public String estadisticas(HttpSession session, Model model) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("usuario", usuario);
        
        List<PedidosEntity> pedidos = pedidosService.listarPedidos();
        
        long pedidosPagados = pedidos.stream().filter(p -> "Pagado".equals(p.getEstado())).count();
        long pedidosPendientes = pedidos.stream().filter(p -> "Pendiente".equals(p.getEstado())).count();
        long pedidosCancelados = pedidos.stream().filter(p -> "Cancelado".equals(p.getEstado())).count();
        long pedidosEnviados = pedidos.stream().filter(p -> "Enviado".equals(p.getEstado())).count();
        
        model.addAttribute("pedidosPagados", pedidosPagados);
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        model.addAttribute("pedidosCancelados", pedidosCancelados);
        model.addAttribute("pedidosEnviados", pedidosEnviados);
        
        // Datos para crecimiento de clientes
        int totalC = clientesService.listarClientes().size();
        model.addAttribute("clientesEne", totalC > 0 ? (int)(totalC * 0.2) : 0);
        model.addAttribute("clientesFeb", totalC > 0 ? (int)(totalC * 0.3) : 0);
        model.addAttribute("clientesMar", totalC > 0 ? (int)(totalC * 0.4) : 0);
        model.addAttribute("clientesAbr", totalC > 0 ? (int)(totalC * 0.1) : 0);

        return "estadisticas";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }

}
