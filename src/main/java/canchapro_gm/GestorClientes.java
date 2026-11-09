/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;

public class GestorClientes {
    private Cliente[] listaClientes;
    private int cantidadClientes; 
    private int correlativoCodigo;
    
    public GestorClientes(int capacidad){
        listaClientes=new Cliente[capacidad];
        cantidadClientes=0;
        correlativoCodigo=1;
    }
   public Cliente buscarClientePorCodigo(String codigo){
       for(int i=0; i<cantidadClientes; i++){
           Cliente clienteActual=listaClientes[i];
           String codigoActual=clienteActual.getCodigo();
           if (codigoActual.equals(codigo)){
               return clienteActual;
           }
       }
       return null;
   }
   public Cliente[]buscarClientesPorNombre(String nombre){
       Cliente[]encontrados=new Cliente[cantidadClientes];
       int cantidadEncontrados=0;
       
      String nombreBuscado=nombre.trim().toLowerCase();
      for(int i=0; i<cantidadClientes; i++){
          Cliente clienteActual=listaClientes[i];
          String nombreActual=clienteActual.getNombre();
          String nombreCliente=nombreActual.toLowerCase();
          if(nombreCliente.contains(nombreBuscado)){
              encontrados[cantidadEncontrados]=clienteActual;
              cantidadEncontrados++;
          }
          
      }
      Cliente[]resultados= new Cliente[cantidadEncontrados];
      for(int i=0;i<cantidadEncontrados; i++){
          resultados[i]=encontrados[i];
      }
       return resultados;
   }
    public Cliente agregarCliente(String nombre, String telefono, String correo ){
        if (nombre == null || nombre.trim().isEmpty()
            || telefono == null || telefono.trim().isEmpty()
            || correo == null || correo.trim().isEmpty()) {

        return null;
        }
        if(cantidadClientes>listaClientes.length){
            return null;
        }
        String codigo=generarCodigoCliente();
        
        while(buscarClientePorCodigo(codigo)!=null){
            correlativoCodigo++;
            codigo=generarCodigoCliente();
        }
        Cliente encontrado=buscarClientePorCodigo(codigo);
        
        if(encontrado!=null){
            return null;
        }
        Cliente nuevoCliente=new Cliente(codigo, nombre.trim(),telefono.trim(),correo.trim(), LocalDate.now());
        listaClientes[cantidadClientes]=nuevoCliente;
         cantidadClientes++;
         correlativoCodigo++;
         return nuevoCliente;
    }
    
    public boolean modificarCliente(String codigo, String nombre, String telefono, String correo){
        Cliente clienteEncontrado=buscarClientePorCodigo(codigo);
      
        if(clienteEncontrado!=null){
            clienteEncontrado.actualizarDatos(nombre, telefono, correo);
        return true; 
    }
        return false; 
    }
    
    public boolean eliminarCliente(String codigo){
        for(int i=0; i<cantidadClientes; i++){
            Cliente clienteActual=listaClientes[i];
            String codigoActual= clienteActual.getCodigo();
            if(codigoActual.equals(codigo)){
                for(int j=i; j<cantidadClientes-1;j++){
                    listaClientes[j]=listaClientes[j+1];
                    
                }
                listaClientes[cantidadClientes-1]=null;
               cantidadClientes=cantidadClientes-1;
                return true;
            }
        }
        return false;
    }
    public boolean desactivarCliente(String codigo){
        Cliente cliente=buscarClientePorCodigo(codigo);
        if(cliente!=null){
            if(cliente.estaActivo()){
                cliente.setActivo(false);
                return true;
            }
        }
            return false;
    }
    public boolean reactivarCliente(String codigo) {

    Cliente cliente = buscarClientePorCodigo(codigo);

    if (cliente != null) {

        if (!cliente.estaActivo()) {
            cliente.setActivo(true);
            return true;
        }
    }

    return false;
}
    
    public Cliente[]obtenerClientes(){
        Cliente[]registrados=new Cliente[cantidadClientes];
        for(int i=0;i<cantidadClientes;i++){
            registrados[i]=listaClientes[i];
        }
        return registrados;
        
    }
    public void establecerClientes(Cliente[]clientesCargados){
        cantidadClientes=0;
        correlativoCodigo=1;
        if(clientesCargados==null){
            return;
        }
        
        for(int i=0;i<listaClientes.length;i++){
            listaClientes[i]=null;
        }
        for(int i=0;i<clientesCargados.length;i++){
            Cliente cliente=clientesCargados[i];
            if(cliente!=null&&cantidadClientes<listaClientes.length){
            listaClientes[cantidadClientes]=cliente;
            cantidadClientes++;
            String codigo=cliente.getCodigo();
            if(codigo !=null &&codigo.matches("C[0-9]+")){
                String numeroTexto=codigo.substring(1);
                int numero=Integer.parseInt(numeroTexto);
                if(numero>=correlativoCodigo){
                    correlativoCodigo=numero+1;
                }
            }
            
        }
        }
    }
    public boolean guardarClientes(){
        ArchivoCliente archivo=new ArchivoCliente();
        Cliente[]clientes=obtenerClientes();
        return archivo.guardarClientes(clientes);
    }
    public String generarCodigoCliente(){
        String codigo;
        if(correlativoCodigo<10){
           codigo="C00"+correlativoCodigo;
        }else if(correlativoCodigo<100){
            codigo="C0"+correlativoCodigo;   
        }else{
            codigo="C"+correlativoCodigo;
        }
  
        return codigo;
    }
    
}

