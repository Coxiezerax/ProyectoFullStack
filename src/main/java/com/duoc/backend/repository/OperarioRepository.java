// src/main/java/com/duoc/backend/repository/OperarioRepository.java
package com.duoc.backend.repository;

import com.duoc.backend.Operario.Operario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperarioRepository extends JpaRepository<Operario, Long>, OperarioRepoSimulado {

}
