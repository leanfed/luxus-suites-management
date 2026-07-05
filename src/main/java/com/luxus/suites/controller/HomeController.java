package com.luxus.suites.controller;

import com.luxus.suites.model.ReservaSolicitud;
import com.luxus.suites.model.Suite;
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
        model.addAttribute("suitesDisponibles", suiteService.listarSuitesDisponibles());

        return "index";
    }

    @GetMapping("/admin")
    public String admin(
            @RequestParam(required = false, defaultValue = "Todas") String estado,
            Model model
    ) {
        model.addAttribute("solicitudes", reservaService.listarSolicitudesPorEstado(estado));
        model.addAttribute("estadoSeleccionado", estado);

        model.addAttribute("totalSolicitudes", reservaService.contarSolicitudes());
        model.addAttribute("totalPendientes", reservaService.contarPendientes());
        model.addAttribute("totalConfirmadas", reservaService.contarConfirmadas());
        model.addAttribute("totalCanceladas", reservaService.contarCanceladas());

        model.addAttribute("suites", suiteService.listarSuites());
        model.addAttribute("totalSuites", suiteService.listarSuites().size());

        model.addAttribute("ingresosEstimados", reservaService.calcularIngresosEstimados());
        model.addAttribute("ingresosConfirmados", reservaService.calcularIngresosConfirmados());

        return "admin";
    }

    @PostMapping("/reservas/solicitar")
    public String solicitarReserva(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam String suite,
            @RequestParam String observaciones,
            Model model
    ) {
        try {
            ReservaSolicitud reserva = reservaService.guardarSolicitud(
                    nombre,
                    email,
                    telefono,
                    checkin,
                    checkout,
                    suite,
                    observaciones
            );

            model.addAttribute("reserva", reserva);

            return "reserva-confirmacion";

        } catch (IllegalArgumentException e) {
            model.addAttribute("suites", suiteService.listarSuites());
            model.addAttribute("suitesDisponibles", suiteService.listarSuitesDisponibles());
            model.addAttribute("errorReserva", e.getMessage());

            model.addAttribute("nombreIngresado", nombre);
            model.addAttribute("emailIngresado", email);
            model.addAttribute("telefonoIngresado", telefono);
            model.addAttribute("checkinIngresado", checkin);
            model.addAttribute("checkoutIngresado", checkout);
            model.addAttribute("suiteIngresada", suite);
            model.addAttribute("observacionesIngresadas", observaciones);

            return "index";
        }
    }

    @GetMapping("/admin/reservas/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ReservaSolicitud solicitud = reservaService.buscarPorId(id);

        if (solicitud == null) {
            return "redirect:/admin";
        }

        model.addAttribute("solicitud", solicitud);
        model.addAttribute("suites", suiteService.listarSuites());

        return "reserva-editar";
    }

    @PostMapping("/admin/reservas/{id}/editar")
    public String actualizarReserva(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam String suite,
            @RequestParam String observaciones,
            Model model
    ) {
        try {
            reservaService.actualizarSolicitud(
                    id,
                    nombre,
                    email,
                    telefono,
                    checkin,
                    checkout,
                    suite,
                    observaciones
            );

            return "redirect:/admin";

        } catch (IllegalArgumentException e) {
            ReservaSolicitud solicitud = reservaService.buscarPorId(id);

            if (solicitud == null) {
                return "redirect:/admin";
            }

            model.addAttribute("solicitud", solicitud);
            model.addAttribute("suites", suiteService.listarSuites());
            model.addAttribute("errorEdicion", e.getMessage());

            return "reserva-editar";
        }
    }

    @GetMapping("/admin/suites/nueva")
    public String mostrarFormularioNuevaSuite() {
        return "suite-nueva";
    }

    @PostMapping("/admin/suites/nueva")
    public String crearSuite(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String categoria,
            @RequestParam Double precioPorNoche,
            @RequestParam Integer capacidad,
            @RequestParam(required = false) Boolean disponible,
            Model model
    ) {
        try {
            suiteService.crearSuite(
                    nombre,
                    descripcion,
                    categoria,
                    precioPorNoche,
                    capacidad,
                    disponible != null
            );

            return "redirect:/admin#suites-admin";

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorSuite", e.getMessage());

            model.addAttribute("nombreIngresado", nombre);
            model.addAttribute("descripcionIngresada", descripcion);
            model.addAttribute("categoriaIngresada", categoria);
            model.addAttribute("precioIngresado", precioPorNoche);
            model.addAttribute("capacidadIngresada", capacidad);
            model.addAttribute("disponibleIngresado", disponible != null);

            return "suite-nueva";
        }
    }

    @GetMapping("/admin/suites/{id}/editar")
    public String mostrarFormularioEdicionSuite(@PathVariable Long id, Model model) {
        Suite suite = suiteService.buscarPorId(id);

        if (suite == null) {
            return "redirect:/admin";
        }

        model.addAttribute("suite", suite);

        return "suite-editar";
    }

    @PostMapping("/admin/suites/{id}/editar")
    public String actualizarSuite(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String categoria,
            @RequestParam Double precioPorNoche,
            @RequestParam Integer capacidad,
            @RequestParam(required = false) Boolean disponible,
            Model model
    ) {
        try {
            suiteService.actualizarSuite(
                    id,
                    nombre,
                    descripcion,
                    categoria,
                    precioPorNoche,
                    capacidad,
                    disponible != null
            );

            return "redirect:/admin#suites-admin";

        } catch (IllegalArgumentException e) {
            Suite suite = suiteService.buscarPorId(id);

            if (suite == null) {
                return "redirect:/admin";
            }

            model.addAttribute("suite", suite);
            model.addAttribute("errorSuite", e.getMessage());

            return "suite-editar";
        }
    }

    @PostMapping("/admin/suites/{id}/activar")
    public String activarSuite(@PathVariable Long id) {
        suiteService.marcarSuiteDisponible(id);

        return "redirect:/admin#suites-admin";
    }

    @PostMapping("/admin/suites/{id}/desactivar")
    public String desactivarSuite(@PathVariable Long id) {
        suiteService.marcarSuiteNoDisponible(id);

        return "redirect:/admin#suites-admin";
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

    @PostMapping("/admin/reservas/{id}/reabrir")
    public String reabrirReserva(@PathVariable Long id) {
        reservaService.reabrirSolicitud(id);

        return "redirect:/admin";
    }
}