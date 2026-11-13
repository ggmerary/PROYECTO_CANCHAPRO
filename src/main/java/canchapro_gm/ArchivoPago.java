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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class ArchivoPago {

    private String archivo = "pagos.csv";

    public boolean guardarPagos(Pago[] pagos) {

        try (FileWriter fw = new FileWriter(archivo);
         BufferedWriter bw = new BufferedWriter(fw)) {

            for (int i = 0; i < pagos.length; i++) {

                Pago pago = pagos[i];

                if (pago != null) {

                    bw.write(
                            pago.getCodigo() + ";" +
                            pago.getCodigoReserva() + ";" +
                            pago.getFecha() + ";" +
                            pago.getHora() + ";" +
                            pago.getMonto() + ";" +
                            pago.getMetodoPago() + ";" +
                            pago.getObservacion()
                    );

                    bw.newLine();
                }
            }

            return true;

        } catch (IOException e) {

            System.out.println("Error al guardar pagos: "
                    + e.getMessage());

            return false;
        }
    }

    public Pago[] cargarPagos(int capacidad) {

        Pago[] pagos = new Pago[capacidad];
        int cantidad = 0;

        try (BufferedReader br =
                new BufferedReader(new FileReader(archivo))) {

            String linea = br.readLine();

            while (linea != null && cantidad < capacidad) {

                if (!linea.trim().isEmpty()) {

                    String[] datos = linea.split(";", -1);

                    if (datos.length == 7) {

                        try {

                            String codigo = datos[0];
                            String codigoReserva = datos[1];

                            LocalDate fecha = LocalDate.parse(datos[2]);
                           LocalTime hora = LocalTime.parse(datos[3]);
                            double monto =Double.parseDouble(datos[4]);

                            MetodoPago metodo =MetodoPago.valueOf(datos[5]);

                            String observacion = datos[6];

                            if (codigo.isEmpty()
                                    || codigoReserva.isEmpty()
                                    || fecha == null
                                    || hora==null
                                    || !Double.isFinite(monto)
                                    || monto <= 0) {

                                throw new IllegalArgumentException(
                                        "Datos de pago incorrectos");
                            }

                            Pago pago = new Pago(
                                    codigo,
                                    codigoReserva,
                                    fecha,
                                    hora,
                                    monto,
                                    metodo,
                                    observacion
                            );

                            pagos[cantidad] = pago;
                            cantidad++;

                        } catch (RuntimeException e) {

                            System.out.println(
                                    "Pago inválido: " + e.getMessage());
                        }
                    }
                }

                linea = br.readLine();
            }

        } catch (IOException e) {

            System.out.println("Error al cargar pagos: "
                    + e.getMessage());
        }

        return pagos;
    }
}
