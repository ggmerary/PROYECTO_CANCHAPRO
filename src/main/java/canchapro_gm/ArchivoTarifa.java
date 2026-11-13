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

public class ArchivoTarifa {

    private String archivo = "tarifas.csv";

    public boolean guardarTarifas(Tarifa tarifa) {

        if (tarifa == null) {
            return false;
        }

        try (FileWriter fw = new FileWriter(archivo);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("PrecioDia,PrecioNoche,PrecioFinSemana");
            bw.newLine();

            bw.write(tarifa.getPrecioDia() + ","
                    + tarifa.getPrecioNoche() + ","
                    + tarifa.getPrecioFinSemana());

            bw.newLine();

            return true;

        } catch (IOException e) {

            System.out.println("Error al guardar tarifas: "
                    + e.getMessage());

            return false;
        }
    }


    public boolean cargarTarifas(Tarifa tarifa) {

        if (tarifa == null) {
            return false;
        }

        File fichero = new File(archivo);

        if (!fichero.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader(archivo))) {

            br.readLine();

            String linea = br.readLine();

            if (linea == null) {
                return false;
            }

            String[] datos = linea.split(",");

            if (datos.length != 3) {
                return false;
            }

            double dia = Double.parseDouble(datos[0].trim());
            double noche = Double.parseDouble(datos[1].trim());
            double finSemana = Double.parseDouble(datos[2].trim());

            return tarifa.modificarTarifas(
                    dia, noche, finSemana);

        } catch (IOException | NumberFormatException e) {

            System.out.println("Error al cargar tarifas: "
                    + e.getMessage());

            return false;
        }
    }
}
    

