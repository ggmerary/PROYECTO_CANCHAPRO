/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalTime;


public class GestorDisponibilidad {
    private GestorReservas gestorReservas;
    private GestorCanchas gestorCanchas;

    public GestorDisponibilidad(GestorReservas gestorReservas,GestorCanchas gestorCanchas) {

        this.gestorReservas = gestorReservas;
        this.gestorCanchas = gestorCanchas;
    }

    public boolean existeConflicto(Cancha cancha, LocalDate fecha,LocalTime inicio, LocalTime fin) {

        Reserva[] reservas = gestorReservas.obtenerReservas();

        for (int i = 0; i < reservas.length; i++) {

            Reserva reserva = reservas[i];
              
            if (reserva == null) {
            continue;
}

            if (reserva.getEstado() == EstadoReserva.CANCELADA) {
                continue;
            }

            if (reserva.getCodigoCancha().equals(cancha.getCodigo())
                    && reserva.getFecha().equals(fecha)) {

                if (inicio.isBefore(reserva.getHoraFin())
                        && fin.isAfter(reserva.getHoraInicio())) {

                    return true;
                }
            }
        }

        return false;
    }

    public boolean verificarDisponibilidad(Cancha cancha, LocalDate fecha,
                                           LocalTime inicio, LocalTime fin) {

        if (cancha == null || fecha == null
                || inicio == null || fin == null) {
            return false;
        }

        if (fecha.isBefore(LocalDate.now())) {
            return false;
        }
        if (fecha.equals(LocalDate.now())) {

        if (inicio.isBefore(LocalTime.now())) {
          return false;
          }

        }

        if (!inicio.isBefore(fin)) {
            return false;
        }

        if (!cancha.getEstado().equalsIgnoreCase("Disponible")) {
            return false;
        }
         
        if (cancha.getHoraApertura() == null|| cancha.getHoraCierre() == null) {
        return false;
}

        if (inicio.isBefore(cancha.getHoraApertura())
                || fin.isAfter(cancha.getHoraCierre())) {
            return false;
        }

        if (existeConflicto(cancha, fecha, inicio, fin)) {
            return false;
        }

        return true;
    }

    public Cancha[] obtenerCanchasDisponibles(LocalDate fecha,  LocalTime inicio, LocalTime fin) {

        Cancha[] canchas = gestorCanchas.obtenerCanchas();
        Cancha[] disponibles = new Cancha[canchas.length];

        int cantidad = 0;

        for (int i = 0; i < canchas.length; i++) {

         Cancha cancha = canchas[i];

            if (cancha != null&& verificarDisponibilidad(cancha, fecha, inicio, fin)) {

                disponibles[cantidad] = cancha;
                cantidad++;
            }
        }

        Cancha[] resultado = new Cancha[cantidad];

        for (int i = 0; i < cantidad; i++) {
            resultado[i] = disponibles[i];
        }

        return resultado;
    }
}
