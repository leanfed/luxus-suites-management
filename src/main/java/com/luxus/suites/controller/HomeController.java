package com.luxus.suites.controller;

import com.luxus.suites.model.ReservaSolicitud;
import com.luxus.suites.service.ReservaService;
import com.luxus.suites.service.SuiteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ReservaService reservaService;
    private final SuiteService suiteService;

    public HomeController(ReservaService reservaService, SuiteService suiteService) {
        this.reservaService = reservaService;
        this.suiteService = suiteService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("suites", suiteService.listarSuites());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("solicitudes", reservaService.listarSolicitudes());
        model.addAttribute("totalSolicitudes", reservaService.contarSolicitudes());
        model.addAttribute("totalPendientes", reservaService.contarPendientes());
        model.addAttribute("totalConfirmadas", reservaService.contarConfirmadas());
        model.addAttribute("totalCanceladas", reservaService.contarCanceladas());
        model.addAttribute("totalSuites", suiteService.listarSuites().size());

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

    @PostMapping("/admin/reservas/{id}/confirmar")
    public String confirmarReserva(@PathVariable Long id) {
        reservaService.confirmarSolicitud(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/reservas/{id}/cancelar")
    public String cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarSolicitud(id);
        return "redirect:/admin";
    }
}