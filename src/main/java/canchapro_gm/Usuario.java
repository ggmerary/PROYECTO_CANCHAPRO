/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

/**
 *
 * @author genesis
 */
public class Usuario extends Persona {
    private String nombreUsuario; 
    private String passwordUsuario;
    
    public Usuario(String codigo, String nombre, String telefono, String correo, String nombreUsuairo, String passwordUsuario){
        super(codigo, nombre, telefono,correo);
        this.nombreUsuario=nombreUsuario;
        this.passwordUsuario=passwordUsuario;
    }
    
}
