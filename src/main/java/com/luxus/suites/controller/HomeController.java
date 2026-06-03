package com.luxus.suites.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/reservas/solicitar")
    public String solicitarReserva(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam String suite,
            Model model
    ) {
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("suite", suite);

        return "reserva-confirmacion";
    }
}