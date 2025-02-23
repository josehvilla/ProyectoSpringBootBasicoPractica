package com.ebac.modulo62.controller;

import com.ebac.modulo62.dto.Mascota;
import com.ebac.modulo62.service.MascotaService;
import org.assertj.core.api.MapAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaControllerTest {

    @Mock
    MascotaService mascotaService;

    @InjectMocks
    MascotaController mascotaController;

    @Test
    void obtenerMascotas() {
        int mascotas = 3;
        List<Mascota> mascotaListExpected = crearMascotas(mascotas);

        //Configuramos el comportamiento del mock
        when(mascotaService.obtenerMascota()).thenReturn(mascotaListExpected);

        //Ejecutamos el metodo del controlador
        List<Mascota> mascotaListActual = mascotaController.obtenerMascotas();

        //Validamos el resultado
        assertEquals(mascotaListExpected, mascotaListActual);
    }

    @Test
    void obtenerMascotasCuandoNoExisten() {
        //Configuramos el comportamiento del mock
        when(mascotaService.obtenerMascota()).thenReturn(List.of());

        //Ejecutamos el metodo del controlador
        List<Mascota> mascotaListActual = mascotaController.obtenerMascotas();

        //Validamos el resultado
        assertTrue(mascotaListActual.isEmpty());
        verify(mascotaService, times(1)).obtenerMascota();
    }

    @Test
    void obtenerMascotaPorId() {
        long idMascota = 1;
        Optional<Mascota> mascotaExpected = Optional.of(crearMascotas(1).get(0));

        //Configuramos el comportamiento del mock
        when(mascotaService.obtenerMascotaPorId(idMascota)).thenReturn(mascotaExpected);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Mascota> mascotaResponseEntity = mascotaController.obtenerMascotaPorId(idMascota);
        Mascota mascotaActual = mascotaResponseEntity.getBody();

        //Validamos el resultado
        assertEquals(200, mascotaResponseEntity.getStatusCode().value());
        assertNotNull(mascotaActual);
        assertEquals("Mascota1", mascotaActual.getNombre());
    }

    @Test
    void obtenerMascotaPorIdCuandoNoExiste() {
        long idMascota = 1;

        //Configuramos el comportamiento del mock
        when(mascotaService.obtenerMascotaPorId(idMascota)).thenReturn(Optional.empty());

        //Ejecutamos el metodo del controlador
        ResponseEntity<Mascota> mascotaResponseEntity = mascotaController.obtenerMascotaPorId(idMascota);
        Mascota mascotaActual = mascotaResponseEntity.getBody();

        //Validamos el resultado
        assertEquals(404, mascotaResponseEntity.getStatusCode().value());
        assertTrue(Objects.isNull(mascotaActual));
    }

    @Test
    void crearMascota() throws URISyntaxException {
        Mascota mascotaExpected = crearMascotas(1).get(0);

        //Configuramos el comportamiento del mock
        when(mascotaService.crearMascota(mascotaExpected)).thenReturn(mascotaExpected);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Mascota> mascotaResponseEntity = mascotaController.crearMascota(mascotaExpected);
        Mascota mascotaActual = mascotaResponseEntity.getBody();

        //Validamos el resultado
        assertEquals(201, mascotaResponseEntity.getStatusCode().value());
        assertTrue(Objects.isNull(mascotaActual));
    }

    @Test
    void actualizarMascota() {
        int idMascota = 1;
        String nombreActualizado = "Coco";
        String dueñoActualizado = "Jose";

        Mascota mascotaAntigua = new Mascota();
        mascotaAntigua.setIdMascota(idMascota);
        mascotaAntigua.setNombre("Pandi");
        mascotaAntigua.setDueño("Alex");

        Mascota mascotaActualizada = new Mascota();
        mascotaActualizada.setNombre(nombreActualizado);
        mascotaActualizada.setDueño(dueñoActualizado);

        //Configuramos el comportamiento del mock
        when(mascotaService.obtenerMascotaPorId((long) idMascota)).thenReturn(Optional.of(mascotaAntigua));
        doNothing().when(mascotaService).actualizarMascota(mascotaActualizada);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Mascota> mascotaResponseEntity = mascotaController.actualizarMascota((long) idMascota, mascotaActualizada);
        Mascota mascotaActual = mascotaResponseEntity.getBody();

        //Validamos el resultado
        assertEquals(200, mascotaResponseEntity.getStatusCode().value());
        assertNotNull(mascotaActual);
        assertEquals(idMascota, mascotaActual.getIdMascota());
        assertEquals(nombreActualizado, mascotaActual.getNombre());
        assertEquals(dueñoActualizado, mascotaActual.getDueño());
    }

    @Test
    void actualizarMascotaCuandoLaMascotaNoExiste() {
        long idMascota = 1;
        String nombreActualizado = "Coco";
        String dueñoActualizado = "Jose";

        Mascota mascotaActualizada = new Mascota();
        mascotaActualizada.setNombre(nombreActualizado);
        mascotaActualizada.setDueño(dueñoActualizado);

        //Configuramos el comportamiento del mock
        when(mascotaService.obtenerMascotaPorId(idMascota)).thenReturn(Optional.empty());

        //Ejecutamos el metodo del controlador
        ResponseEntity<Mascota> mascotaResponseEntity = mascotaController.actualizarMascota(idMascota, mascotaActualizada);
        Mascota mascotaActual = mascotaResponseEntity.getBody();

        //Validamos el resultado
        assertEquals(404, mascotaResponseEntity.getStatusCode().value());
        assertNull(mascotaActual);
        verify(mascotaService, never()).actualizarMascota(mascotaActualizada);
    }

    @Test
    void eliminarMascota() {
        long idMascota = 1;

        //Configuramos el comportamiento del mock
        doNothing().when(mascotaService).eliminarMascota(idMascota);

        //Ejecutamos el metodo del controlador
        ResponseEntity<Void> mascotaResponseEntity = mascotaController.eliminarMascota(idMascota);

        //Validamos el resultado
        assertEquals(204, mascotaResponseEntity.getStatusCode().value());
        verify(mascotaService, times(1)).eliminarMascota(idMascota);
    }

    private List<Mascota> crearMascotas(int elementos) {
        return IntStream.range(1, elementos + 1)
                .mapToObj(i -> {
                    Mascota mascota = new Mascota();
                    mascota.setIdMascota(i);
                    mascota.setNombre("Mascota" + i);
                    mascota.setDueño("Dueño" + i);
                    return mascota;
                }).collect(Collectors.toList());
    }
}