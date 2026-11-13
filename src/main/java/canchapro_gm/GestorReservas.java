/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalTime;

public class GestorReservas {

    private Reserva[] listaReservas;
    private int cantidadReservas;
    private int correlativoCodigo;
    private Tarifa tarifa;
    private GestorServiciosAdicionales gestorServicios;

    public GestorReservas(int capacidad, Tarifa tarifa, GestorServiciosAdicionales gestorServicio) {

        listaReservas = new Reserva[capacidad];
        cantidadReservas = 0;
        correlativoCodigo = 1;
        this.tarifa = tarifa;
         this.gestorServicios = gestorServicios;
    }

    public String generarCodigoReserva() {

        String codigo;

        if (correlativoCodigo < 10) {
            codigo = "R00" + correlativoCodigo;
        } else if (correlativoCodigo < 100) {
            codigo = "R0" + correlativoCodigo;
        } else {
            codigo = "R" + correlativoCodigo;
        }

        return codigo;
    }

    public Reserva agregarReserva(
            String codigoCliente,
            String codigoCancha,
            LocalDate fecha,
            LocalTime horaInicio,
            int duracionHoras,
            GestorDisponibilidad disponibilidad,
            GestorClientes gestorClientes,
            GestorCanchas gestorCanchas,
            ServicioAdicional[] serviciosSeleccionados) {

        if (codigoCliente == null
                || codigoCancha == null
                || fecha == null
                || horaInicio == null
                || duracionHoras <= 0
                || disponibilidad == null
                || gestorClientes == null
                || gestorCanchas == null
                || tarifa == null) {

            return null;
        }

        if (cantidadReservas >= listaReservas.length) {
            return null;
        }

        Cliente cliente =gestorClientes.buscarClientePorCodigo(codigoCliente);

        if (cliente == null) {
            return null;
        }

        if (!cliente.estaActivo()) {
            return null;
        }

        Cancha cancha =gestorCanchas.buscarCanchaPorCodigo(codigoCancha);

        if (cancha == null) {
            return null;
        }

     
        long segundosFin = horaInicio.toSecondOfDay() + duracionHoras * 3600L;

        if (segundosFin >= 86400) {
            return null;
        }

        LocalTime horaFin =horaInicio.plusHours(duracionHoras);


        boolean disponible =disponibilidad.verificarDisponibilidad(
                        cancha,
                        fecha,
                        horaInicio,
                        horaFin
                );

        if (!disponible) {
            return null;
        }

        double totalCancha = tarifa.calcularTotal(
                fecha,
                horaInicio,
                duracionHoras
        );

        if (!Double.isFinite(totalCancha)
                || totalCancha <= 0) {
            return null;
        }

        String codigo = generarCodigoReserva();

        if (buscarReserva(codigo) != null) {
            return null;
        }

        Reserva reserva = new Reserva(
                codigo,
                codigoCliente,
                codigoCancha,
                fecha,
                horaInicio,
                duracionHoras,
                totalCancha
        );

        if (serviciosSeleccionados != null) {

            for (int i = 0;
                    i < serviciosSeleccionados.length;
                    i++) {

                if (serviciosSeleccionados[i] != null) {

                    boolean agregado =
                            reserva.agregarServicio(
                                    serviciosSeleccionados[i]
                            );

                    if (!agregado) {
                        return null;
                    }
                }
            }
        }

        reserva.actualizarTotal(totalCancha);

        listaReservas[cantidadReservas] = reserva;
        cantidadReservas++;

        correlativoCodigo++;

        return reserva;
    }

    public Reserva buscarReserva(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidadReservas; i++) {

            if (listaReservas[i].getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return listaReservas[i];
            }
        }

        return null;
    }

    public Reserva[] obtenerReservas() {

        Reserva[] resultado =
                new Reserva[cantidadReservas];

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

    public boolean cancelarReserva(
            String codigo,
            String motivo,
            String usuario) {

        Reserva reserva = buscarReserva(codigo);

        if (reserva == null) {
            return false;
        }

        return reserva.cancelar(motivo, usuario);
    }

    public boolean guardarReservas() {

    ArchivoReserva archivoReserva = new ArchivoReserva();

    ArchivoReservaServicios archivoServicios =new ArchivoReservaServicios();

    Reserva[] reservas = obtenerReservas();

    boolean guardadoReservas =archivoReserva.guardarReservas(reservas);

    if (!guardadoReservas) {
        return false;
    }

    // Guardar los servicios adicionales de las reservas
    boolean guardadoServicios =
            archivoServicios.guardarServiciosReservas(reservas);

    return guardadoServicios;
    }


    public void establecerReservas(Reserva[] reservasCargadas) {

        cantidadReservas = 0;
        correlativoCodigo = 1;

        if (reservasCargadas == null) {

            for (int i = 0; i < listaReservas.length; i++) {
                listaReservas[i] = null;
            }

            return;
        }

        for (int i = 0; i < reservasCargadas.length; i++) {

            Reserva reserva = reservasCargadas[i];

            if (reserva != null
                    && cantidadReservas < listaReservas.length) {

                listaReservas[cantidadReservas] = reserva;
                cantidadReservas++;

                String codigo = reserva.getCodigo();

                if (codigo != null
                        && codigo.matches("R[0-9]+")) {

                    try {

                        int numero = Integer.parseInt(
                                codigo.substring(1)
                        );

                        if (numero >= correlativoCodigo) {
                            correlativoCodigo = numero + 1;
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Codigo de reserva invalido: "+ codigo
                        );
                    }
                }
            }
        }
        for (int i = cantidadReservas;
                i < listaReservas.length;
                i++) {

            listaReservas[i] = null;
        }
    }

    public void descartarCambios() {

        ArchivoReserva archivoReserva = new ArchivoReserva();

    ArchivoReservaServicios archivoServicios =
            new ArchivoReservaServicios();

    // Cargar las reservas desde el CSV
    Reserva[] reservasCargadas =
            archivoReserva.cargarReservas(listaReservas.length);

    // Recuperar los servicios de cada reserva
    archivoServicios.cargarServiciosReservas(
            reservasCargadas,
            gestorServicios
    );

    // Actualizar el arreglo del gestor
    establecerReservas(reservasCargadas);
    }
}
