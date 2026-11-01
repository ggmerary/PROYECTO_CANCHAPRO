/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ArchivoCliente {
    
   public void guardarClientes(Cliente[]clientes){
       try{
           FileWriter fr=new FileWriter("clientes,csv");
           BufferedWriter bw=new BufferedWriter(fr);
           
           for(int i=0; i<clientes.length;i++){
               Cliente clienteActual=clientes[i];
               
               bw.write(clienteActual.getCodigo()+"," +clienteActual.getNombre()+","+clienteActual.getTelefono()+","+clienteActual.getCorreo() + ","+clienteActual.getfechaRegistro());
               bw.newLine();
               
           }bw.close();
           System.out.println("Clientes Guardados correctamente");
           
       }catch (IOException e){
           System.out.println("Eror al guardar: "+e.getMessage());
       }
   }
}
