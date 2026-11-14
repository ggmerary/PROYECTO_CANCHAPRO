/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package canchapro_gm;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class JInternalFrmReservas extends javax.swing.JInternalFrame {

private GestorReservas gestorReservas;
private GestorClientes gestorClientes;
private GestorCanchas gestorCanchas;
private GestorServiciosAdicionales gestorServicios;
private GestorDisponibilidad gestorDisponibilidad;
private GestorPago gestorPagos;
private Tarifa tarifa;

public JInternalFrmReservas(GestorReservas gestorReservas,
        GestorClientes gestorClientes,
        GestorCanchas gestorCanchas,
        GestorServiciosAdicionales gestorServicios,
        GestorDisponibilidad gestorDisponibilidad,
        GestorPago gestorPagos,
        Tarifa tarifa) {
     
        initComponents();
        txtPrecioHora.setEditable(false);
        txtPrecioTotalCancha.setEditable(false);

        txtPrecioHora.setText("");
        txtPrecioTotalCancha.setText("");
        this.gestorReservas = gestorReservas;
        this.gestorClientes = gestorClientes;
        this.gestorCanchas = gestorCanchas;
        this.gestorServicios = gestorServicios;
        this.gestorDisponibilidad = gestorDisponibilidad;
        this.gestorPagos = gestorPagos;
        this.tarifa = tarifa;
        
        cmboxCanchaDisponible.removeAllItems();
        cmboxCliente.removeAllItems();

        txtHoraFin.setEditable(false);

        cargarHoras();
        cargarDuraciones();
        cargarServicios();

        dateFechaReserva.setMinSelectableDate(new java.util.Date());
        dateFechaReserva.setDate(new java.util.Date());

        actualizarHoraFinalizacion();
        calcularTotalServicios();
        calcularTotalGeneral();
}
private void cargarServicios() {

    cmboxServicio.removeAllItems();

    ServicioAdicional[] servicios =
            gestorServicios.obtenerServicios();

    for (int i = 0; i < servicios.length; i++) {

        ServicioAdicional servicio = servicios[i];

        if (servicio != null && servicio.estaActivo()) {

            cmboxServicio.addItem(
                    servicio.getCodigo() + " - "
                    + servicio.getNombre()
            );
        }
    }

    cmboxServicio.setSelectedIndex(-1);
}
public void actualizarServicios() {
    cargarServicios();
}
private void agregarServicioTabla() {

    if (cmboxServicio.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione un servicio.");
        return;
    }

    String seleccionado = cmboxServicio.getSelectedItem().toString();

    String codigo = seleccionado.split(" - ")[0];

    ServicioAdicional servicio =
            gestorServicios.buscarServicio(codigo);

    if (servicio == null || !servicio.estaActivo()) {
        JOptionPane.showMessageDialog(this,
                "Servicio no disponible.");
        return;
    }

    DefaultTableModel modelo =
            (DefaultTableModel) tbServiciosReserva.getModel();

    for (int i = 0; i < modelo.getRowCount(); i++) {

        String codigoTabla = modelo.getValueAt(i, 0).toString();

        if (codigoTabla.equals(servicio.getCodigo())) {
            JOptionPane.showMessageDialog(this,
                    "Este servicio ya fue agregado.");
            return;
        }
    }

    modelo.addRow(new Object[]{
        servicio.getCodigo(),
        servicio.getNombre(),
        servicio.getPrecio()
    });

    calcularTotalServicios();
    calcularTotalGeneral();
    cmboxServicio.setSelectedIndex(-1);
}
private void quitarServicioTabla() {

    int fila = tbServiciosReserva.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla.");
        return;
    }

    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Desea quitar el servicio seleccionado?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
    );

    if (respuesta == JOptionPane.YES_OPTION) {

        DefaultTableModel modelo =
                (DefaultTableModel) tbServiciosReserva.getModel();

        modelo.removeRow(fila);

    calcularTotalServicios();
    calcularTotalGeneral();
    }
}
private void calcularTotalServicios() {

    DefaultTableModel modelo =(DefaultTableModel) tbServiciosReserva.getModel();
    double total = 0;
    for (int i = 0; i < modelo.getRowCount(); i++) {
        double precio = Double.parseDouble(
         modelo.getValueAt(i, 2).toString());
        total += precio;
    }

    txtTotalServicios.setText("Q " + total);
}
private void calcularTotalGeneral() {

    if (cmboxCanchaDisponible.getSelectedItem() == null
            || txtPrecioTotalCancha.getText().trim().isEmpty()) {

        totalServiciosAdicionales.setText("Q 0.00");
        jblPrecioTotalCancha.setText("Q 0.00");
        jlbTotalReserva.setText("Q 0.00");

        calcularSaldoPendiente();

        return;
    }

    String textoCancha = txtPrecioTotalCancha.getText()
            .replace("Q", "").trim();

    double totalCancha = Double.parseDouble(textoCancha);

 
    DefaultTableModel modelo =
            (DefaultTableModel) tbServiciosReserva.getModel();

    double totalServicios = 0;

    for (int i = 0; i < modelo.getRowCount(); i++) {

        double precio = Double.parseDouble(
                modelo.getValueAt(i, 2).toString()
        );

        totalServicios += precio;
    }

   
    double totalGeneral = totalCancha + totalServicios;

    totalServiciosAdicionales.setText(
            String.format("Q %.2f", totalServicios));

    jblPrecioTotalCancha.setText(
            String.format("Q %.2f", totalCancha));

    jlbTotalReserva.setText(
            String.format("Q %.2f", totalGeneral));

    calcularSaldoPendiente(); 
}

 private void limpiarCanchasConsultadas() {

    cmboxCanchaDisponible.removeAllItems();
    txtPrecioHora.setText("");
    txtPrecioTotalCancha.setText("");
    calcularTotalGeneral();

}
 private void buscarClientes() {

    String texto = txtBuscarCliente.getText().trim();
    cmboxCliente.removeAllItems();

    if (texto.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingrese el nombre o código del cliente.");
        return;
    }

    Cliente[] clientes = gestorClientes.obtenerClientes();

    for (int i = 0; i < clientes.length; i++) {

     Cliente cliente = clientes[i];

     if (cliente != null && cliente.estaActivo()) {

            String codigo = cliente.getCodigo();
            String nombre = cliente.getNombre();
            String telefono = cliente.getTelefono();

            if (codigo.equalsIgnoreCase(texto)
                    || nombre.toLowerCase().contains(
                            texto.toLowerCase())) {

                cmboxCliente.addItem(  codigo + " - " + nombre + " - " + telefono);
            }
        }
    }

    if (cmboxCliente.getItemCount() == 0) {

        JOptionPane.showMessageDialog(this,
                "No se encontraron clientes.");
    }
}
 private LocalDate obtenerFechaReserva() {

    if (dateFechaReserva.getDate() == null) {
        JOptionPane.showMessageDialog(this,"Seleccione una fecha.");
        return null;
    }

    LocalDate fecha = dateFechaReserva.getDate()
            .toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();

    if (fecha.isBefore(LocalDate.now())) {
        JOptionPane.showMessageDialog(this,
                "No puede registrar reservas en fechas pasadas.");
        return null;
    }

    return fecha;
}
 private void actualizarHoraFinalizacion() {
     if (gestorReservas == null) {
    return;
}

    if (cmboxHoraInicio.getSelectedItem() == null
            || cmboxDuracionHoras.getSelectedItem() == null) {

        txtHoraFin.setText("");
        return;
    }

    LocalTime horaInicio = LocalTime.parse(cmboxHoraInicio.getSelectedItem().toString()
    );

    int duracionHoras =cmboxDuracionHoras.getSelectedIndex() + 1;

    LocalTime horaFin = gestorReservas.calcularHoraFin(horaInicio, duracionHoras
    );

    if (horaFin == null) {
        txtHoraFin.setText("Horario no válido");
        return;
    }

    txtHoraFin.setText(horaFin.toString());
}
 private void verificarFechaHoraSeleccionada() {
        if (gestorReservas == null) {
        return;
    }

    if (dateFechaReserva.getDate() == null || cmboxHoraInicio.getSelectedItem() == null) {
        return;
    }

    LocalDate fecha = dateFechaReserva.getDate()
            .toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();

    LocalTime horaInicio = LocalTime.parse(
            cmboxHoraInicio.getSelectedItem().toString()
    );

    if (!gestorReservas.validarFechaHoraReserva(
            fecha, horaInicio)) {

        JOptionPane.showMessageDialog(this,
                "La fecha y hora seleccionadas ya pasaron. "
                + "Seleccione otro horario.");

        cmboxHoraInicio.setSelectedIndex(-1);
        txtHoraFin.setText("");
        return;
    }

    actualizarHoraFinalizacion();
}

