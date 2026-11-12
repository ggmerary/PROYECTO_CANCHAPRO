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


public class ArchivoServicio {
     private String archivo = "servicios.csv";

    // Guardar servicios en el archivo CSV
    public boolean guardarServicios(ServicioAdicional[] servicios) {

        try (BufferedWriter bw =new BufferedWriter(new FileWriter(archivo))) {

            for (int i = 0; i < servicios.length; i++) {

                ServicioAdicional servicio = servicios[i];

                if (servicio != null) {

                    bw.write(
                            servicio.getCodigo() + ";" +
                            servicio.getNombre() + ";" +
                            servicio.getPrecio() + ";" +
                            servicio.estaActivo()
                    );

                    bw.newLine();
                }
            }

            return true;

        } catch (IOException e) {

            System.out.println("Error al guardar servicios: "+ e.getMessage());

            return false;
        }
    }

    // Cargar servicios desde el archivo CSV
    public ServicioAdicional[] cargarServicios(int capacidad) {

        ServicioAdicional[] servicios =new ServicioAdicional[capacidad];

        int cantidad = 0;

        try (BufferedReader br =
                new BufferedReader(new FileReader(archivo))) {

            String linea = br.readLine();

            while (linea != null && cantidad < capacidad) {

                if (!linea.trim().isEmpty()) {

                    String[] datos = linea.split(";", -1);

                    if (datos.length == 4) {

                        try {

                            String codigo = datos[0];
                            String nombre = datos[1];
                            double precio =
                                    Double.parseDouble(datos[2]);

                            if (codigo.isEmpty()|| nombre.isEmpty()
                                    || !Double.isFinite(precio)
                                    || precio < 0
                                    || (!datos[3].equals("true")
                                    && !datos[3].equals("false"))) {

                                throw new IllegalArgumentException(
                                        "Datos de servicio incorrectos");
                            }

                            boolean activo =
                                    Boolean.parseBoolean(datos[3]);

                            ServicioAdicional servicio =
                                    new ServicioAdicional(
                                            codigo,
                                            nombre,
                                            precio
                                    );

                            servicio.setActivo(activo);

                            servicios[cantidad] = servicio;
                            cantidad++;

                        } catch (RuntimeException e) {

                            System.out.println(
                                    "Servicio inválido: "+ e.getMessage()
                            );
                        }
                    }
                }

                linea = br.readLine();
            }

        } catch (IOException e) {

            System.out.println("Error al cargar servicios: "
                    + e.getMessage());
        }

        return servicios;
    }
    
}
