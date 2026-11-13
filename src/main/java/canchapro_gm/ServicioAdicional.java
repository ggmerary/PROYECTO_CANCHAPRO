/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

public class ServicioAdicional {
  private String codigo;
    private String nombre;
    private double precio;
    private boolean activo;

    public ServicioAdicional(String codigo, String nombre,
                             double precio) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.activo = true;
        
        setPrecio(precio);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean estaActivo() {
        return activo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        if (Double.isFinite(precio) && precio >= 0) {
            this.precio = precio;
        }
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