private void cargarHoras() {

    cmboxHoraInicio.removeAllItems();

    for (int hora = 8; hora <= 22; hora++) {
        cmboxHoraInicio.addItem(
                LocalTime.of(hora, 0).toString()
        );
    }

    cmboxHoraInicio.setSelectedIndex(-1);
}

private void cargarDuraciones() {

    cmboxDuracionHoras.removeAllItems();

    for (int i = 1; i <= 5; i++) {
        cmboxDuracionHoras.addItem(i + " horas");
    }

}
private void consultarCanchasDisponibles() {

    cmboxCanchaDisponible.removeAllItems();

    LocalDate fecha = obtenerFechaReserva();

    if (fecha == null) {
        return;
    }

    if (cmboxHoraInicio.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una hora de inicio.");
        return;
    }

    if (cmboxDuracionHoras.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una duración.");
        return;
    }

    LocalTime horaInicio = LocalTime.parse(
            cmboxHoraInicio.getSelectedItem().toString()
    );

    int duracionHoras =
            cmboxDuracionHoras.getSelectedIndex() + 1;

    if (!gestorReservas.validarFechaHoraReserva(
            fecha, horaInicio)) {

        JOptionPane.showMessageDialog(this,
                "La fecha y hora seleccionadas ya pasaron.");
        return;
    }

    LocalTime horaFin = gestorReservas.calcularHoraFin(
            horaInicio, duracionHoras
    );

    if (horaFin == null) {
        JOptionPane.showMessageDialog(this,
                "El horario de finalización no es válido.");
        return;
    }

    Cancha[] disponibles =
            gestorDisponibilidad.obtenerCanchasDisponibles(
                    fecha, horaInicio, horaFin
            );

    for (int i = 0; i < disponibles.length; i++) {

        Cancha cancha = disponibles[i];

        cmboxCanchaDisponible.addItem( cancha.getCodigo() + " - " + cancha.getNombre()+" - "+cancha.getTipo()
        );
    }
    cmboxCanchaDisponible.setSelectedIndex(-1);

    if (cmboxCanchaDisponible.getItemCount() == 0) {

        JOptionPane.showMessageDialog(this,
                "No hay canchas disponibles para este horario.");

    } else {

        JOptionPane.showMessageDialog(this,
                "Se encontraron "
                + cmboxCanchaDisponible.getItemCount()
                + " canchas disponibles.");
    }
}
private void calcularPrecioReserva() {

    if (dateFechaReserva.getDate() == null
            || cmboxHoraInicio.getSelectedItem() == null
            || cmboxDuracionHoras.getSelectedItem() == null
            || cmboxCanchaDisponible.getSelectedItem() == null) {

        txtPrecioHora.setText("");
        txtPrecioTotalCancha.setText("");
        calcularTotalGeneral();
        return;
    }

    LocalDate fecha = dateFechaReserva.getDate()
            .toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();

    LocalTime horaInicio = LocalTime.parse(
            cmboxHoraInicio.getSelectedItem().toString());

    int duracionHoras = cmboxDuracionHoras.getSelectedIndex() + 1;

    if (!gestorReservas.validarFechaHoraReserva(fecha, horaInicio)
            || gestorReservas.calcularHoraFin(
                    horaInicio, duracionHoras) == null) {

        txtPrecioHora.setText("");
        txtPrecioTotalCancha.setText("");
        calcularTotalGeneral();
        return;
    }

    double precioHora = tarifa.obtenerPrecioHora(
            fecha, horaInicio);

    double precioTotal = tarifa.calcularTotal(
            fecha, horaInicio, duracionHoras);

    if (precioHora < 0 || precioTotal < 0) {

        txtPrecioHora.setText("");
        txtPrecioTotalCancha.setText("");
        calcularTotalGeneral();
        return;
    }

    txtPrecioHora.setText("Q " + precioHora);
    txtPrecioTotalCancha.setText("Q " + precioTotal);

    // Actualizar el total general
    calcularTotalGeneral();
}
private double obtenerAnticipo() {

    String texto = txtAnticipo.getText().trim();

    if (texto.isEmpty()) {
        return 0;
    }

    try {
        return Double.parseDouble(texto);
    } catch (NumberFormatException e) {
        return -1;
    }
}
private MetodoPago obtenerMetodoPago() {

    int opcion = cmboxMetodoPago.getSelectedIndex();

    if (opcion == 0) {
        return MetodoPago.EFECTIVO;
    } else if (opcion == 1) {
        return MetodoPago.TRANSFERENCIA;
    } else if (opcion == 2) {
        return MetodoPago.TARJETA_DEBITO;
    } else if (opcion == 3) {
        return MetodoPago.TARJETA_CREDITO;
    }

    return null;
}
private ServicioAdicional[] obtenerServiciosSeleccionados() {

    DefaultTableModel modelo =
            (DefaultTableModel) tbServiciosReserva.getModel();

    ServicioAdicional[] servicios =
            new ServicioAdicional[modelo.getRowCount()];

    for (int i = 0; i < modelo.getRowCount(); i++) {

        String codigo = modelo.getValueAt(i, 0).toString();

        servicios[i] = gestorServicios.buscarServicio(codigo);
    }

    return servicios;
}
private void calcularSaldoPendiente() {

    String textoTotal = jlbTotalReserva.getText()
            .replace("Q", "").trim();

    double totalReserva;

    try {
        totalReserva = Double.parseDouble(textoTotal);
    } catch (NumberFormatException e) {
        lblSaldoPendiente.setText("Q 0.00");
        return;
    }

    double anticipo = 0;

    if (!txtAnticipo.getText().trim().isEmpty()) {

        try {
            anticipo = Double.parseDouble(
                    txtAnticipo.getText().trim());
        } catch (NumberFormatException e) {
            lblSaldoPendiente.setText("Anticipo inválido");
            return;
        }
    }

    if (!Double.isFinite(anticipo)
            || anticipo < 0
            || anticipo > totalReserva) {

        lblSaldoPendiente.setText("Anticipo inválido");
        return;
    }

    double saldo = totalReserva - anticipo;

    lblSaldoPendiente.setText(
            String.format("Q %.2f", saldo));
}private void registrarReserva() {

    if (cmboxCliente.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione un cliente.");
        return;
    }

    if (cmboxCanchaDisponible.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una cancha disponible.");
        return;
    }

    LocalDate fecha = obtenerFechaReserva();

    if (fecha == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una fecha válida.");
        return;
    }

    if (cmboxHoraInicio.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una hora de inicio.");
        return;
    }

    if (cmboxDuracionHoras.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this,
                "Seleccione la duración de la reserva.");
        return;
    }
      String codigoCliente = cmboxCliente.getSelectedItem().toString().split(" - ")[0];

      String codigoCancha = cmboxCanchaDisponible.getSelectedItem().toString().split(" - ")[0];

      LocalTime horaInicio = LocalTime.parse(
        cmboxHoraInicio.getSelectedItem().toString());

     int duracionHoras = cmboxDuracionHoras.getSelectedIndex() + 1;
     ServicioAdicional[] serviciosSeleccionados =
     obtenerServiciosSeleccionados();
     double anticipo;

    try { anticipo = Double.parseDouble(txtAnticipo.getText().trim());

    } catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this,
            "Ingrese un anticipo válido.");
    return;
}

    calcularTotalGeneral();
    double totalReserva = Double.parseDouble(
    jlbTotalReserva.getText().replace("Q", "").trim());
    double anticipoMinimo = totalReserva * 0.30;

    if (!Double.isFinite(totalReserva) || totalReserva <= 0) {
    JOptionPane.showMessageDialog(this,
            "El total de la reserva no es válido.");
    return;
}

     if (!Double.isFinite(anticipo)
        || anticipo < anticipoMinimo
        || anticipo > totalReserva) {

    JOptionPane.showMessageDialog(this,
            "El anticipo debe ser como mínimo el 30%.\n"
            + "Anticipo mínimo: Q " + anticipoMinimo);
    return;
}     MetodoPago metodoPago = obtenerMetodoPago();

     if (metodoPago == null) { JOptionPane.showMessageDialog(this,
      "Seleccione un método de pago.");
    return;
    }
       Reserva reserva = gestorReservas.agregarReserva(
        codigoCliente,
        codigoCancha,
        fecha,
        horaInicio,
        duracionHoras,
        gestorDisponibilidad,
        gestorClientes,
        gestorCanchas,
        serviciosSeleccionados
);

