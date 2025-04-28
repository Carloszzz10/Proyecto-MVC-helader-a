package com.utp.heladeriaBreezemvc.repository;

import com.utp.heladeriaBreezemvc.model.Asunto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsuntoRepository extends 
        JpaRepository<Asunto, Integer> {
    
}