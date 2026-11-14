/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class GestorPago {
    
    private Pago[] pagos;
    private int cantidadPagos;
    private int correlativoCodigo;
    private GestorReservas gestorReservas;

    public GestorPago(int capacidad, GestorReservas gestorReservas) {

        pagos = new Pago[capacidad];
        cantidadPagos = 0;
        correlativoCodigo = 1;
        this.gestorReservas = gestorReservas;
    }

    public String generarCodigoPago() {

        String codigo;

        if (correlativoCodigo < 10) {
            codigo = "P00" + correlativoCodigo;
        } else if (correlativoCodigo < 100) {
            codigo = "P0" + correlativoCodigo;
        } else {
            codigo = "P" + correlativoCodigo;
        }

        return codigo;
    }

    public Pago registrarPago(String codigoReserva,
                             double monto,
                             MetodoPago metodoPago,
                             String observacion) {

        if (codigoReserva == null
                || metodoPago == null
                || !Double.isFinite(monto)
                || monto <= 0) {
            return null;
        }

        if (gestorReservas == null
                || cantidadPagos >= pagos.length) {
            return null;
        }

 
        Reserva reserva =gestorReservas.buscarReserva(codigoReserva);

        if (reserva == null) {
            return null;
        }
        if (reserva.getEstado() == EstadoReserva.CANCELADA|| reserva.getEstado() == EstadoReserva.FINALIZADA) {
          return null;
          }

        if (monto > reserva.calcularSaldo()) {
            return null;
        }

        String codigo = generarCodigoPago();

        if (buscarPago(codigo) != null) {
            return null;
        }

        if (!reserva.registrarPago(monto)) {
            return null;
        }

        Pago pago = new Pago(
                codigo,
                codigoReserva,
                LocalDate.now(),
                LocalTime.now().withNano(0),
                monto,
                metodoPago,
                observacion == null ? "" : observacion
        );

        pagos[cantidadPagos] = pago;
        cantidadPagos++;
        correlativoCodigo++;

        return pago;
    }

    public Pago buscarPago(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidadPagos; i++) {

            if (pagos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return pagos[i];
            }
        }

        return null;
    }

    public Pago[] obtenerPagos() {

        Pago[] resultado = new Pago[cantidadPagos];

        for (int i = 0; i < cantidadPagos; i++) {
            resultado[i] = pagos[i];
        }

        return resultado;
    }

    public Pago[] obtenerPagosPorReserva(String codigoReserva) {

        if (codigoReserva == null) {
            return new Pago[0];
        }

        Pago[] encontrados = new Pago[cantidadPagos];
        int cantidad = 0;

        for (int i = 0; i < cantidadPagos; i++) {

            if (pagos[i].getCodigoReserva()
                    .equalsIgnoreCase(codigoReserva)) {

                encontrados[cantidad] = pagos[i];
                cantidad++;
            }
        }

        Pago[] resultado = new Pago[cantidad];

        for (int i = 0; i < cantidad; i++) {
            resultado[i] = encontrados[i];
        }

        return resultado;
    }

    public void establecerPagos(Pago[] pagosCargados) {

        cantidadPagos = 0;
        correlativoCodigo = 1;

        for (int i = 0; i < pagos.length; i++) {
            pagos[i] = null;
        }

        if (pagosCargados == null) {
            return;
        }

        for (int i = 0; i < pagosCargados.length; i++) {

            Pago pago = pagosCargados[i];

            if (pago != null && cantidadPagos < pagos.length) {

                pagos[cantidadPagos] = pago;
                cantidadPagos++;

                String codigo = pago.getCodigo();

                if (codigo != null&& codigo.matches("P[0-9]+")) {

                    try {
                        int numero = Integer.parseInt(
                                codigo.substring(1));

                        if (numero >= correlativoCodigo) {
                            correlativoCodigo = numero + 1;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Código de pago inválido: " + codigo);
                    }
                }
            }
        }
    }

    public boolean guardarPagos() {

        ArchivoPago archivo = new ArchivoPago();

        return archivo.guardarPagos(obtenerPagos());
    }
    
}