// VERIFICAR SI SE REGISTRÓ CORRECTAMENTE
if (reserva == null) {
    JOptionPane.showMessageDialog(this,
            "No se pudo registrar la reserva.\n"
            + "Verifique los datos y la disponibilidad.");
    return; 
    
}   Pago pago = gestorPagos.registrarPago(
     reserva.getCodigo(),
     anticipo,
     metodoPago,
    "Anticipo inicial de reserva"
);


if (pago == null) {
    JOptionPane.showMessageDialog(this,
            "No se pudo registrar el anticipo.\n"
            + "La reserva quedó en memoria sin pago.\n"
            + "No guarde los cambios todavía.");
    return;
}
        JOptionPane.showMessageDialog(this,
        "Reserva registrada correctamente.\n\n"
        + "Código de reserva: " + reserva.getCodigo()
        + "\nTotal de reserva: Q " + reserva.getTotal()
        + "\nAnticipo pagado: Q " + reserva.getTotalPagado()
        + "\nSaldo pendiente: Q " + reserva.calcularSaldo()
        + "\nEstado: " + reserva.getEstado()
        + "\n\nRecuerde presionar Guardar Cambios.");

}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmboxDuracionHoras = new javax.swing.JComboBox<>();
        cmboxHoraInicio = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtHoraFin = new javax.swing.JTextField();
        dateFechaReserva = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jblTotalGeneral = new javax.swing.JLabel();
        totalServiciosAdicionales = new javax.swing.JLabel();
        jblPrecioTotalCancha = new javax.swing.JLabel();
        jlbTotalReserva = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscarCliente = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        cmboxCliente = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cmboxServicio = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnAgregarServicio = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbServiciosReserva = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtTotalServicios = new javax.swing.JTextField();
        btnquitarServicio = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        cmboxCanchaDisponible = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtPrecioHora = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtPrecioTotalCancha = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtAnticipo = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cmboxMetodoPago = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        lblSaldoPendiente = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel1.setBackground(new java.awt.Color(46, 125, 50));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(241, 248, 233));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Reserva"));

        jLabel4.setText("Fecha");

        jLabel5.setText("Duración ");

        cmboxDuracionHoras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 hora", "2 horas", "3 horas", "4 horas", "5 horas" }));
        cmboxDuracionHoras.addActionListener(this::cmboxDuracionHorasActionPerformed);

        cmboxHoraInicio.addActionListener(this::cmboxHoraInicioActionPerformed);

        jLabel9.setText("Hora de Inicio");

        jLabel13.setText("Hora Finalización");

        txtHoraFin.setEditable(false);
        txtHoraFin.setText("jTextField3");

        dateFechaReserva.addPropertyChangeListener(this::dateFechaReservaPropertyChange);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(dateFechaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cmboxHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(cmboxDuracionHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmboxDuracionHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateFechaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmboxHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumen de la Reserva"));

        jLabel7.setText("Precio total de Cancha");

        jLabel8.setText("Servicio Adicionales");

        jblTotalGeneral.setText("Total de la Reserva");

        totalServiciosAdicionales.setText("Q");

        jblPrecioTotalCancha.setText("Q");

        jlbTotalReserva.setText("Q.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jblTotalGeneral)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbTotalReserva)
                            .addComponent(jblPrecioTotalCancha)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(47, 47, 47)
                        .addComponent(totalServiciosAdicionales)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(totalServiciosAdicionales))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jblPrecioTotalCancha))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jblTotalGeneral))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbTotalReserva)
                        .addGap(1, 1, 1)))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Cliente"));

        btnBuscarCliente.setText("Buscar Cliente");
        btnBuscarCliente.addActionListener(this::btnBuscarClienteActionPerformed);

        jButton2.setText("Limpiar Busqueda");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jLabel2.setText("Buscar cliente por nombre o codigo");

        jLabel3.setText("Cliente Seleccionado");

        jButton7.setText("Agregar Cliente");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmboxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmboxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        cmboxServicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Seleccionar un servicio");

        btnAgregarServicio.setText("Agregar el Servicio");
        btnAgregarServicio.addActionListener(this::btnAgregarServicioActionPerformed);

        tbServiciosReserva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código ", "Servicio", "Precio"
            }
        ));
        jScrollPane1.setViewportView(tbServiciosReserva);

        jLabel11.setText("Total Servicio");

        btnquitarServicio.setText("Quitar Servicio");
        btnquitarServicio.addActionListener(this::btnquitarServicioActionPerformed);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(cmboxServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnAgregarServicio)
                        .addGap(61, 61, 61)
                        .addComponent(btnquitarServicio))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmboxServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarServicio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnquitarServicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel6.setText("Cancha Disponible");

        jButton3.setText("Consultar Disponibilidad");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        cmboxCanchaDisponible.addActionListener(this::cmboxCanchaDisponibleActionPerformed);

        jLabel12.setText("Precio por hora");

        txtPrecioHora.addActionListener(this::txtPrecioHoraActionPerformed);

        jLabel15.setText("Precio total de Cancha");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecioHora)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(txtPrecioTotalCancha, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(cmboxCanchaDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(66, 66, 66))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmboxCanchaDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrecioHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioTotalCancha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton4.setText("REGISTRAR RESERVA");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Limpiar");

        txtAnticipo.addActionListener(this::txtAnticipoActionPerformed);

        jLabel20.setText("Anticipo");

        jLabel21.setText("Metodo de Pago");

        cmboxMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EFECTIVO", "TRANSFERENCIA", "TARJETA_DEBITO", "TARJETA_CREDITO" }));

        jLabel14.setText("Saldo Pendiente");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel14)
                        .addGap(30, 30, 30)
                        .addComponent(lblSaldoPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addGap(0, 62, Short.MAX_VALUE))))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtAnticipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmboxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSaldoPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmboxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel23))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jButton1.setText("GUADAR CAMBIOS");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(868, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(1032, 1032, 1032))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton5)
                            .addComponent(jButton4)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(6089, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("NUEVA RESERVA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(711, 711, 711)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 2057, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrecioHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioHoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioHoraActionPerformed

    private void cmboxHoraInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxHoraInicioActionPerformed
     verificarFechaHoraSeleccionada(); 
     limpiarCanchasConsultadas();  
     calcularPrecioReserva();// TODO add your handling code here:
    }//GEN-LAST:event_cmboxHoraInicioActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    if (getDesktopPane() == null) {
        JOptionPane.showMessageDialog(this,
                "No se puede abrir el formulario de clientes.");
        return;
    }

    JInternalFrm_Cliente ventana =new JInternalFrm_Cliente(gestorClientes);

    getDesktopPane().add(ventana);
    ventana.setVisible(true);
    ventana.toFront();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
    buscarClientes();        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     txtBuscarCliente.setText("");
     cmboxCliente.removeAllItems();// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmboxDuracionHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxDuracionHorasActionPerformed
    actualizarHoraFinalizacion(); 
    limpiarCanchasConsultadas();// TODO add your handling code here:
    calcularPrecioReserva();
    }//GEN-LAST:event_cmboxDuracionHorasActionPerformed

    private void dateFechaReservaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateFechaReservaPropertyChange
     if ("date".equals(evt.getPropertyName())) {
     verificarFechaHoraSeleccionada();
        limpiarCanchasConsultadas();
        calcularPrecioReserva();

    }   // TODO add your handling code here:
    }//GEN-LAST:event_dateFechaReservaPropertyChange

    private void cmboxCanchaDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxCanchaDisponibleActionPerformed
    calcularPrecioReserva();       
    }//GEN-LAST:event_cmboxCanchaDisponibleActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       consultarCanchasDisponibles();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAgregarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarServicioActionPerformed
     agregarServicioTabla();        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarServicioActionPerformed

    private void btnquitarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnquitarServicioActionPerformed
        // TODO add your handling code here:
        quitarServicioTabla();
    }//GEN-LAST:event_btnquitarServicioActionPerformed

    private void txtAnticipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnticipoActionPerformed
    calcularSaldoPendiente();        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnticipoActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        registrarReserva();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
