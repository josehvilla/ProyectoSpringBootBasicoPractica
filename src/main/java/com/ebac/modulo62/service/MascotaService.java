package com.ebac.modulo62.service;

import com.ebac.modulo62.dto.Mascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {
    @Autowired
    MascotaRepository mascotaRepository;

    public Mascota crearMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public Optional<Mascota> obtenerMascotaPorId(Long idMascota) {
        return mascotaRepository.findById(idMascota);
    }

    public List<Mascota> obtenerMascota() {
        return mascotaRepository.findAll();
    }

    public void actualizarMascota(Mascota mascota) {
        mascotaRepository.save(mascota);
    }

    public void eliminarMascota(Long idMascota) {
        mascotaRepository.deleteById(idMascota);
    }
}
