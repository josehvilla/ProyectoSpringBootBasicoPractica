package com.ebac.modulo62.controller;

import com.ebac.modulo62.dto.Mascota;
import com.ebac.modulo62.service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping("/mascotas")
    public List<Mascota> obtenerMascotas() {
        return mascotaService.obtenerMascota();
    }

    @GetMapping("/mascotas/{id}")
    public ResponseEntity<Mascota> obtenerMascotaPorId(@PathVariable Long id) {
        Optional<Mascota> mascotaOptional = mascotaService.obtenerMascotaPorId(id);

        return mascotaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/mascotas")
    public ResponseEntity<Mascota> crearMascota(@RequestBody Mascota mascota) throws URISyntaxException {
        mascotaService.crearMascota(mascota);

        return ResponseEntity.created(new URI("htttp://localhost/mascostas")).build();
    }

    @PutMapping("/mascotas/{id}")
    public ResponseEntity<Mascota> actualizarMascota(@PathVariable Long id, @RequestBody Mascota mascotaActualizada) {
        Optional<Mascota> mascotaOptional = mascotaService.obtenerMascotaPorId(id);

        if (mascotaOptional.isPresent()) {
            mascotaActualizada.setIdMascota(mascotaOptional.get().getIdMascota());
            mascotaService.actualizarMascota(mascotaActualizada);
            return ResponseEntity.ok(mascotaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/mascotas/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);

        return ResponseEntity.noContent().build();
    }
}
