/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;


public class GestorServiciosAdicionales {
    
    private ServicioAdicional[] servicios;
    private int cantidadServicios;

    public GestorServiciosAdicionales(int capacidad) {

        servicios = new ServicioAdicional[capacidad];
        cantidadServicios = 0;
    }

    public boolean agregarServicio(ServicioAdicional servicio) {

        if (servicio == null) {
            return false;
        }

        if (cantidadServicios >= servicios.length) {
            return false;
        }

        if (!Double.isFinite(servicio.getPrecio())
                || servicio.getPrecio() < 0) {
            return false;
        }

        if (buscarServicio(servicio.getCodigo()) != null) {
            return false;
        }

        servicios[cantidadServicios] = servicio;
        cantidadServicios++;

        return true;
    }

    public ServicioAdicional buscarServicio(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidadServicios; i++) {

            if (servicios[i].getCodigo().equalsIgnoreCase(codigo)) {
                return servicios[i];
            }
        }

        return null;
    }

    public ServicioAdicional[] obtenerServicios() {

        ServicioAdicional[] resultado =
                new ServicioAdicional[cantidadServicios];

        for (int i = 0; i < cantidadServicios; i++) {
            resultado[i] = servicios[i];
        }

        return resultado;
    }
    public void establecerServicios(ServicioAdicional[] serviciosCargados) {

    cantidadServicios = 0;

    // Limpiar el arreglo
    for (int i = 0; i < servicios.length; i++) {
        servicios[i] = null;
    }

    if (serviciosCargados == null) {
        return;
    }

    // Copiar los servicios cargados
    for (int i = 0; i < serviciosCargados.length; i++) {

        if (serviciosCargados[i] != null
                && cantidadServicios < servicios.length) {

            servicios[cantidadServicios] = serviciosCargados[i];
            cantidadServicios++;
        }
    }
}
    public boolean guardarServicios() {

    ArchivoServicio archivo = new ArchivoServicio();

    return archivo.guardarServicios(obtenerServicios());
}
}
