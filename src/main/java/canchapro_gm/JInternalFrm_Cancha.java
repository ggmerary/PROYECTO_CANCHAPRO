/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package canchapro_gm;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JInternalFrm_Cancha extends javax.swing.JInternalFrame {
private GestorCanchas gestorCanchas;
private boolean cambiosPendientes = false;
private boolean editandoDatos = false;
private boolean editandoEstado = false;
    
public JInternalFrm_Cancha(GestorCanchas gestorCanchas) {
        initComponents();
        cmboxHoraApertura.setSelectedItem("08");
        cmboxHoraCierre.setSelectedItem("23");
        this.gestorCanchas=gestorCanchas;
        txtCodigo.setEditable(false);
        btnModificar.setEnabled(false);
        btnCambiarEstado.setEnabled(false);
       

    mostrarCanchas();
    }
private void mostrarCanchas() {

    DefaultTableModel modelo = (DefaultTableModel) tbCanchas.getModel();

    modelo.setRowCount(0);

    Cancha[] canchas = gestorCanchas.obtenerCanchas();

    for (int i = 0; i < gestorCanchas.getCantidadCanchas(); i++) {

        Cancha cancha = canchas[i];

        Object[] fila = {
            cancha.getCodigo(),
            cancha.getNombre(),
            cancha.getTipo(),
            cancha.getCapacidad() + " jugadores",
            cancha.getHoraApertura(),
            cancha.getHoraCierre(),
            cancha.getEstado()
        };

        modelo.addRow(fila);
    }
}
private void cargarDatosCancha() {

    String codigo = txtCodigo.getText();

    Cancha cancha = gestorCanchas.buscarCanchaPorCodigo(codigo);

    if (cancha != null) {

        txtNombre.setText(cancha.getNombre());
        cmboxTipo.setSelectedItem(cancha.getTipo());

         txtCapacidad.setText(String.valueOf(cancha.getCapacidad())
        );

        int horaA = cancha.getHoraApertura().getHour();
        int horaC = cancha.getHoraCierre().getHour();


        if (horaA < 10) {
        cmboxHoraApertura.setSelectedItem("0" + horaA);
       } else {
       cmboxHoraApertura.setSelectedItem("" + horaA);
       }

      if (horaC < 10) {
      cmboxHoraCierre.setSelectedItem("0" + horaC);
      } else {
      cmboxHoraCierre.setSelectedItem("" + horaC);
       }
        cmboxEstado.setSelectedItem(cancha.getEstado());
    }
}
private void actualizarCapacidad() {

    String tipo = cmboxTipo.getSelectedItem().toString();

    if (tipo.equals("Fútbol 5")) {
        txtCapacidad.setText("10");

    } else if (tipo.equals("Fútbol 7")) {
        txtCapacidad.setText("14");

    } else if (tipo.equals("Fútbol 11")) {
        txtCapacidad.setText("22");
    }
}
private boolean validarHorarioCancha(LocalTime apertura,
                                    LocalTime cierre) {

    LocalTime horaMinima = LocalTime.of(8, 0);
    LocalTime horaMaxima = LocalTime.of(23, 0);

    if (apertura.isBefore(horaMinima)) {

        JOptionPane.showMessageDialog(this,
                "La cancha no puede abrir antes de las 08:00.");
        return false;
    }

    if (cierre.isAfter(horaMaxima)) {

        JOptionPane.showMessageDialog(this,
                "La cancha no puede cerrar después de las 23:00.");
        return false;
    }

    if (!cierre.isAfter(apertura)) {

        JOptionPane.showMessageDialog(this,
                "La hora de cierre debe ser posterior a la apertura.");
        return false;
    }

    return true;
}

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelCancha = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        cmboxTipo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCanchas = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnModificar = new javax.swing.JButton();
        btnCambiarEstado = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnNuevaCancha = new javax.swing.JButton();
        btnRegistrarCancha = new javax.swing.JButton();
        btnGuardarCambios = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmboxHoraApertura = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmboxHoraCierre = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmboxEstado = new javax.swing.JComboBox<>();
        txtCapacidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(241, 248, 233));
        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jpanelCancha.setBackground(new java.awt.Color(241, 248, 233));

        txtCodigo.setBackground(new java.awt.Color(255, 153, 153));

        cmboxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fútbol 5", "Fútbol 7", "Fútbol 11" }));
        cmboxTipo.addActionListener(this::cmboxTipoActionPerformed);

        tbCanchas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Tipo", "Capacidad", "Apertura", "Cierre", "Estado"
            }
        ));
        tbCanchas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCanchasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCanchas);

        jPanel1.setBackground(new java.awt.Color(129, 199, 132));

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnCambiarEstado.setText("Cambiar Estado");
        btnCambiarEstado.addActionListener(this::btnCambiarEstadoActionPerformed);

        btnLimpiar.setText("Limpiar ");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnNuevaCancha.setText("Nueva Cancha(Limpiar)");
        btnNuevaCancha.addActionListener(this::btnNuevaCanchaActionPerformed);

        btnRegistrarCancha.setText("Registrar");
        btnRegistrarCancha.addActionListener(this::btnRegistrarCanchaActionPerformed);

        btnGuardarCambios.setBackground(new java.awt.Color(255, 102, 102));
        btnGuardarCambios.setText("Guardar Cambios");
        btnGuardarCambios.addActionListener(this::btnGuardarCambiosActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnModificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addComponent(btnCambiarEstado))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLimpiar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardarCambios))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNuevaCancha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrarCancha)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevaCancha)
                    .addComponent(btnRegistrarCancha))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnCambiarEstado))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardarCambios)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLimpiar)
                        .addGap(35, 35, 35))))
        );

        jLabel1.setText("Código");

        jLabel2.setText("Nombre");

        jLabel3.setText("Tipo");

        jLabel4.setText("Capacidad");

        jLabel5.setText("Apertura");

        jLabel6.setText("Cierre");

        cmboxHoraApertura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", " " }));

        jLabel8.setText("Hora");

        jLabel11.setText("Hora");

        cmboxHoraCierre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(46, 125, 50));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Estado");

        cmboxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "Mantenimiento", "Inactiva" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addGap(35, 35, 35)
                .addComponent(cmboxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmboxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        txtCapacidad.setEditable(false);
        txtCapacidad.addActionListener(this::txtCapacidadActionPerformed);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setText("GESTION CANCHAS");

        javax.swing.GroupLayout jpanelCanchaLayout = new javax.swing.GroupLayout(jpanelCancha);
        jpanelCancha.setLayout(jpanelCanchaLayout);
        jpanelCanchaLayout.setHorizontalGroup(
            jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelCanchaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jpanelCanchaLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelCanchaLayout.createSequentialGroup()
                        .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpanelCanchaLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(54, 54, 54)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addGroup(jpanelCanchaLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(60, 60, 60)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpanelCanchaLayout.createSequentialGroup()
                                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmboxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(752, Short.MAX_VALUE))
                    .addGroup(jpanelCanchaLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpanelCanchaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                            .addGroup(jpanelCanchaLayout.createSequentialGroup()
                                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpanelCanchaLayout.createSequentialGroup()
                                        .addGap(238, 238, 238)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpanelCanchaLayout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmboxHoraCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelCanchaLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmboxHoraApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(208, 208, 208)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182))))
        );
        jpanelCanchaLayout.setVerticalGroup(
            jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelCanchaLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelCanchaLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelCanchaLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(21, 21, 21)
                        .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(15, 15, 15)))
                .addGap(15, 15, 15)
                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmboxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmboxHoraApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(29, 29, 29)
                .addGroup(jpanelCanchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(5, 5, 5)
                .addComponent(cmboxHoraCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelCanchaLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanelCancha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelCancha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbCanchasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCanchasMouseClicked
        // TODO add your handling code here:
    int fila = tbCanchas.getSelectedRow();

    if (fila >= 0) {

        String codigo = tbCanchas.getValueAt(fila, 0).toString();
        txtCodigo.setText(codigo);
         cargarDatosCancha();
        
         btnRegistrarCancha.setEnabled(false);

        btnModificar.setEnabled(true);
        btnCambiarEstado.setEnabled(true);
    }
    }//GEN-LAST:event_tbCanchasMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
     actualizarCapacidad();
    String codigo = txtCodigo.getText();
    String nombre = txtNombre.getText().trim();
    String tipo = cmboxTipo.getSelectedItem().toString();

 

    try {
            int capacidad = Integer.parseInt(
            txtCapacidad.getText().trim() );

        int horaApertura = Integer.parseInt(
                cmboxHoraApertura.getSelectedItem().toString());

        int horaCierre = Integer.parseInt(
                cmboxHoraCierre.getSelectedItem().toString());

        LocalTime apertura = LocalTime.of(horaApertura, 0);
        LocalTime cierre = LocalTime.of(horaCierre, 0);
        
        if (!validarHorarioCancha(apertura, cierre)) {
        return;
}

        Cancha cancha = gestorCanchas.buscarCanchaPorCodigo(codigo);

        if (cancha == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró la cancha.");
            return;
        }

        boolean modificado = gestorCanchas.modificarCancha(
                codigo,
                nombre,
                tipo,
                capacidad,
                apertura,
                cierre
        );

        if (modificado) {
             cambiosPendientes = true;

            JOptionPane.showMessageDialog(this,
                    "Cancha modificada correctamente.");

            mostrarCanchas();

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo modificar la cancha.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "Verifique los datos ingresados.");
    }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCambiarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarEstadoActionPerformed
    String codigo = txtCodigo.getText().trim();
    String nuevoEstado = cmboxEstado.getSelectedItem().toString();

    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una cancha de la tabla.");
        return;
    }

    boolean cambiado = gestorCanchas.cambiarEstado( codigo, nuevoEstado);

    if (cambiado) {
        cambiosPendientes = true;
       JOptionPane.showMessageDialog(this,"Estado de la cancha actualizado correctamente.");

        mostrarCanchas();

    } else {

        JOptionPane.showMessageDialog(this,
                "No se pudo cambiar el estado de la cancha.");
    }
    }//GEN-LAST:event_btnCambiarEstadoActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        // TODO add your handling code here:

    boolean guardado = gestorCanchas.guardarCanchas();

    if (guardado) {JOptionPane.showMessageDialog(this, "Los cambios se guardaron correctamente.");

        cambiosPendientes = false;

    } else {

        JOptionPane.showMessageDialog(this,
                "No se pudieron guardar los cambios.");
    }
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        if (cambiosPendientes) {

        Object[] opciones = {  "Guardar", "No guardar", "Cancelar"};

        int respuesta = JOptionPane.showOptionDialog(
                this,
                "¿Desea guardar los cambios antes de cerrar?",
                "Cambios pendientes",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (respuesta == 0) {

            boolean guardado = gestorCanchas.guardarCanchas();

            if (guardado) {
                cambiosPendientes = false;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudieron guardar los cambios.");
            }

        } else if (respuesta == 1) {

            gestorCanchas.cargarDatos();
            cambiosPendientes = false;
            dispose();
        }
        } else {
        dispose();
       }
    }//GEN-LAST:event_formInternalFrameClosing

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
    txtCodigo.setText("");
    txtNombre.setText("");
   
    cmboxTipo.setSelectedIndex(0);
    txtCapacidad.setText("");
   
    cmboxHoraApertura.setSelectedItem("08");
    cmboxHoraCierre.setSelectedItem("23");

    cmboxEstado.setSelectedIndex(0);

    tbCanchas.clearSelection();

    btnModificar.setEnabled(false);
    btnCambiarEstado.setEnabled(false);
    btnRegistrarCancha.setEnabled(true);
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtCapacidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCapacidadActionPerformed
      String tipo = cmboxTipo.getSelectedItem().toString();

   if (tipo.equals("Fútbol 5")) {
    txtCapacidad.setText("10");

   } else if (tipo.equals("Fútbol 7")) {
    txtCapacidad.setText("14");

   } else if (tipo.equals("Fútbol 11")) {
    txtCapacidad.setText("22");
   }  // TODO add your handling code here:
    }//GEN-LAST:event_txtCapacidadActionPerformed

    private void cmboxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxTipoActionPerformed
       actualizarCapacidad();
    }//GEN-LAST:event_cmboxTipoActionPerformed

    private void btnRegistrarCanchaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarCanchaActionPerformed

    String nombre = txtNombre.getText().trim();

    String tipo = cmboxTipo.getSelectedItem().toString();

    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingrese el nombre de la cancha.");
        return;
    }

    try {

        actualizarCapacidad();

        int capacidad = Integer.parseInt( txtCapacidad.getText().trim() );

       int horaApertura = Integer.parseInt(
        cmboxHoraApertura.getSelectedItem().toString());

      int horaCierre = Integer.parseInt(
        cmboxHoraCierre.getSelectedItem().toString());

       LocalTime apertura = LocalTime.of(horaApertura, 0);
       LocalTime cierre = LocalTime.of(horaCierre, 0);

       if (!validarHorarioCancha(apertura, cierre)) {
       return;
}

        Cancha nuevaCancha = gestorCanchas.agregarCancha(
                nombre,
                tipo,
                capacidad,
                apertura,
                cierre
        );

        if (nuevaCancha != null) {

            cambiosPendientes = true;

            JOptionPane.showMessageDialog(this,
                    "Cancha registrada correctamente.\n"
                    + "Código: " + nuevaCancha.getCodigo());

            mostrarCanchas();

            txtCodigo.setText(nuevaCancha.getCodigo());

            btnRegistrarCancha.setEnabled(false);

        } else {

            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la cancha.");
        }

   } catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al registrar la cancha. Verifique los datos.");
    }// TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarCanchaActionPerformed

    private void btnNuevaCanchaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCanchaActionPerformed
       txtCodigo.setText(gestorCanchas.generarCodigoCancha());

txtNombre.setText("");

cmboxTipo.setSelectedItem("Fútbol 5");
actualizarCapacidad();

cmboxHoraApertura.setSelectedItem("08");
cmboxHoraCierre.setSelectedItem("23");

cmboxEstado.setSelectedItem("Disponible");

tbCanchas.clearSelection();

btnRegistrarCancha.setEnabled(true);
btnModificar.setEnabled(false);
btnCambiarEstado.setEnabled(false);
    }//GEN-LAST:event_btnNuevaCanchaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarEstado;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaCancha;
    private javax.swing.JButton btnRegistrarCancha;
    private javax.swing.JComboBox<String> cmboxEstado;
    private javax.swing.JComboBox<String> cmboxHoraApertura;
    private javax.swing.JComboBox<String> cmboxHoraCierre;
    private javax.swing.JComboBox<String> cmboxTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpanelCancha;
    private javax.swing.JTable tbCanchas;
    private javax.swing.JTextField txtCapacidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}

