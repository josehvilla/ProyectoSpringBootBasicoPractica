package com.ebac.modulo62.service;

import com.ebac.modulo62.dto.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
