/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ArchivoReservaServicios {
    private String archivo = "reserva_servicios.csv";

    public boolean guardarServiciosReservas(Reserva[] reservas) {

        if (reservas == null) {
            return false;
        }

            try (FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw)) {

            for (int i = 0; i < reservas.length; i++) {

                Reserva reserva = reservas[i];

                if (reserva != null) {

                    ServicioAdicional[] servicios =
                            reserva.obtenerServicios();

                    for (int j = 0; j < servicios.length; j++) {

                        ServicioAdicional servicio = servicios[j];

                        if (servicio != null) {

                            double precioAplicado =
                                    reserva.obtenerPrecioAplicado(j);

                            bw.write(
                                    reserva.getCodigo() + ";" +
                                    servicio.getCodigo() + ";" +
                                    precioAplicado
                            );

                            bw.newLine();
                        }
                    }
                }
            }

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar servicios de reservas: "
                    + e.getMessage()
            );

            return false;
        }
    }

 
    public void cargarServiciosReservas(
            Reserva[] reservas,
            GestorServiciosAdicionales gestorServicios) {

        if (reservas == null || gestorServicios == null) {
            return;
        }

        try (FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr)) {

        String linea = br.readLine();

            while (linea != null) {

                if (!linea.trim().isEmpty()) {

                    String[] datos = linea.split(";", -1);

                    if (datos.length == 3) {

                        try {

                            String codigoReserva = datos[0];
                            String codigoServicio = datos[1];

                            double precioAplicado =
                                    Double.parseDouble(datos[2]);

                            // BUSCAR RESERVA EN EL ARREGLO
                            Reserva reserva = null;

                            for (int i = 0; i < reservas.length; i++) {

                                if (reservas[i] != null
                                        && reservas[i].getCodigo()
                                                .equalsIgnoreCase(
                                                        codigoReserva)) {

                                    reserva = reservas[i];
                                    break;
                                }
                            }

                            
                            ServicioAdicional servicio =gestorServicios.buscarServicio(codigoServicio
                                    );

                          
                            if (reserva != null && servicio != null) {

                                reserva.restaurarServicio(
                                        servicio,
                                        precioAplicado
                                );
                            }

                        } catch (RuntimeException e) {

                            System.out.println(
                                    "Servicio de reserva invalido: "
                                    + e.getMessage()
                            );
                        }
                    }
                }

                linea = br.readLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al cargar servicios de reservas: "
                    + e.getMessage()
            );
        }
    }
    
}