boolean reservasGuardadas = gestorReservas.guardarReservas();


boolean pagosGuardados = gestorPagos.guardarPagos();

if (reservasGuardadas && pagosGuardados) {

    JOptionPane.showMessageDialog(this,
            "Reservas y pagos guardados correctamente.");

} else {

    JOptionPane.showMessageDialog(this,
            "Ocurrió un error al guardar los cambios.\n"
            + "Verifique los archivos CSV.");
}    
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarServicio;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnquitarServicio;
    private javax.swing.JComboBox<String> cmboxCanchaDisponible;
    private javax.swing.JComboBox<String> cmboxCliente;
    private javax.swing.JComboBox<String> cmboxDuracionHoras;
    private javax.swing.JComboBox<String> cmboxHoraInicio;
    private javax.swing.JComboBox<String> cmboxMetodoPago;
    private javax.swing.JComboBox<String> cmboxServicio;
    private com.toedter.calendar.JDateChooser dateFechaReserva;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jblPrecioTotalCancha;
    private javax.swing.JLabel jblTotalGeneral;
    private javax.swing.JLabel jlbTotalReserva;
    private javax.swing.JLabel lblSaldoPendiente;
    private javax.swing.JTable tbServiciosReserva;
    private javax.swing.JLabel totalServiciosAdicionales;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtHoraFin;
    private javax.swing.JTextField txtPrecioHora;
    private javax.swing.JTextField txtPrecioTotalCancha;
    private javax.swing.JTextField txtTotalServicios;
    // End of variables declaration//GEN-END:variables
}
