/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalTime;


public class Cancha {
    private String codigo;
    private String nombre;
    private String tipo;
    private int capacidad;
    private String estado;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    
    public Cancha(String codigo, String nombre, String tipo, int capacidad,
            LocalTime horaApertura, LocalTime horaCierre){
        this.codigo=codigo;
        this.nombre=nombre; 
        this.tipo=tipo;
        this.capacidad=capacidad;
        this.estado="Disponible";
        this.horaApertura=horaApertura;
        this.horaCierre=horaCierre;
    }
       
      public String getCodigo(){
          return codigo;
      }
      public String getNombre(){
          return nombre;
      }
      public String getTipo(){
          return tipo;
      }
      public int getCapacidad(){
          return capacidad;
      }
      public String getEstado(){
          return estado;
      }
      public LocalTime getHoraApertura(){
          return horaApertura;
      }
      public LocalTime getHoraCierre(){
          return horaCierre;
      }
      public void setEstado(String estado){
          if(estado.equalsIgnoreCase("Disponible")||
                  estado.equalsIgnoreCase("Mantenimiento")||
                  estado.equalsIgnoreCase("Inactiva")){
           this.estado=estado;
          }
          
      }
      
      public void actualizarDatos(String nombre, String tipo, int capacidad, 
                                   LocalTime horaApertura, LocalTime horaCierre){
          this.nombre=nombre;
          this.tipo=tipo;
          this.capacidad=capacidad;
          this.horaApertura=horaApertura;
          this.horaCierre=horaCierre;
      }
      public boolean estaActiva(){
          return estado.equalsIgnoreCase("Disponible");
      }
      public boolean estaEnHorario(LocalTime horaInicio,LocalTime horaFin){
          boolean inicioValido =!horaInicio.isBefore(horaApertura);

          boolean finValido =!horaFin.isAfter(horaCierre);

          boolean ordenCorrecto =
            horaFin.isAfter(horaInicio);

        if (inicioValido && finValido && ordenCorrecto) {
            return true;
          } else {
        return false;
         }
          
      }
        
    
           
}
