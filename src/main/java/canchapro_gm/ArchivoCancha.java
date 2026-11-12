/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;


public class ArchivoCancha {
    private String archivo="canchas.csv";
    public boolean guardarCanchas(Cancha[]listaCanchas, int cantidadCanchas){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(archivo))){
            for(int i=0;i<cantidadCanchas; i++){
                Cancha cancha=listaCanchas[i];
                bw.write(
                        cancha.getCodigo()+","
                        +cancha.getNombre()+","
                        +cancha.getTipo()+","
                        +cancha.getCapacidad()+","
                        +cancha.getPrecioBase()+","
                        +cancha.getEstado()+","
                        +cancha.getHoraApertura()+","
                        +cancha.getHoraCierre()
                );
                bw.newLine();
            }
            return true;
        }catch (IOException e){
            System.out.println("error al guardar canchas: "+e.getMessage());
            return false;
        }
    }
    public Cancha[]cargarCanchas(){
        File fichero=new File(archivo);
        if(!fichero.exists()){
            return null;
        }
        Cancha[]canchasCargadas=new Cancha[3];
        int cantidad=0;
        try(BufferedReader br=new BufferedReader(new FileReader(archivo))){
           String linea = br.readLine();

           while (linea != null) {

           if (!linea.trim().isEmpty()) {

           String[] datos = linea.split(",", -1);
                
                if(datos.length!=8||cantidad>=3){
                return null;
            }
                String codigo=datos[0].trim();
                String nombre=datos[1].trim();
                String tipo=datos[2].trim();
                
                int capacidad=Integer.parseInt(datos[3].trim());
                double precioBase=Double.parseDouble(datos[4].trim());
                String estado=datos[5].trim();
                LocalTime horaApertura=LocalTime.parse(datos[6].trim());
                LocalTime horaCierre=LocalTime.parse(datos[7].trim());
                if(nombre.isEmpty()||tipo.isEmpty()||capacidad<=0||precioBase<0||!horaCierre.isAfter(horaApertura)){
                    return null;
                }
                if(!estado.equalsIgnoreCase("Disponible")&&!estado.equalsIgnoreCase("Mantenimiento")
                        &&!estado.equalsIgnoreCase("Inactiva")){
                    return null;
                }
                    
                
                Cancha cancha=new Cancha(codigo, nombre, tipo, capacidad, precioBase, horaApertura, horaCierre);
                cancha.setEstado(estado);
                canchasCargadas[cantidad]=cancha;
                cantidad++;
           }
           linea=br.readLine();
                
            }
            if(cantidad!=3){
                return null;
            }
            return canchasCargadas;
            
        }catch(IOException e){
            System.out.println("error al cargar canchas: "+e.getMessage());
            return null;
        }
    }
    
    
}
