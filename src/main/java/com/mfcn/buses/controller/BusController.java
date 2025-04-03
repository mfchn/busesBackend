package com.mfcn.buses.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import com.mfcn.buses.repository.MarcaRepository;
import com.mfcn.buses.repository.BusRepository;
import com.mfcn.buses.model.Bus;
import com.mfcn.buses.exception.BrandNotFoundException;
import com.mfcn.buses.exception.BusNotFoundException;

@RestController
@CrossOrigin("http://localhost:3000/")
public class BusController {

    @Autowired
    private BusRepository busRepository;
    
    @Autowired
    private MarcaRepository marcaRepository;

    @PostMapping("/bus/registrar")
    Bus nuevoBus(@RequestBody Bus bus) {
        // Asegurar que la marca exista
        if (bus.getMarca() != null && bus.getMarca().getId() != null) {
            if (!marcaRepository.existsById(bus.getMarca().getId())) {
                throw new BrandNotFoundException(bus.getMarca().getId());
            }
        }
        // Generar la fecha de creación automáticamente
        bus.setFechaCreacion(Timestamp.from(Instant.now()));

        return busRepository.save(bus);
    }

    @GetMapping("/bus")
    List<Bus> obtenerBuses() {
        return busRepository.findAll();
    }

    @GetMapping("/bus/{id}")
    Bus getBusById(@PathVariable Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new BusNotFoundException(id));
    }

    @PutMapping("/bus/editar/{id}")
    Bus actualizarBus(@RequestBody Bus nuevoBus, @PathVariable Long id) {
        return busRepository.findById(id)
                .map(bus -> {
                    bus.setNumeroBus(nuevoBus.getNumeroBus());
                    bus.setPlaca(nuevoBus.getPlaca());
                    bus.setCaracteristicas(nuevoBus.getCaracteristicas());
                    bus.setActivo(nuevoBus.isActivo());
                    if (nuevoBus.getMarca() != null && nuevoBus.getMarca().getId() != null) {
                        if (!marcaRepository.existsById(nuevoBus.getMarca().getId())) {
                            throw new BrandNotFoundException(nuevoBus.getMarca().getId());
                        }
                        bus.setMarca(nuevoBus.getMarca());
                    }
                    return busRepository.save(bus);
                }).orElseThrow(() -> new BusNotFoundException(id));
    }

    @DeleteMapping("/bus/{id}")
    String eliminarBus(@PathVariable Long id) {
        if (!busRepository.existsById(id)) {
            throw new BusNotFoundException(id);
        }
        busRepository.deleteById(id);
        return "Bus con id " + id + " fue borrado.";
    }

}

