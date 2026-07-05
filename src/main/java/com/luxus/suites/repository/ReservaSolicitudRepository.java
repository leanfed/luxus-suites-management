package com.luxus.suites.repository;

import com.luxus.suites.model.ReservaSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaSolicitudRepository extends JpaRepository<ReservaSolicitud, Long> {

    List<ReservaSolicitud> findAllByOrderByIdDesc();
}