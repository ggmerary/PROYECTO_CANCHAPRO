/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.io.File;
import java.time.LocalTime;

public class GestorCanchas {

    private Cancha[] listaCanchas;
    private int cantidadCanchas;
    private int correlativoCodigo;

    public GestorCanchas() {
        listaCanchas = new Cancha[100];
        cantidadCanchas = 0;
        correlativoCodigo = 1;
    }

    public String generarCodigoCancha() {

        String codigo;

        if (correlativoCodigo < 10) {
            codigo = "C0" + correlativoCodigo;
        } else {
            codigo = "C" + correlativoCodigo;
        }

        return codigo;
    }

    public Cancha agregarCancha(String nombre, String tipo,
            int capacidad,LocalTime horaApertura, LocalTime horaCierre) {

        if (nombre == null || nombre.trim().isEmpty()
                || tipo == null || tipo.trim().isEmpty()
                || capacidad <= 0
                || horaApertura == null
                || horaCierre == null
                || !horaCierre.isAfter(horaApertura)) {

            return null;
        }

        if (cantidadCanchas >= listaCanchas.length) {
            return null;
        }

        String codigo = generarCodigoCancha();

        while (buscarCanchaPorCodigo(codigo) != null) {
            correlativoCodigo++;
            codigo = generarCodigoCancha();
        }

        Cancha nuevaCancha = new Cancha(
                codigo, nombre.trim(), tipo.trim(),
                capacidad,horaApertura, horaCierre
        );

        listaCanchas[cantidadCanchas] = nuevaCancha;
        cantidadCanchas++;
        correlativoCodigo++;

        return nuevaCancha;
    }

    public void inicializarCanchas() {

        listaCanchas = new Cancha[100];
        cantidadCanchas = 0;
        correlativoCodigo = 1;

        agregarCancha("La Bombonera", "Fútbol 5",
                10, LocalTime.of(8, 0),
                LocalTime.of(23, 0));

        agregarCancha("El Maracaná", "Fútbol 5",
                10, LocalTime.of(8, 0),
                LocalTime.of(23, 0));

        agregarCancha("El Monumental", "Fútbol 7",
                14, LocalTime.of(8, 0),
                LocalTime.of(23, 0));
    }

    public Cancha buscarCanchaPorCodigo(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidadCanchas; i++) {

            Cancha canchaActual = listaCanchas[i];

            if (canchaActual.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return canchaActual;
            }
        }

        return null;
    }

    public boolean modificarCancha(String codigo, String nombre,
            String tipo, int capacidad, 
            LocalTime horaApertura, LocalTime horaCierre) {

        Cancha cancha = buscarCanchaPorCodigo(codigo);

        if (cancha == null) {
            return false;
        }

        if (nombre == null || tipo == null
                || nombre.trim().isEmpty()
                || tipo.trim().isEmpty()
                || capacidad <= 0) {
            return false;
        }

        if (horaApertura == null || horaCierre == null
                || !horaCierre.isAfter(horaApertura)) {
            return false;
        }

        cancha.actualizarDatos(nombre, tipo, capacidad,
                 horaApertura, horaCierre);

        return true;
    }

    public boolean cambiarEstado(String codigo, String estado) {

        Cancha cancha = buscarCanchaPorCodigo(codigo);

        if (cancha == null || estado == null) {
            return false;
        }

        if (estado.equalsIgnoreCase("Disponible")
                || estado.equalsIgnoreCase("Mantenimiento")
                || estado.equalsIgnoreCase("Inactiva")) {

            cancha.setEstado(estado);
            return true;
        }

        return false;
    }

    // Obtener canchas registradas
    public Cancha[] obtenerCanchas() {

        Cancha[] registradas = new Cancha[cantidadCanchas];

        for (int i = 0; i < cantidadCanchas; i++) {
            registradas[i] = listaCanchas[i];
        }

        return registradas;
    }

    public int getCantidadCanchas() {
        return cantidadCanchas;
    }

    public boolean establecerCanchas(Cancha[] canchasCargadas) {

        if (canchasCargadas == null) {
            return false;
        }

        cantidadCanchas = 0;
        correlativoCodigo = 1;

        for (int i = 0; i < listaCanchas.length; i++) {
            listaCanchas[i] = null;
        }

        for (int i = 0; i < canchasCargadas.length; i++) {

            Cancha cancha = canchasCargadas[i];

            if (cancha != null
                    && cantidadCanchas < listaCanchas.length) {

                listaCanchas[cantidadCanchas] = cancha;
                cantidadCanchas++;

                String codigo = cancha.getCodigo();

                if (codigo != null&& codigo.matches("C[0-9]+")) {

                    String numeroTexto = codigo.substring(1);
                    int numero = Integer.parseInt(numeroTexto);

                    if (numero >= correlativoCodigo) {
                        correlativoCodigo = numero + 1;
                    }
                }
            }
        }

        return true;
    }

    public boolean cargarDatos() {

        ArchivoCancha archivoCancha = new ArchivoCancha();

        File fichero = new File("canchas.csv");

        if (!fichero.exists()) {

            inicializarCanchas();
            return guardarCanchas();

        } else {

            Cancha[] canchasCargadas =
                    archivoCancha.cargarCanchas();

            return establecerCanchas(canchasCargadas);
        }
    }

    public boolean guardarCanchas() {

        ArchivoCancha archivoCancha = new ArchivoCancha();

        return archivoCancha.guardarCanchas(
                listaCanchas, cantidadCanchas
        );
    }

    public void descartarCambios() {

        ArchivoCancha archivoCancha = new ArchivoCancha();

        Cancha[] canchasGuardadas =
                archivoCancha.cargarCanchas();

        establecerCanchas(canchasGuardadas);
    }
}