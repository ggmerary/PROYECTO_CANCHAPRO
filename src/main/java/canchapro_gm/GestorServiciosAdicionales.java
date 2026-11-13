/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;


public class GestorServiciosAdicionales {
    
    private ServicioAdicional[] servicios;
    private int cantidadServicios;
    private int correlativoCodigo;

    public GestorServiciosAdicionales(int capacidad) {
        servicios = new ServicioAdicional[capacidad];
        cantidadServicios = 0;
        correlativoCodigo = 1;
    }

    public String generarCodigo() {

        String codigo = "S";

        if (correlativoCodigo < 10) {
            codigo += "00" + correlativoCodigo;
        } else if (correlativoCodigo < 100) {
            codigo += "0" + correlativoCodigo;
        } else {
            codigo += correlativoCodigo;
        }

        return codigo;
    }

    public boolean agregarServicio(ServicioAdicional servicio) {

        if (servicio == null) {
            return false;
        }

        if (cantidadServicios >= servicios.length) {
            return false;
        }

        double precio = servicio.getPrecio();

        if (!Double.isFinite(precio)) {
          return false;
        }

        if (precio <=0) {
         return false;
        }

        if (servicio.getCodigo() == null|| servicio.getNombre() == null
           || servicio.getNombre().trim().isEmpty()) {
            return false;
        }

        if (buscarServicio(servicio.getCodigo()) != null) {
            return false;
        }

        servicios[cantidadServicios] = servicio;
        cantidadServicios++;

        actualizarCorrelativo(servicio.getCodigo());

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


    public boolean modificarServicio(String codigo,
            String nombre, double precio) {

        ServicioAdicional servicio = buscarServicio(codigo);

        if (servicio == null) {
            return false;
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (!Double.isFinite(precio) || precio < 0) {
            return false;
        }

        servicio.setNombre(nombre);
        servicio.setPrecio(precio);

        return true;
    }

    public boolean cambiarEstado(String codigo, boolean activo) {

        ServicioAdicional servicio = buscarServicio(codigo);

        if (servicio == null) {
            return false;
        }

        servicio.setActivo(activo);

        return true;
    }

    public ServicioAdicional[] obtenerServicios() {

        ServicioAdicional[] resultado =
                new ServicioAdicional[cantidadServicios];

        for (int i = 0; i < cantidadServicios; i++) {
            resultado[i] = servicios[i];
        }

        return resultado;
    }


    public void establecerServicios(
            ServicioAdicional[] serviciosCargados) {

        cantidadServicios = 0;
        correlativoCodigo = 1;

        for (int i = 0; i < servicios.length; i++) {
            servicios[i] = null;
        }

        if (serviciosCargados == null) {
            return;
        }

        for (int i = 0; i < serviciosCargados.length; i++) {

            if (serviciosCargados[i] != null
                    && cantidadServicios < servicios.length) {

                servicios[cantidadServicios] = serviciosCargados[i];
                cantidadServicios++;

                actualizarCorrelativo(
                        serviciosCargados[i].getCodigo());
            }
        }
    }

  
    private void actualizarCorrelativo(String codigo) {

        if (codigo == null) {
            return;
        }

        if (codigo.matches("S[0-9]+")) {

            try {
                int numero = Integer.parseInt(codigo.substring(1));

                if (numero >= correlativoCodigo) {
                    correlativoCodigo = numero + 1;
                }

            } catch (NumberFormatException e) {
            }
        }
    }

    public boolean guardarServicios() {

        ArchivoServicio archivo = new ArchivoServicio();

        return archivo.guardarServicios(obtenerServicios());
    }
}
   

