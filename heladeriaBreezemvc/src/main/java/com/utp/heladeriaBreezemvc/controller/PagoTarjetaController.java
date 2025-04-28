package com.utp.heladeriaBreezemvc.controller;

import com.utp.heladeriaBreezemvc.model.DetallePedido;
import com.utp.heladeriaBreezemvc.model.EstadoPedido;
import com.utp.heladeriaBreezemvc.model.MetodoPago;
import com.utp.heladeriaBreezemvc.model.PagoPedido;
import com.utp.heladeriaBreezemvc.model.Pedido;
import com.utp.heladeriaBreezemvc.model.Usuario;
import com.utp.heladeriaBreezemvc.repository.PagoPedidoRepository;
import com.utp.heladeriaBreezemvc.service.ComprobantePDFService;
import com.utp.heladeriaBreezemvc.service.ContactoService;
import com.utp.heladeriaBreezemvc.service.DetallePedidoService;
import com.utp.heladeriaBreezemvc.service.LocalService;
import com.utp.heladeriaBreezemvc.service.PagoService;
import com.utp.heladeriaBreezemvc.service.PedidoService;
import com.utp.heladeriaBreezemvc.service.ProductoService;
import com.utp.heladeriaBreezemvc.service.UsuarioService;
import com.utp.heladeriaBreezemvc.service.VentaService;
import com.utp.heladeriaBreezemvc.service.VentasPDFService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PagoTarjetaController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;  // Añadir esta inyección

    @Autowired
    private ContactoService contactoService; // Añadir esta inyección

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PagoPedidoRepository pagoPedidoRepository;

    @Autowired
    private LocalService localService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private ComprobantePDFService comprobantePDFService;

    @Autowired
    private VentaService ventasService;

    @Autowired
    private VentasPDFService ventasPdfGeneratorService;
    
     //Para almacenar los detalles del Pedido
    List<DetallePedido> detalles = new ArrayList<DetallePedido>();

    //Detalles del pedido    
    Pedido pedido = new Pedido();



    @PostMapping("/tarjeta/procesar")
    public String procesarPagoTarjeta(
            @RequestParam Integer pedidoId,
            @RequestParam String numeroTarjeta,
            @RequestParam String nombreTarjeta,
            @RequestParam String fechaExpiracion,
            @RequestParam String cvv,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // Validaciones básicas de tarjeta (puedes expandir estas)
            if (!validarTarjeta(numeroTarjeta, nombreTarjeta, fechaExpiracion, cvv)) {
                redirectAttributes.addFlashAttribute("error", "Datos de tarjeta inválidos");
                return "redirect:/pagos/tarjeta/" + pedidoId;
            }

            // Simulación de pago (en un sistema real, integrarías con pasarela de pagos)
            Pedido pedido = pedidoService.findById(pedidoId);
            pedido.setMetodoPago(MetodoPago.TARJETA_CREDITO);
            pedido.setEstado(EstadoPedido.PAGADO);

            // Guardar detalles de pago
            PagoPedido pago = new PagoPedido();
            pago.setMontoPagado(pedido.getTotal());
            pago.setFechaPago(new Date());

            pedidoService.save(pedido);

            redirectAttributes.addFlashAttribute("success", "Pago realizado con éxito");
            return "redirect:/pedidos/mis-pedidos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el pago");
            return "redirect:/pagos/tarjeta/" + pedidoId;
        }
    }

    // Método de validación básica (expandir según necesidades)
    private boolean validarTarjeta(String numero, String nombre, String fecha, String cvv) {
        // Validaciones básicas de ejemplo
        return numero != null && numero.length() == 16
                && nombre != null && !nombre.isEmpty()
                && cvv != null && cvv.length() == 3;
    }
}
