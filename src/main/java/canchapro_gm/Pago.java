/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Pago {
    private String codigo;
    private String codigoReserva;
    private LocalDate fecha;
    private LocalTime  hora;
    private double monto;
    private MetodoPago metodoPago;
    private String observacion;

    public Pago(String codigo, String codigoReserva,
                LocalDate fecha, LocalTime hora , double monto,
                MetodoPago metodoPago, String observacion) {

        this.codigo = codigo;
        this.codigoReserva = codigoReserva;
        this.fecha=fecha;
        this.hora = hora;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observacion = observacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public LocalTime getHora(){
        return hora;
    }

    public double getMonto() {
        return monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public String getObservacion() {
        return observacion;
    }
    
}
