/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;


public class CanchaPro_GM {
    
    public static void main(String[] args) {
       
  //JFrameLogin formulario = new JFrameLogin();
//formulario.setVisible(true);

//JFrameMenuPrincipal menu=new JFrameMenuPrincipal();
//menu.setVisible(true);



        
        // =====================================
        // 1. CREAR RESERVA DE PRUEBA
        // =====================================

        Reserva reserva = new Reserva(
                "R001",
                "C005",
                "C03",
                LocalDate.of(2026, 11, 15),
                LocalTime.of(14, 0),
                120,
                500.00
        );

        System.out.println("===== RESERVA =====");
        System.out.println("Codigo: " + reserva.getCodigo());
        System.out.println("Estado: " + reserva.getEstado());
        System.out.println("Total: Q" + reserva.getTotal());

        // =====================================
        // 2. CREAR GESTOR DE RESERVAS
        // =====================================

        Tarifa tarifa = new Tarifa();

        GestorReservas gestorReservas =
                new GestorReservas(100, tarifa);

        // Agregar la reserva al gestor
        Reserva[] reservasPrueba = new Reserva[1];

        reservasPrueba[0] = reserva;

        gestorReservas.establecerReservas(reservasPrueba);

        // =====================================
        // 3. PROBAR SERVICIOS ADICIONALES
        // =====================================

        System.out.println();
        System.out.println("===== SERVICIOS =====");

        ArchivoServicio archivoServicio =
                new ArchivoServicio();

        ServicioAdicional[] serviciosCargados =
                archivoServicio.cargarServicios(50);

        GestorServiciosAdicionales gestorServicios =
                new GestorServiciosAdicionales(50);

        gestorServicios.establecerServicios(serviciosCargados);

        ServicioAdicional[] servicios =
                gestorServicios.obtenerServicios();

        for (int i = 0; i < servicios.length; i++) {

            System.out.println(
                    servicios[i].getCodigo() + " - " +
                    servicios[i].getNombre() + " - Q" +
                    servicios[i].getPrecio()
            );
        }

        // =====================================
        // 4. CREAR GESTOR DE PAGOS
        // =====================================

        GestorPago gestorPagos =
                new GestorPago(100, gestorReservas);

        System.out.println();
        System.out.println("===== PRUEBA DE PAGOS =====");

        // Buscar la reserva
        Reserva reservaEncontrada =
                gestorReservas.buscarReserva("R001");

        if (reservaEncontrada != null) {

            System.out.println(
                    "Reserva: " + reservaEncontrada.getCodigo()
            );

            System.out.println(
                    "Total: Q" + reservaEncontrada.getTotal()
            );

            System.out.println(
                    "Saldo inicial: Q" +
                    reservaEncontrada.calcularSaldo()
            );

            // =====================================
            // 5. REGISTRAR PRIMER ABONO
            // =====================================

            Pago pago1 = gestorPagos.registrarPago(
                    "R001",
                    100.00,
                    MetodoPago.EFECTIVO,
                    "Primer anticipo"
            );

            if (pago1 != null) {

                System.out.println(
                        "Primer pago registrado: " +
                        pago1.getCodigo()
                );

            } else {

                System.out.println(
                        "No se pudo registrar el primer pago"
                );
            }

            // =====================================
            // 6. REGISTRAR SEGUNDO ABONO
            // =====================================

            Pago pago2 = gestorPagos.registrarPago(
                    "R001",
                    50.00,
                    MetodoPago.TRANSFERENCIA,
                    "Segundo abono"
            );

            if (pago2 != null) {

                System.out.println(
                        "Segundo pago registrado: " +
                        pago2.getCodigo()
                );

            } else {

                System.out.println(
                        "No se pudo registrar el segundo pago"
                );
            }

            // =====================================
            // 7. CONSULTAR SALDO
            // =====================================

            System.out.println();
            System.out.println("===== CONSULTA DE SALDO =====");

            System.out.println(
                    "Total de reserva: Q" +
                    reservaEncontrada.getTotal()
            );

            System.out.println(
                    "Total pagado: Q" +
                    reservaEncontrada.getTotalPagado()
            );

            System.out.println(
                    "Saldo pendiente: Q" +
                    reservaEncontrada.calcularSaldo()
            );

        } else {

            System.out.println("No se encontro la reserva R001");
        }

        // =====================================
        // 8. GUARDAR PAGOS EN CSV
        // =====================================

        // ACTIVAR SOLO EN UNA COPIA DE PRUEBA.
        // guardarPagos sobrescribe pagos.csv.

        // if (gestorPagos.guardarPagos()) {
        //     System.out.println("Pagos guardados correctamente");
        // } else {
        //     System.out.println("Error al guardar pagos");
        // }

        // =====================================
        // 9. CONSULTAR PAGOS REGISTRADOS
        // =====================================

        System.out.println();
        System.out.println("===== HISTORIAL DE PAGOS =====");

        Pago[] pagos =
                gestorPagos.obtenerPagosPorReserva("R001");

        for (int i = 0; i < pagos.length; i++) {

            System.out.println(
                    "Codigo: " + pagos[i].getCodigo()
            );

            System.out.println(
                    "Fecha: " + pagos[i].getFecha()
            );

            System.out.println(
                    "Hora: " + pagos[i].getHora()
            );

            System.out.println(
                    "Monto: Q" + pagos[i].getMonto()
            );

            System.out.println(
                    "Metodo: " + pagos[i].getMetodoPago()
            );

            System.out.println(
                    "Observacion: " + pagos[i].getObservacion()
            );

            System.out.println("-------------------------");
        }

        // =====================================
        // 10. PRUEBA DE CARGA DESDE CSV
        // =====================================

        // Después de activar el guardado en una
        // copia de prueba, puedes descomentar:

        /*
        ArchivoPago archivoPago = new ArchivoPago();

        Pago[] pagosCargados =
                archivoPago.cargarPagos(100);

        GestorPagos gestorCargado =
                new GestorPagos(100, gestorReservas);

        gestorCargado.establecerPagos(pagosCargados);

        System.out.println("PAGOS RECUPERADOS:");

        Pago[] recuperados = gestorCargado.obtenerPagos();

        for (int i = 0; i < recuperados.length; i++) {

            System.out.println(
                    recuperados[i].getCodigo() + " - Q" +
                    recuperados[i].getMonto()
            );
        }
        */
        // Guardar pagos
if (gestorPagos.guardarPagos()) {
    System.out.println("Pagos guardados correctamente");
}

// Cargar pagos desde el archivo
ArchivoPago archivoPago = new ArchivoPago();

Pago[] pagosCargados = archivoPago.cargarPagos(100);

GestorPago gestorCargado =
        new GestorPago(100, gestorReservas);

gestorCargado.establecerPagos(pagosCargados);

// Mostrar pagos recuperados
Pago[] recuperados = gestorCargado.obtenerPagos();

System.out.println("===== PAGOS RECUPERADOS =====");

for (int i = 0; i < recuperados.length; i++) {

    System.out.println(
            recuperados[i].getCodigo() + " - " +
            recuperados[i].getFecha() + " - " +
            recuperados[i].getHora() + " - Q" +
            recuperados[i].getMonto() + " - " +
            recuperados[i].getMetodoPago()
    );
}

    }
}


