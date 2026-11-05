/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package canchapro_gm;

import java.time.LocalDate;


public class CanchaPro_GM {
    
    public static void main(String[] args) {
       
  JFrameLogin formulario = new JFrameLogin();
formulario.setVisible(true);
System.out.println("Fecha actual de Java: " + LocalDate.now());

// Crear gestor con capacidad para 5 clientes
GestorClientes gestor = new GestorClientes(5);

// REGISTRAR CLIENTES
Cliente cliente1 = gestor.agregarCliente(
        "Carlos Perez",
        "45671234",
        "carlos@gmail.com"
);

Cliente cliente2 = gestor.agregarCliente(
        "Maria Lopez",
        "51234567",
        "maria@gmail.com"
);

// Mostrar códigos generados
if (cliente1 != null) {
    System.out.println("Cliente registrado: "
            + cliente1.getCodigo());
}

if (cliente2 != null) {
    System.out.println("Cliente registrado: "
            + cliente2.getCodigo());
}

// BUSCAR CLIENTE POR CÓDIGO
Cliente encontrado = gestor.buscarClientePorCodigo("C002");

if (encontrado != null) {
    System.out.println("Cliente encontrado: "
            + encontrado.getNombre());
} else {
    System.out.println("Cliente no encontrado");
}

// MODIFICAR CLIENTE
boolean modificado = gestor.modificarCliente(
        "C002",
        "Maria Fernanda Lopez",
        "55556666",
        "mariafernanda@gmail.com"
);

System.out.println("Cliente modificado: " + modificado);

// CONSULTAR CLIENTE MODIFICADO
Cliente clienteModificado =
        gestor.buscarClientePorCodigo("C002");

if (clienteModificado != null) {
    System.out.println("Nombre: "
            + clienteModificado.getNombre());
    System.out.println("Telefono: "
            + clienteModificado.getTelefono());
    System.out.println("Correo: "
            + clienteModificado.getCorreo());
}

// REGISTRAR OTRO CLIENTE
Cliente cliente3 = gestor.agregarCliente(
        "Carlos Perez",
        "47896523",
        "carlos2@gmail.com"
);

// BUSCAR CLIENTES POR NOMBRE
Cliente[] encontrados =
        gestor.buscarClientesPorNombre("Carlos Perez");

System.out.println("CLIENTES ENCONTRADOS:");

for (int i = 0; i < encontrados.length; i++) {

    if (encontrados[i] != null) {
        System.out.println("Codigo: "
                + encontrados[i].getCodigo());
        System.out.println("Nombre: "
                + encontrados[i].getNombre());
        System.out.println("Telefono: "
                + encontrados[i].getTelefono());
        System.out.println("------------------------");
    }
}

// MOSTRAR TODOS LOS CLIENTES
Cliente[] registrados = gestor.obtenerClientes();

System.out.println("CLIENTES REGISTRADOS:");

for (int i = 0; i < registrados.length; i++) {

    Cliente clienteActual = registrados[i];

    System.out.println("Codigo: "
            + clienteActual.getCodigo());
    System.out.println("Nombre: "
            + clienteActual.getNombre());
    System.out.println("Telefono: "
            + clienteActual.getTelefono());
    System.out.println("Correo: "
            + clienteActual.getCorreo());
    System.out.println("-------------------------");
}

// GUARDAR CLIENTES EN CSV
//ArchivoCliente archClientes = new ArchivoCliente();

//registrados = gestor.obtenerClientes();

//archClientes.guardarClientes(registrados);
ArchivoCliente archivo = new ArchivoCliente();

Cliente[] clientesCargados = archivo.cargarClientes(100);

for (int i = 0; i < clientesCargados.length; i++) {

    Cliente cliente = clientesCargados[i];

    System.out.println("Código: " + cliente.getCodigo());
    System.out.println("Nombre: " + cliente.getNombre());
    System.out.println("Teléfono: " + cliente.getTelefono());
    System.out.println("Correo: " + cliente.getCorreo());
    System.out.println("Fecha: " + cliente.getFechaRegistro());

    System.out.println("----------------------");    
}
    
}
}
