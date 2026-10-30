/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

/**
 *
 * @author genesis
 */
public class Persona {
    private String codigo; 
    private String nombre; 
    private String telefono; 
    private String correo; 
    
    public Persona(String codigo, String nombre, String telefono, String correo){
        this.codigo=codigo;
        this.nombre=nombre; 
        this.telefono=telefono; 
        this.correo=correo; 
    }
    public String getCodigo(){
        return codigo; 
    }
    public String getNombre(){
       return nombre; 
    }
     public String getTelefono(){
         return telefono; 
     }
     public String getCorreo(){
         return correo;
     }
         public void actualizarDatos(
         String nombre, String telefono, String correo) {
             this.nombre=nombre; 
             this.telefono=telefono;
             this.correo=correo; 
         }      
}
