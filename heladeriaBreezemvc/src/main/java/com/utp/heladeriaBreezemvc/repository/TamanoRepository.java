package com.utp.heladeriaBreezemvc.repository;

import com.utp.heladeriaBreezemvc.model.Tamano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TamanoRepository extends JpaRepository<Tamano, Integer> {
    Tamano findByNombre(String nombre);
}
