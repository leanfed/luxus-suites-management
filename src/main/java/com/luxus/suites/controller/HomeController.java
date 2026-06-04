package com.luxus.suites.controller;

import com.luxus.suites.model.ReservaSolicitud;
import com.luxus.suites.service.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ReservaService reservaService;

    public HomeController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("solicitudes", reservaService.listarSolicitudes());
        model.addAttribute("totalSolicitudes", reservaService.contarSolicitudes());

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
        ReservaSolicitud reserva = reservaService.guardarSolicitud(
                nombre,
                email,
                checkin,
                checkout,
                suite
        );

        model.addAttribute("reserva", reserva);

        return "reserva-confirmacion";
    }
}