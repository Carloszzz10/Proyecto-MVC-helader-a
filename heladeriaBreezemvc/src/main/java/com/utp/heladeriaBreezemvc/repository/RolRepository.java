package com.utp.heladeriaBreezemvc.repository;


import com.utp.heladeriaBreezemvc.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);
}
