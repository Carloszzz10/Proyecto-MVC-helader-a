package com.utp.heladeriaBreezemvc.repository;

import com.utp.heladeriaBreezemvc.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local, Integer> {
    Local findByNombre(String nombre);
    
}
