package com.utp.heladeriaBreezemvc.repository;

import com.utp.heladeriaBreezemvc.model.DetallePedido;
import com.utp.heladeriaBreezemvc.model.PagoPedido;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoPedidoRepository extends JpaRepository<PagoPedido, Integer> {
    Optional<PagoPedido> findByDetallePedido_Id(Integer detallePedidoId);

    public Object findByDetallePedidoId(Integer detallePedidoId);
    
     Optional<PagoPedido> findByDetallePedido(DetallePedido detallePedido);
}
