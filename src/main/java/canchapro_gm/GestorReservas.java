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

    public GestorReservas(int capacidad, Tarifa tarifa) {
        listaReservas = new Reserva[capacidad];
        cantidadReservas = 0;
        correlativoCodigo=1;
        this.tarifa=tarifa;
        
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
        int duracionMinutos,
        GestorDisponibilidad disponibilidad,
        GestorClientes gestorClientes,
        GestorCanchas gestorCanchas,
        ServicioAdicional[] serviciosSeleccionados) {

    if (codigoCliente == null || codigoCancha == null
            || fecha == null || horaInicio == null
            || duracionMinutos <= 0 || disponibilidad == null
            || gestorClientes == null || gestorCanchas == null
            || tarifa == null) {
        return null;
    }

    // Verificar capacidad del arreglo
    if (cantidadReservas >= listaReservas.length) {
        return null;
    }

    // Buscar cliente
    Cliente cliente =gestorClientes.buscarClientePorCodigo(codigoCliente);

    if (cliente == null) {
        return null;
    }

    if (!cliente.estaActivo()) {
        return null;
    }

    // Buscar cancha
    Cancha cancha =gestorCanchas.buscarCanchaPorCodigo(codigoCancha);

    if (cancha == null) {
        return null;
    }

    // Calcular hora final
    // Evitar que el horario llegue al día siguiente
    long segundosFin = horaInicio.toSecondOfDay()+ duracionMinutos * 60L;

    if (segundosFin >= 86400) {
        return null;
    }

    LocalTime horaFin =
            horaInicio.plusMinutes(duracionMinutos);

    // Verificar disponibilidad
    boolean disponible =
            disponibilidad.verificarDisponibilidad(
                    cancha,
                    fecha,
                    horaInicio,
                    horaFin
            );

    if (!disponible) {
        return null;
    }

    // Tarifa es quien realiza el cálculo
    double total = tarifa.calcularTotal(
            fecha,
            horaInicio,
            duracionMinutos
    );

    if (!Double.isFinite(total) || total <= 0) {
        return null;
    }

    // Generar código
    String codigo = generarCodigoReserva();

    if (buscarReserva(codigo) != null) {
        return null;
    }

    // Crear reserva con el total calculado
    Reserva reserva = new Reserva(
            codigo,
            codigoCliente,
            codigoCancha,
            fecha,
            horaInicio,
            duracionMinutos,
            total
    );
    if (serviciosSeleccionados != null) {

    for (int i = 0; i < serviciosSeleccionados.length; i++) {

        if (serviciosSeleccionados[i] != null) {

            boolean agregado = reserva.agregarServicio(
                    serviciosSeleccionados[i]
            );

            if (!agregado) {
                return null;
            }
        }
       }
    }

// Sumar el precio de los servicios al alquiler
reserva.actualizarTotal(total);

    // Registrar en el arreglo
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
    public boolean guardarReservas() {

    ArchivoReserva archivo = new ArchivoReserva();

    return archivo.guardarReservas(obtenerReservas());
    }
    public void establecerReservas(Reserva[] reservasCargadas) {

    cantidadReservas = 0;
    correlativoCodigo = 1;

    if (reservasCargadas == null) {
        return;
    }

    for (int i = 0; i < reservasCargadas.length; i++) {

        Reserva reserva = reservasCargadas[i];

        if (reserva != null
                && cantidadReservas < listaReservas.length) {

            listaReservas[cantidadReservas] = reserva;
            cantidadReservas++;

            String codigo = reserva.getCodigo();

            if (codigo != null) {

            if (codigo.matches("R[0-9]+")) {

            String textoNumero = codigo.substring(1);

            int numero = Integer.parseInt(textoNumero);

            if (numero >= correlativoCodigo) {
            correlativoCodigo = numero + 1;
            }
            }
        }
    }
    }


    for (int i = cantidadReservas; i < listaReservas.length; i++) {
        listaReservas[i] = null;
    }
    }
    public void descartarCambios() {

    ArchivoReserva archivo = new ArchivoReserva();

    Reserva[] reservasCargadas =
            archivo.cargarReservas(listaReservas.length);

    establecerReservas(reservasCargadas);
}
    
}
