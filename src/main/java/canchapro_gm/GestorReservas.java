/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;


public class GestorReservas {
    private Reserva[] listaReservas;
    private int cantidadReservas;

    public GestorReservas(int capacidad) {
        listaReservas = new Reserva[capacidad];
        cantidadReservas = 0;
    }

    public boolean agregarReserva(Reserva reserva) {

        if (reserva == null) {
            return false;
        }

        if (cantidadReservas >= listaReservas.length) {
            return false;
        }

        if (buscarReserva(reserva.getCodigo()) != null) {
            return false;
        }

        listaReservas[cantidadReservas] = reserva;
        cantidadReservas++;

        return true;
    }

    public Reserva buscarReserva(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidadReservas; i++) {

            if (listaReservas[i].getCodigo().equalsIgnoreCase(codigo)) {
                return listaReservas[i];
            }
        }

        return null;
    }

    public Reserva[] obtenerReservas() {

        Reserva[] resultado = new Reserva[cantidadReservas];

        for (int i = 0; i < cantidadReservas; i++) {
            resultado[i] = listaReservas[i];
        }

        return resultado;
    }

    public boolean confirmarReserva(String codigo) {

        Reserva reserva = buscarReserva(codigo);

        if (reserva == null) {
            return false;
        }

        return reserva.confirmar();
    }
    
}
