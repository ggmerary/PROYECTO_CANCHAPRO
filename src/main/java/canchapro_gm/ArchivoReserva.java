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
import java.time.LocalTime;

public class ArchivoReserva {

    private String archivo = "reservas.csv";

  
    public boolean guardarReservas(Reserva[] reservas) {

        if (reservas == null) {
            return false;
        }

        try (BufferedWriter bw =new BufferedWriter(new FileWriter(archivo))) {

            for (int i = 0; i < reservas.length; i++) {

                Reserva reserva = reservas[i];

                if (reserva != null) {

                    String fechaCancelacion = "";

                    if (reserva.getFechaCancelacion() != null) {
                        fechaCancelacion =
                                reserva.getFechaCancelacion().toString();
                    }

                    bw.write(
                            reserva.getCodigo() + ";" +
                            reserva.getCodigoCliente() + ";" +
                            reserva.getCodigoCancha() + ";" +
                            reserva.getFecha() + ";" +
                            reserva.getHoraInicio() + ";" +
                            reserva.getDuracionHoras() + ";" +
                            reserva.getTotal() + ";" +
                            reserva.getTotalPagado() + ";" +
                            reserva.getEstado() + ";" +
                            fechaCancelacion + ";" +
                            reserva.getMotivoCancelacion() + ";" +
                            reserva.getUsuarioResponsable()
                    );

                    bw.newLine();
                }
            }

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar reservas: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public Reserva[] cargarReservas(int capacidad) {

        Reserva[] reservas = new Reserva[capacidad];

        int cantidad = 0;

        try (BufferedReader br =
                new BufferedReader(new FileReader(archivo))) {

            String linea = br.readLine();

            while (linea != null && cantidad < capacidad) {

                if (!linea.trim().isEmpty()) {

                    String[] datos = linea.split(";", -1);

                    if (datos.length == 12) {

                        try {

                            String codigo = datos[0];
                            String codigoCliente = datos[1];
                            String codigoCancha = datos[2];

                            LocalDate fecha =
                                    LocalDate.parse(datos[3]);

                            LocalTime horaInicio =
                                    LocalTime.parse(datos[4]);

                            // DURACION EN HORAS
                            int duracionHoras =
                                    Integer.parseInt(datos[5]);

                            double total =
                                    Double.parseDouble(datos[6]);

                            double totalPagado =
                                    Double.parseDouble(datos[7]);

                            EstadoReserva estado =
                                    EstadoReserva.valueOf(datos[8]);

                            LocalDate fechaCancelacion = null;

                            if (!datos[9].isEmpty()) {

                                fechaCancelacion =
                                        LocalDate.parse(datos[9]);
                            }

                            String motivo = datos[10];
                            String usuario = datos[11];

                            // VALIDAR DATOS
                            if (duracionHoras > 0
                                    && Double.isFinite(total)
                                    && Double.isFinite(totalPagado)
                                    && total >= 0
                                    && totalPagado >= 0
                                    && totalPagado <= total
                                    && horaInicio.toSecondOfDay()
                                    + duracionHoras * 3600L < 86400) {

                                Reserva reserva = new Reserva(
                                        codigo,
                                        codigoCliente,
                                        codigoCancha,
                                        fecha,
                                        horaInicio,
                                        duracionHoras,
                                        total
                                );

                                // RECUPERAR ESTADO Y PAGOS
                                reserva.restaurarDatos(
                                        totalPagado,
                                        estado,
                                        fechaCancelacion,
                                        motivo,
                                        usuario
                                );

                                reservas[cantidad] = reserva;

                                cantidad++;
                            }

                        } catch (RuntimeException e) {

                            System.out.println(
                                    "Reserva invalida: "
                                    + e.getMessage()
                            );
                        }
                    }
                }

                linea = br.readLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al cargar reservas: "
                    + e.getMessage()
            );
        }

        return reservas;
    }
}
