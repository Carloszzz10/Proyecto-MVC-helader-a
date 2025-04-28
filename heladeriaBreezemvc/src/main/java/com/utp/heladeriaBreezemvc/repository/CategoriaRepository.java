package com.utp.heladeriaBreezemvc.repository;

import com.utp.heladeriaBreezemvc.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends 
        JpaRepository<Categoria, Integer>{
    
}
