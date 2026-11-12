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



        // 1. Crear una reserva
        Reserva reserva = new Reserva(
                "R001",
                "C005",
                "C03",
                LocalDate.of(2026, 11, 15),
                LocalTime.of(14, 0),
                120,
                500
        );

        // 2. Mostrar estado inicial
        System.out.println("Estado inicial: " + reserva.getEstado());

        // 3. Registrar un pago
        boolean pagoRegistrado = reserva.registrarPago(200);

        if (pagoRegistrado) {

            System.out.println("Pago registrado correctamente");

            // 4. Confirmar la reserva
            boolean confirmada = reserva.confirmar();

            if (confirmada) {
                System.out.println("Reserva confirmada");
                System.out.println("Estado: " + reserva.getEstado());
                System.out.println("Saldo: Q" + reserva.calcularSaldo());
            }

        } else {
            System.out.println("No se pudo registrar el pago");
        }
    
     // Crear gestor de servicios
        GestorServiciosAdicionales gestor =
                new GestorServiciosAdicionales(50);

        // Crear servicios
        ServicioAdicional servicio1 =
                new ServicioAdicional("S001", "Balón", 25.00);

        ServicioAdicional servicio2 =
                new ServicioAdicional("S002", "Chalecos", 20.00);

        ServicioAdicional servicio3 =
                new ServicioAdicional("S003", "Árbitro", 100.00);

        // Agregar servicios al gestor
        gestor.agregarServicio(servicio1);
        gestor.agregarServicio(servicio2);
        gestor.agregarServicio(servicio3);

        // Guardar en CSV
        if (gestor.guardarServicios()) {
            System.out.println("Servicios guardados correctamente");
        } else {
            System.out.println("Error al guardar servicios");
        }

        // Crear otro gestor para comprobar la carga
        GestorServiciosAdicionales gestorCargado =
                new GestorServiciosAdicionales(50);

        ArchivoServicio archivo = new ArchivoServicio();

        ServicioAdicional[] cargados =
                archivo.cargarServicios(50);

        gestorCargado.establecerServicios(cargados);

        // Mostrar servicios recuperados
        System.out.println("SERVICIOS CARGADOS:");

        ServicioAdicional[] servicios =
                gestorCargado.obtenerServicios();

        for (int i = 0; i < servicios.length; i++) {

            System.out.println(
                    servicios[i].getCodigo() + " - " +
                    servicios[i].getNombre() + " - Q" +
                    servicios[i].getPrecio()
            );
        }
}
}

