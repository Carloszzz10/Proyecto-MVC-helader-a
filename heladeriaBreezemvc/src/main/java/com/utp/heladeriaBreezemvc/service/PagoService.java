/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.heladeriaBreezemvc.service;

import com.utp.heladeriaBreezemvc.model.DetallePedido;
import com.utp.heladeriaBreezemvc.model.EstadoPedido;
import com.utp.heladeriaBreezemvc.model.PagoPedido;
import com.utp.heladeriaBreezemvc.model.Pedido;
import com.utp.heladeriaBreezemvc.repository.DetallePedidoRepository;
import com.utp.heladeriaBreezemvc.repository.PagoPedidoRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PagoService {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private PagoPedidoRepository pagoPedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public PagoPedido procesarPago(Integer detallePedidoId, double montoPagado, Authentication authentication) {
        DetallePedido detallePedido = detallePedidoService.findById(detallePedidoId);

        // Validar que el monto pagado sea suficiente
        if (montoPagado < detallePedido.getTotal()) {
            throw new IllegalArgumentException("El monto pagado es insuficiente");
        }

        // Verificar si ya está pagado
        if ("PAGADO".equals(detallePedido.getEstadoPago())) {
            throw new IllegalArgumentException("Este pedido ya ha sido pagado");
        }

        // Calcular vuelto
        double vuelto = montoPagado - detallePedido.getTotal();

        // Log del estado actual antes de cambiarlo
        System.out.println("Estado actual del pedido: " + detallePedido.getPedido().getEstado());

        // Cambiar estado del pedido a PAGADO
        Pedido pedido = detallePedido.getPedido();
        pedido.setEstado(EstadoPedido.PAGADO);

        // Log del nuevo estado
        System.out.println("Nuevo estado del pedido: " + pedido.getEstado());

        // Crear registro de pago
        PagoPedido pagoPedido = new PagoPedido();
        pagoPedido.setDetallePedido(detallePedido);
        pagoPedido.setMontoPagado(montoPagado);
        pagoPedido.setVuelto(vuelto);
        pagoPedido.setFechaPago(new Date());
        pagoPedido.setUsuarioPago(usuarioService.findByUsername(authentication.getName()));

        // Actualizar el estado del pedido
        detallePedido.setEstadoPago("PAGADO");
        detallePedidoRepository.save(detallePedido);

        return pagoPedidoRepository.save(pagoPedido);
    }
}
