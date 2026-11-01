/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package canchapro_gm;

import java.time.LocalDate;


public class CanchaPro_GM {
    
    public static void main(String[] args) {
        JFrameLogin formulario=new JFrameLogin();
        formulario.setVisible(true);
        
  
        
         // Crear un gestor con capacidad para 5 clientes
    GestorClientes gestor = new GestorClientes(5);

    // Crear dos clientes
    Cliente cliente1 = new Cliente(
        "C001", "Carlos Perez", "45671234",
        "carlos@gmail.com", LocalDate.now()
    );

    Cliente cliente2 = new Cliente(
        "C002", "Maria Lopez", "51234567",
        "maria@gmail.com", LocalDate.now()
    );

    // Agregar clientes
    System.out.println(gestor.agregarCliente(cliente1));
    System.out.println(gestor.agregarCliente(cliente2));

    // Intentar agregar nuevamente al primer cliente
    System.out.println(gestor.agregarCliente(cliente1));

    // Buscar por código
    Cliente encontrado = gestor.buscarClientePorCodigo("C002");

    if (encontrado != null) {
        System.out.println("Cliente encontrado: " + encontrado.getNombre());
    } else {
        System.out.println("Cliente no encontrado");
    }
        
        boolean modificado = gestor.modificarCliente(
    "C002",
    "Maria Fernanda Lopez",
    "55556666",
    "mariafernanda@gmail.com"
);

System.out.println("Cliente modificado: " + modificado);

Cliente clienteModificado = gestor.buscarClientePorCodigo("C002");

if (clienteModificado != null) {
    System.out.println("Nombre: " + clienteModificado.getNombre());
    System.out.println("Telefono: " + clienteModificado.getTelefono());
    System.out.println("Correo: " + clienteModificado.getCorreo());
}
Cliente[] registrados = gestor.obtenerClientes();

System.out.println("CLIENTES REGISTRADOS:");

for (int i = 0; i < registrados.length; i++) {

    Cliente clienteActual = registrados[i];

    System.out.println("Codigo: " + clienteActual.getCodigo());
    System.out.println("Nombre: " + clienteActual.getNombre());
    System.out.println("Telefono: " + clienteActual.getTelefono());
    System.out.println("Correo: " + clienteActual.getCorreo());
    System.out.println("-------------------------");
        
}
Cliente cliente3 = new Cliente(
    "C003",
    "Carlos Perez",
    "47896523",
    "carlos2@gmail.com",
    LocalDate.now()
);

gestor.agregarCliente(cliente3);
Cliente[] encontrados = gestor.buscarClientesPorNombre("Carlos Perez");

System.out.println("CLIENTES ENCONTRADOS:");

for (int i = 0; i < encontrados.length; i++) {

    if (encontrados[i] != null) {

        System.out.println("Codigo: " + encontrados[i].getCodigo());
        System.out.println("Nombre: " + encontrados[i].getNombre());
        System.out.println("Telefono: " + encontrados[i].getTelefono());
        System.out.println("------------------------");
    }
}
ArchivoCliente archClientes=new ArchivoCliente();
registrados=gestor.obtenerClientes();
archClientes.guardarClientes(registrados);
}
    
}
