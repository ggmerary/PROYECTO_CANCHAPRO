/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class ArchivoCancha {

    private String archivo = "canchas.csv";

    public boolean guardarCanchas(Cancha[] listaCanchas,
            int cantidadCanchas) {

        if (listaCanchas == null|| cantidadCanchas < 0
            || cantidadCanchas > listaCanchas.length) {
            return false;
        }

        try (FileWriter fw = new FileWriter(archivo);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (int i = 0; i < cantidadCanchas; i++) {

                Cancha cancha = listaCanchas[i];

                if (cancha == null) {
                    return false;
                }

                bw.write(
                        cancha.getCodigo() + ","
                        + cancha.getNombre() + ","
                        + cancha.getTipo() + ","
                        + cancha.getCapacidad() + ","
                        + cancha.getEstado() + ","
                        + cancha.getHoraApertura() + ","
                        + cancha.getHoraCierre()
                );

                bw.newLine();
            }

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar canchas: " + e.getMessage()
            );

            return false;
        }
    }

    public Cancha[] cargarCanchas() {

        File fichero = new File(archivo);

        if (!fichero.exists()) {
            return null;
        }

        Cancha[] canchasCargadas = new Cancha[100];
        int cantidad = 0;

        try (FileReader fr = new FileReader(archivo);
         BufferedReader br = new BufferedReader(fr)) {

            String linea = br.readLine();

            while (linea != null) {

                if (!linea.trim().isEmpty()) {

                    String[] datos = linea.split(",", -1);

                    if (datos.length != 7 || cantidad >= 100) {
                        return null;
                    }

                    String codigo = datos[0].trim();
                    String nombre = datos[1].trim();
                    String tipo = datos[2].trim();

                    int capacidad =
                            Integer.parseInt(datos[3].trim());


                    String estado = datos[4].trim();

                    LocalTime horaApertura =LocalTime.parse(datos[5].trim());

                    LocalTime horaCierre =LocalTime.parse(datos[6].trim());

                  
                    if (codigo.isEmpty()
                            || nombre.isEmpty()
                            || tipo.isEmpty()
                            || capacidad <= 0
                            || horaApertura.isBefore(LocalTime.of(8, 0))
                            || horaCierre.isAfter(LocalTime.of(23, 0))
                            || !horaCierre.isAfter(horaApertura)) {

                        return null;
                    }

                 
                    if (!estado.equalsIgnoreCase("Disponible")
                            && !estado.equalsIgnoreCase("Mantenimiento")
                            && !estado.equalsIgnoreCase("Inactiva")) {

                        return null;
                    }

                    Cancha cancha = new Cancha(
                            codigo,
                            nombre,
                            tipo,
                            capacidad,
                            horaApertura,
                            horaCierre
                    );

                    cancha.setEstado(estado);

                    canchasCargadas[cantidad] = cancha;
                    cantidad++;
                }

                linea = br.readLine();
            }

            return canchasCargadas;

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "Error al cargar canchas: " + e.getMessage()
            );

            return null;
        }
    }
}