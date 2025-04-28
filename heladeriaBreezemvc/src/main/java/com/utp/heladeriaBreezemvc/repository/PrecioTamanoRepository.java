package com.utp.heladeriaBreezemvc.repository;

import com.utp.heladeriaBreezemvc.model.PrecioTamano;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioTamanoRepository extends JpaRepository<PrecioTamano, Integer> {
    List<PrecioTamano> findByProductoId(Integer productoId);
    PrecioTamano findByProductoIdAndTamanoId(Integer productoId, Integer tamanoId);
}