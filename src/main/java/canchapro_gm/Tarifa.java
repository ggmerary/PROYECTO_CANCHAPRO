/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author genesis
 */
public class Tarifa {
    

     private double precioDia;
    private double precioNoche;
    private double precioFinSemana;

    public Tarifa() {
        precioDia = 150;
        precioNoche = 225;
        precioFinSemana = 250;
    }

    public double obtenerPrecioHora(LocalDate fecha,
                                   LocalTime horaInicio) {

        if (fecha == null || horaInicio == null) {
            return -1;
        }

        int dia = fecha.getDayOfWeek().getValue();

        if (dia == 6 || dia == 7) {
            return precioFinSemana;

        } else if (horaInicio.getHour() < 17) {
            return precioDia;

        } else {
            return precioNoche;
        }
    }

    // Método 2: Calcular el total
public double calcularTotal(LocalDate fecha,
                           LocalTime horaInicio,
                           int duracionMinutos) {

    if (fecha == null || horaInicio == null
            || duracionMinutos <= 0) {
        return -1;
    }

    double precioHora = obtenerPrecioHora(fecha, horaInicio);

    double horas = duracionMinutos / 60.0;

    double total = precioHora * horas;

    return Math.round(total * 100.0) / 100.0;
}

    public boolean modificarTarifas(double dia,double noche,double finSemana) {

        if (dia <= 0 || noche <= 0 || finSemana <= 0
                || !Double.isFinite(dia)
                || !Double.isFinite(noche)
                || !Double.isFinite(finSemana)) {
            return false;
        }

        precioDia = dia;
        precioNoche = noche;
        precioFinSemana = finSemana;

        return true;
    }

    public double getPrecioDia() {
        return precioDia;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public double getPrecioFinSemana() {
        return precioFinSemana;
    }
}
    

