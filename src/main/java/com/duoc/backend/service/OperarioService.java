package com.duoc.backend.service;

import com.duoc.backend.repository.OperarioRepoSimulado;

import com.duoc.backend.Operario.Operario;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperarioService {

    private OperarioRepoSimulado operarioRepo;

    public List<Operario> listar() {
        return operarioRepo.findAll();
    }

    public Optional<Operario> buscarPorId(Long id) {
        return operarioRepo.findById(id);
    }

    public Operario guardar(Operario operario) {
        return operarioRepo.save(operario);
    }

    public void eliminar(Long id) {
        if (!operarioRepo.existsById(id)) {
            throw new EntityNotFoundException("No se encontró el operario con ID: " + id);
        }
        operarioRepo.deleteById(id);
    }

    // Setter para pruebas unitarias
    public void setOperarioRepo(OperarioRepoSimulado operarioRepo) {
        this.operarioRepo = operarioRepo;

    }

    @Autowired
    public OperarioService(OperarioRepoSimulado operarioRepo) {
        this.operarioRepo = operarioRepo;
    }

}
