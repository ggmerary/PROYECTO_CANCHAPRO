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

    public GestorCanchas() {
        listaCanchas = new Cancha[3];
        cantidadCanchas = 0;
    }

    public void inicializarCanchas() {

        listaCanchas[0] = new Cancha(
                "C01", "La Bombonera", "Fútbol 5",
                10, 0,
                LocalTime.of(8, 0),
                LocalTime.of(23, 0));

        listaCanchas[1] = new Cancha(
                "C02", "El Maracaná", "Fútbol 5",
                10, 0,
                LocalTime.of(8, 0),
                LocalTime.of(23, 0));

        listaCanchas[2] = new Cancha(
                "C03", "El Monumental", "Fútbol 7",
                14, 0,
                LocalTime.of(8, 0),
                LocalTime.of(23, 0));

        cantidadCanchas = 3;
    }

    public Cancha buscarCanchaPorCodigo(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidadCanchas; i++) {

            if (listaCanchas[i].getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return listaCanchas[i];
            }
        }

        return null;
    }
    public boolean modificarCancha(String codigo, String nombre,
        String tipo, int capacidad, double precioBase,
        LocalTime horaApertura, LocalTime horaCierre) {

    Cancha cancha = buscarCanchaPorCodigo(codigo);

    if (cancha == null) {
        return false;
    }

    if (nombre == null || tipo == null
            || nombre.trim().isEmpty()
            || tipo.trim().isEmpty()
            || capacidad <= 0 || precioBase < 0) {
        return false;
    }

    if (horaApertura == null || horaCierre == null
            || !horaCierre.isAfter(horaApertura)) {
        return false;
    }

    cancha.actualizarDatos(nombre, tipo, capacidad,
            precioBase, horaApertura, horaCierre);

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

    public Cancha[] obtenerCanchas() {
        return listaCanchas;
    }

    public int getCantidadCanchas() {
        return cantidadCanchas;
    }
    public boolean establecerCanchas(Cancha[]canchasCargadas){
        if(canchasCargadas==null||canchasCargadas.length!=3){
            return false;
        }
        for(int i=0;i<3;i++){
            if(canchasCargadas[i]==null){
                return false;
            }
        }
        for(int i=0; i<3;i++){
            listaCanchas[i]=canchasCargadas[i];
        }
        cantidadCanchas=3;
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
    
    public boolean guardarCanchas(){
        ArchivoCancha archivoCancha=new ArchivoCancha();
        return archivoCancha.guardarCanchas(listaCanchas, cantidadCanchas);
    }
}
