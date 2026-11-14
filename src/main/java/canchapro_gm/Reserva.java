/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private String codigo;
    private String codigoCliente;
    private String codigoCancha;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int duracionHoras;
    private double total;
    private double totalPagado;

    private EstadoReserva estado;
    private LocalDate fechaCancelacion;
    private String motivoCancelacion;
    private String usuarioResponsable;

    private ServicioAdicional[] servicios;
    private double[] preciosAplicados;
    private int cantidadServicios;

    public Reserva(String codigo, String codigoCliente,
            String codigoCancha, LocalDate fecha,
            LocalTime horaInicio, int duracionHoras,
            double total) {

        this.codigo = codigo;
        this.codigoCliente = codigoCliente;
        this.codigoCancha = codigoCancha;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracionHoras = duracionHoras;
        this.horaFin = horaInicio.plusHours(duracionHoras);
        this.total = total;
        this.totalPagado = 0;

        this.estado = EstadoReserva.PENDIENTE;

        this.fechaCancelacion = null;
        this.motivoCancelacion = "";
        this.usuarioResponsable = "";

        servicios = new ServicioAdicional[10];
        preciosAplicados = new double[10];
        cantidadServicios = 0;
    }

    public void restaurarDatos(double totalPagado,
            EstadoReserva estado,
            LocalDate fechaCancelacion,
            String motivoCancelacion,
            String usuarioResponsable) {

        this.totalPagado = totalPagado;
        this.estado = estado;
        this.fechaCancelacion = fechaCancelacion;
        this.motivoCancelacion = motivoCancelacion;
        this.usuarioResponsable = usuarioResponsable;
    }

    // GETTERS
    public String getCodigo() {
        return codigo;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public String getCodigoCancha() {
        return codigoCancha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public double getTotal() {
        return total;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public int getCantidadServicios() {
        return cantidadServicios;
    }

    // CALCULAR SALDO PENDIENTE
    public double calcularSaldo() {

        return total - totalPagado;
    }

    // CONFIRMAR RESERVA
    public boolean confirmar() {

        if (estado == EstadoReserva.PENDIENTE) {

            estado = EstadoReserva.CONFIRMADA;
            return true;
        }

        return false;
    }

 
    public boolean cancelar(String motivo, String usuario) {

        if (estado == EstadoReserva.CANCELADA
                || estado == EstadoReserva.FINALIZADA) {
            return false;
        }

        if (motivo == null || motivo.trim().isEmpty()) {
            return false;
        }

        if (usuario == null || usuario.trim().isEmpty()) {
            return false;
        }

        estado = EstadoReserva.CANCELADA;

        fechaCancelacion = LocalDate.now();
        motivoCancelacion = motivo.trim();
        usuarioResponsable = usuario.trim();

        return true;
    }

    // REGISTRAR PAGO
    public boolean registrarPago(double monto) {

        if (estado == EstadoReserva.CANCELADA
                || estado == EstadoReserva.FINALIZADA) {
            return false;
        }

        if (!Double.isFinite(monto) || monto <= 0) {
            return false;
        }

        if (monto > calcularSaldo()) {
            return false;
        }

        totalPagado = totalPagado + monto;

        return true;
    }

    public boolean agregarServicio(ServicioAdicional servicio) {

        if (servicio == null) {
            return false;
        }

        if (estado != EstadoReserva.PENDIENTE
                || totalPagado > 0) {
            return false;
        }

        if (!servicio.estaActivo()) {
            return false;
        }

        if (!Double.isFinite(servicio.getPrecio())
                || servicio.getPrecio() < 0) {
            return false;
        }

        if (cantidadServicios >= servicios.length) {
            return false;
        }

        // Evitar servicios repetidos
        for (int i = 0; i < cantidadServicios; i++) {

            if (servicios[i].getCodigo().equalsIgnoreCase(
                    servicio.getCodigo())) {
                return false;
            }
        }

        servicios[cantidadServicios] = servicio;

        // Guardar el precio al momento de contratar
        preciosAplicados[cantidadServicios] =
                servicio.getPrecio();

        cantidadServicios++;

        return true;
    }

    // OBTENER SERVICIOS CONTRATADOS
    public ServicioAdicional[] obtenerServicios() {

        ServicioAdicional[] resultado =
                new ServicioAdicional[cantidadServicios];

        for (int i = 0; i < cantidadServicios; i++) {
            resultado[i] = servicios[i];
        }

        return resultado;
    }

    // CONSULTAR PRECIO HISTORICO DE UN SERVICIO
    public double obtenerPrecioAplicado(int posicion) {

        if (posicion < 0 || posicion >= cantidadServicios) {
            return -1;
        }

        return preciosAplicados[posicion];
    }

    // RESTAURAR SERVICIO DESDE CSV
    public boolean restaurarServicio(
            ServicioAdicional servicio, double precioAplicado) {

        if (servicio == null) {
            return false;
        }

        if (!Double.isFinite(precioAplicado)
                || precioAplicado < 0) {
            return false;
        }

        if (cantidadServicios >= servicios.length) {
            return false;
        }

        // Evitar servicios duplicados
        for (int i = 0; i < cantidadServicios; i++) {

            if (servicios[i].getCodigo().equalsIgnoreCase(
                    servicio.getCodigo())) {
                return false;
            }
        }

        servicios[cantidadServicios] = servicio;
        preciosAplicados[cantidadServicios] = precioAplicado;

        cantidadServicios++;

        return true;
    }

    public double calcularTotalServicios() {

        double totalServicios = 0;

        for (int i = 0; i < cantidadServicios; i++) {

            totalServicios =totalServicios + preciosAplicados[i];
        }

        return totalServicios;
    }


    public void actualizarTotal(double totalCancha) {

        if (estado != EstadoReserva.PENDIENTE
                || totalPagado > 0) {
            return;
        }

        if (!Double.isFinite(totalCancha)
                || totalCancha < 0) {
            return;
        }

        double totalServicios = calcularTotalServicios();

        total = totalCancha + totalServicios;
    }
}