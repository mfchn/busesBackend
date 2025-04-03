package com.mfcn.buses.controller;

import java.util.List;

import com.mfcn.buses.exception.BrandNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mfcn.buses.model.Marca;
import com.mfcn.buses.repository.MarcaRepository;

@RestController
@CrossOrigin("http://localhost:3000/")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @PostMapping("/marca/registrar")
    Marca nuevaMarca(@RequestBody Marca nuevaMarca){
        return marcaRepository.save(nuevaMarca);
    }

    @GetMapping("/marcas")
    List<Marca> obtenerMarcas() {
        return marcaRepository.findAll();
    }

    @GetMapping("/marca/{id}")
        Marca getUserById(@PathVariable Long id){
            return marcaRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));
    }

    @PutMapping("/marca/editar/{id}")
        Marca actualizarMarca(@RequestBody Marca nuevaMarca, @PathVariable Long id) {
            return marcaRepository.findById(id)
            .map(marca -> {
                marca.setNombre(nuevaMarca.getNombre());
                return marcaRepository.save(marca);
            }).orElseThrow(() -> new BrandNotFoundException (id));
    }

    @DeleteMapping("/marca/{id}")
        String eliminarMarca(@PathVariable Long id) {
        if (!marcaRepository.existsById(id)) {
            throw new BrandNotFoundException(id);
        }
        marcaRepository.deleteById(id);
        return "Marca con id " + id + " fue borrado.";
    }
}