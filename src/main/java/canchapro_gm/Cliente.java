/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;


public class Cliente {
    private String codigo;
    private String nombre; 
    private String telefono;
    private String correo; 
    private LocalDate fecha_registro;
    
    
    public Cliente(String codigo, String nombre, String telefono, String correo, LocalDate fecha_registro) {
    this.codigo=codigo;
    this.nombre=nombre;
    this.telefono=telefono;
    this.correo=correo;
    this.fecha_registro=fecha_registro;
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
    public LocalDate getFecha_registro(){
        return fecha_registro;
    }
    public void SetCodigo(String codigo){
        this.codigo=codigo;   
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setTelefono(String telefono){
        this.telefono=telefono;
    }
    public void setCorreo (String correo){
        this.correo=correo;
    }
    public void setFecha_registro(LocalDate fecha_registro){
        this.fecha_registro=fecha_registro;
    }

}
