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

JFrameMenuPrincipal menu=new JFrameMenuPrincipal();
menu.setVisible(true);



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
    }
}

