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

public class ArchivoCliente {

    public boolean guardarClientes(Cliente[] clientes) {

        try {
            FileWriter fr = new FileWriter("clientes.csv");
            BufferedWriter bw = new BufferedWriter(fr);

            for (int i = 0; i < clientes.length; i++) {

                Cliente clienteActual = clientes[i];

                bw.write(clienteActual.getCodigo() + ","
                        + clienteActual.getNombre() + ","
                        + clienteActual.getTelefono() + ","
                        + clienteActual.getCorreo() + ","
                        + clienteActual.getFechaRegistro() + ","
                        + clienteActual.estaActivo());

                bw.newLine();
            }

            bw.close();

            System.out.println("Clientes guardados correctamente");
            return true;

        } catch (IOException e) {

            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    public Cliente[] cargarClientes(int capacidad) {

        Cliente[] clientes = new Cliente[capacidad];
        int cantidad = 0;

        try {
            FileReader fr = new FileReader("clientes.csv");
            BufferedReader br = new BufferedReader(fr);

            String linea = br.readLine();

            while (linea != null) {

                String[] datos = linea.split(",", -1);

               
            if (datos.length == 6 && cantidad < clientes.length) {

               String codigo = datos[0];
               String nombre = datos[1];
               String telefono = datos[2];
               String correo = datos[3];

                LocalDate fecha = LocalDate.parse(datos[4]);

                Cliente cliente = new Cliente(
                codigo, nombre, telefono, correo, fecha);

                boolean activo = Boolean.parseBoolean(datos[5]);
                cliente.setActivo(activo);

                 clientes[cantidad] = cliente;
                 cantidad++;
            }

                linea = br.readLine();
            }

            br.close();

            System.out.println("Clientes cargados: " + cantidad);

        } catch (IOException e) {

            System.out.println("Error al cargar: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Error en los datos: " + e.getMessage());
        }

        Cliente[] registrados = new Cliente[cantidad];

        for (int i = 0; i < cantidad; i++) {
            registrados[i] = clientes[i];
        }

        return registrados;
    }
}
