package com.ebac.modulo62.controller;

import com.ebac.modulo62.dto.Mascota;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MascotaController {
    @GetMapping("/mascotas")
    public List<Mascota> obtenerMascotas() {
        return List.of(new Mascota());
    }

    @GetMapping("/mascotas/{id}")
    public Mascota obtenerMascotaPorId(@PathVariable Long id) {
        System.out.println("Id recibido: " + id);
        return new Mascota();
    }

    @PostMapping("/mascotas")
    public ResponseEntity<Mascota> crearMascota(@RequestBody Mascota mascota) {
        System.out.println("Mascota recibida: " + mascota);
        return ResponseEntity.ok(mascota);
    }

    @PutMapping("/mascotas/{id}")
    public ResponseEntity<Mascota> actualizarMascota(@PathVariable Long id, @RequestBody Mascota mascotaActualizada) {
        System.out.println("Id recibido: " + id);
        System.out.println("Mascota recibida: " + mascotaActualizada);
        return ResponseEntity.ok(mascotaActualizada);
    }

    @DeleteMapping("/mascotas/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        System.out.println("Id recibido: " + id);
        return ResponseEntity.noContent().build();
    }
}
