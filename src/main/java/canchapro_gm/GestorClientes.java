/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package canchapro_gm;

public class GestorClientes {
    private Cliente[] listaclientes;
    private int cantidadClientes; 
    
    public GestorClientes(int capacidad){
        listaclientes=new Cliente[capacidad];
        cantidadClientes=0;
    }
   public Cliente buscarClientePorCodigo(String codigo){
       for(int i=0; i<cantidadClientes; i++){
           Cliente clienteActual=listaclientes[i];
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
       
       for(int i=0; i<cantidadClientes; i++){
           
       Cliente clienteActual=listaclientes[i];
       String nombreActual=clienteActual.getNombre();
       
       if(nombreActual.equalsIgnoreCase(nombre)){
           encontrados[cantidadEncontrados]=listaclientes[i];
           cantidadEncontrados ++;
       }
           
       }
      return encontrados;
       
   }
    public boolean agregarCliente(Cliente cliente){
        if (cliente==null){
            return false;
        }
        if(cantidadClientes>=listaclientes.length){
            return false; 
        }
        String codigo=cliente.getCodigo();
        Cliente encontrado=buscarClientePorCodigo(codigo);
        if (encontrado!=null){
            return false; 
        }
        listaclientes[cantidadClientes]=cliente;
        cantidadClientes++;
        return true;
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
            Cliente clienteActual=listaclientes[i];
            String codigoActual= clienteActual.getCodigo();
            if(codigoActual.equals(codigo)){
                for(int j=i; j<cantidadClientes-1;j++){
                    listaclientes[j]=listaclientes[j+1];
                    
                }
                listaclientes[cantidadClientes-1]=null;
               cantidadClientes=cantidadClientes-1;
                return true;
            }
        }
        return false;
    }
    public Cliente[]obtenerClientes(){
        Cliente[]registrados=new Cliente[cantidadClientes];
        for(int i=0;i<cantidadClientes;i++){
            registrados[i]=listaclientes[i];
        }
        return registrados;
        
    }
}
