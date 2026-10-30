/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;


public class Cliente extends Persona {
    
 private LocalDate fechaRegistro;
    public Cliente(String codigo ,String nombre, String telefono, String correo, LocalDate fechaRegistro){
        
     super(codigo, nombre, telefono, correo);
     this.fechaRegistro=fechaRegistro;
    }
}
    
