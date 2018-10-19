/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PANTALLAS;

import CLASES.DataUser;
import CLASES.ModuloMySQL;
import CLASES.OrdenesTrabajo;
import CLASES.ValoresEstaticos;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erobles
 */
public class CierreOrdenTrabajo extends javax.swing.JFrame {

    /**
     * Creates new form CreacionOrdenTrabajo
     * @param consecutivo
     */
    public CierreOrdenTrabajo(int consecutivo) {
       
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();   
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        jLabelVersion16.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
        ordentrabajo=consecutivo;
        jTitulo.setText("ORDEN DE TRABAJO # "+ordentrabajo);
        getDataOrden();
         
        
        setListeners();
       // //ValoresEstaticos.espere.setVisible(false);
        
        
        
        
    }
    private void getDataOrden(){
        new Thread(){
            @Override
            public void run(){
                try {
            ResultSet rs=modulo.Listar("Select * from Ordenes_Trabajo where Id_OrdenTrabajo='"+ordentrabajo+"'");
            if (rs.first()){
                SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
                Date d=rs.getDate("Fecha");
                jUnidad.setText(String.valueOf(rs.getInt("Id_Bus")));
                jFecha.setText(df.format(d));
                getTrabajosOrden();
                //ValoresEstaticos.ocultaMensaje();
            }
        } catch (SQLException ex) {
            ValoresEstaticos.ocultaMensaje();
            Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        }.start();
        ValoresEstaticos.muestreMensajeEsperar("Recuperando datos de la orden de trabajo");
    }
    private void setListeners(){
        jTrabajos.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange()==ItemEvent.SELECTED){
                        try {
                            int index=jTrabajos.getSelectedIndex()+1;
                            trabajos_realizar.first();
                            trabajos_realizar.absolute(index);
                            idTrabajo=trabajos_realizar.getInt("Id_SubMantenimiento");
                            if (idEmpleado!=trabajos_realizar.getInt("Id_Empleado")){
                            getListEmpleados(trabajos_realizar.getInt("Id_Empleado"));}
                        } catch (SQLException ex) {
                            Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                        }}
                    }
                });
    }
    private void getTrabajosOrden(){
        jTrabajos.removeAllItems();
        new Thread(){
            @Override
            public void run(){
                 trabajos_realizar=modulo.Listar("SELECT\n" +
"     SubOrdenes_Trabajo.`Id_SubMantenimiento`,\n" +
"     SubOrdenes_Trabajo.`Id_OrdenTrabajo`,\n" +
"     SubOrdenes_Trabajo.`Id_Empleado` ,\n" +
"     SubOrdenes_Trabajo.`Id_TrabajoRealizado` ,\n" +
"     SubOrdenes_Trabajo.`HoraInicio` ,\n" +
"     SubOrdenes_Trabajo.`HoraFin` ,\n" +
"     SubOrdenes_Trabajo.`Tiempo_Invertido` ,\n" +
"     Sub_Mantenimiento.`Descripcion`\n" +
"FROM\n" +
"     Sub_Mantenimiento INNER JOIN SubOrdenes_Trabajo ON Sub_Mantenimiento.`ID_Submantenimiento` = SubOrdenes_Trabajo.`Id_TrabajoRealizado` where Id_OrdenTrabajo='"+ordentrabajo+"' and Estado='1'");
        if (trabajos_realizar!=null){
            try {
                while (trabajos_realizar.next()){
                    jTrabajos.addItem(trabajos_realizar.getString("Descripcion"));
                }
                
                if (trabajos_realizar.first()){
                idTrabajo=trabajos_realizar.getInt("Id_SubMantenimiento");
                    getListEmpleados(trabajos_realizar.getInt("Id_Empleado"));}
                else{
                   String s=modulo.Ejecutar("update Ordenes_Trabajo set IdEstado='3' where Id_OrdenTrabajo='"+ordentrabajo+"'" );
                   if (s.contains("concluyó")){
                       ValoresEstaticos.ocultaMensaje();
                       JOptionPane.showMessageDialog(rootPane, "Orden de trabajo "+ordentrabajo+" Cerrada","Sistema de Control de Indicadores",JOptionPane.INFORMATION_MESSAGE);
                       java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new ListadoOrdenesTrabajo().setVisible(true);
                        }
                        });
                        CierreOrdenTrabajo.this.dispose();
                   }
                }
                
                
            } catch (SQLException ex) {
                ValoresEstaticos.ocultaMensaje();
                Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            }
        }.start();
      // ValoresEstaticos.muestreMensajeEsperar("Recuperando trabajos asignados a la orden");
    }
    
   
    private void getListEmpleados(final int i){
        new Thread(){
            @Override
            public void run(){
                try {
                    jEmpleado.removeAllItems();
            idPuesto=i;
            result_empleados=modulo.Listar("Select Id_Empleado, Nombre from Empleado where Id_Empleado='"+i+"' order by Nombre");
            if (result_empleados.isBeforeFirst()){
                try {
                    while (result_empleados.next()){
                        jEmpleado.addItem(result_empleados.getString("Nombre"));
                    }
                    
                    result_empleados.first();
                    idEmpleado=result_empleados.getInt("Id_Empleado");
                    nombreempleado=result_empleados.getString("Nombre");
                    ValoresEstaticos.ocultaMensaje();
                } catch (SQLException ex) {
                    ValoresEstaticos.ocultaMensaje();
                    Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ValoresEstaticos.ocultaMensaje();
        } catch (SQLException ex) {
            ValoresEstaticos.ocultaMensaje();
            Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        }.start();
        //ValoresEstaticos.muestreMensajeEsperar("Recuperando empleado responsable del trabajo");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTitulo = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jFecha = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jAgregar1 = new javax.swing.JButton();
        jAgregar2 = new javax.swing.JButton();
        jUnidad = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jEmpleado = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTrabajos = new javax.swing.JComboBox();
        jAgregar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jhoraFinTrabajo = new javax.swing.JFormattedTextField();
        jHoraInicioTrabajo = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jFechaInicioTrabajo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jFechaFinTrabajo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabelVersion16 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CIERRE DE ORDEN DE TRABAJO");

        jTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitulo.setText("ORDEN DE TRABAJO # "+ordentrabajo);

        jButton11.setBackground(new java.awt.Color(51, 51, 51));
        jButton11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/P.png"))); // NOI18N
        jButton11.setText("anel Principal");
        jButton11.setBorder(null);
        jButton11.setFocusable(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/SCI.png"))); // NOI18N
        jLabel6.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 898, Short.MAX_VALUE)
                    .addComponent(jTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton11)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTitulo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jFecha.setEditable(false);
        jFecha.setBackground(new java.awt.Color(51, 51, 51));
        jFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jFecha.setForeground(new java.awt.Color(255, 153, 0));
        jFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFechaMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FECHA");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("UNIDAD");

        jLabel11.setBackground(new java.awt.Color(255, 102, 0));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("DATOS BASICOS DE LA ORDEN DE TRABAJO");
        jLabel11.setOpaque(true);

        jAgregar1.setBackground(new java.awt.Color(51, 51, 51));
        jAgregar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jAgregar1.setForeground(new java.awt.Color(255, 255, 255));
        jAgregar1.setText("NUEVA");
        jAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAgregar1ActionPerformed(evt);
            }
        });

        jAgregar2.setBackground(new java.awt.Color(51, 51, 51));
        jAgregar2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jAgregar2.setForeground(new java.awt.Color(255, 255, 255));
        jAgregar2.setText("LISTADO");
        jAgregar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAgregar2ActionPerformed(evt);
            }
        });

        jUnidad.setEditable(false);
        jUnidad.setBackground(new java.awt.Color(51, 51, 51));
        jUnidad.setForeground(new java.awt.Color(255, 153, 0));
        jUnidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm"))));
        jUnidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jUnidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFecha)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jUnidad)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jAgregar2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jAgregar2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jEmpleado.setBackground(new java.awt.Color(102, 102, 102));
        jEmpleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione un trabajo" }));
        jEmpleado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Empleado");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Trabajo a Realizar");

        jTrabajos.setBackground(new java.awt.Color(102, 102, 102));
        jTrabajos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTrabajos.setForeground(new java.awt.Color(255, 255, 255));
        jTrabajos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jAgregar.setBackground(new java.awt.Color(51, 51, 51));
        jAgregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jAgregar.setText("CERRAR TRABAJO");
        jAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAgregarActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 102, 0));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("INGRESO DE TRABAJOS A REALIZAR");
        jLabel14.setOpaque(true);

        jhoraFinTrabajo.setBackground(new java.awt.Color(51, 51, 51));
        jhoraFinTrabajo.setForeground(new java.awt.Color(255, 255, 255));
        jhoraFinTrabajo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm"))));
        jhoraFinTrabajo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jhoraFinTrabajo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jHoraInicioTrabajo.setBackground(new java.awt.Color(51, 51, 51));
        jHoraInicioTrabajo.setForeground(new java.awt.Color(255, 255, 255));
        jHoraInicioTrabajo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm"))));
        jHoraInicioTrabajo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jHoraInicioTrabajo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("HORA");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("HORA");

        jTextArea1.setBackground(new java.awt.Color(51, 51, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Observaciones");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("NOTA: LA ORDEN DE TRABAJO SE CERRARÁ AUTOMATICAMENTE AL MOMENTO DE CERRAR TODOS LOS TRABAJOS ASIGNADOS A LA MISMA");

        jFechaInicioTrabajo.setBackground(new java.awt.Color(102, 102, 102));
        jFechaInicioTrabajo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFechaInicioTrabajo.setForeground(new java.awt.Color(255, 153, 0));
        jFechaInicioTrabajo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFechaInicioTrabajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFechaInicioTrabajoMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("FECHA");

        jLabel19.setBackground(new java.awt.Color(255, 102, 0));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("FIN DEL TRABAJO");
        jLabel19.setOpaque(true);

        jLabel20.setBackground(new java.awt.Color(255, 102, 0));
        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("INICIO DEL TRABAJO");
        jLabel20.setOpaque(true);

        jFechaFinTrabajo.setBackground(new java.awt.Color(102, 102, 102));
        jFechaFinTrabajo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFechaFinTrabajo.setForeground(new java.awt.Color(255, 153, 0));
        jFechaFinTrabajo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFechaFinTrabajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFechaFinTrabajoMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("FECHA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1149, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jFechaInicioTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jHoraInicioTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jFechaFinTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jhoraFinTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jAgregar)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel17)
                    .addComponent(jLabel7)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFechaFinTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jhoraFinTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFechaInicioTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jHoraInicioTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        jLabel22.setBackground(new java.awt.Color(0, 51, 102));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("SELECCIONE UN TIPO DE CONTROL");
        jLabel22.setOpaque(true);

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 153, 51));
        jButton3.setText("Ingresos Diarios");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(51, 51, 51));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 153, 51));
        jButton10.setText("Control de Llantas");
        jButton10.setFocusable(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(51, 51, 51));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 153, 51));
        jButton12.setText("Control de Aceites");
        jButton12.setFocusable(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(51, 51, 51));
        jButton13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 153, 51));
        jButton13.setText("SALIR");
        jButton13.setFocusable(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(51, 51, 51));
        jButton14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 153, 51));
        jButton14.setText("Parametros de Empresa");
        jButton14.setFocusable(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(51, 51, 51));
        jButton15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 153, 51));
        jButton15.setText("Control de Baterias");
        jButton15.setFocusable(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(0, 204, 0));
        jButton16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton16.setForeground(new java.awt.Color(0, 51, 102));
        jButton16.setText("Ordenes de Trabajo");
        jButton16.setFocusable(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addGap(18, 18, 18)
                .addComponent(jButton15)
                .addGap(18, 18, 18)
                .addComponent(jButton16)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addGap(18, 18, 18)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 102, 153));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Cierre de Ordenes de Trabajo");

        jLabelVersion16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelVersion16.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVersion16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVersion16.setText("Version.");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jLabelVersion16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(6, 6, 6)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jLabelVersion16)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFechaMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jFechaMouseClicked

    private void jAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAgregarActionPerformed
         new Thread(){
           @Override
           public void run(){
              try {
            SimpleDateFormat hf=new SimpleDateFormat("HH:mm");
            SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Time horainicio=new Time(hf.parse(jHoraInicioTrabajo.getText()).getTime());
            Time horafin=new Time(hf.parse(jhoraFinTrabajo.getText()).getTime());
            Date fechainicio=new java.sql.Date(df.parse(jFechaInicioTrabajo.getText()+" "+jHoraInicioTrabajo.getText()).getTime());
            Date fechafin=new java.sql.Date(df.parse(jFechaFinTrabajo.getText()+" "+jhoraFinTrabajo.getText()).getTime());
            long tiempoinvertido=(fechafin.getTime()-fechainicio.getTime())/60000;
            //String s="update SubOrdenes_Trabajo set HoraInicio='"+horainicio+"', HoraFin='"+horafin+"', Tiempo_Invertido='"+tiempoinvertido+"', Estado='3' where Id_SubMantenimiento='"+idTrabajo+"'";
            //JOptionPane.showMessageDialog(rootPane, s,"Sistema Control de Indicadores", JOptionPane.INFORMATION_MESSAGE);
            String s=modulo.Ejecutar("update SubOrdenes_Trabajo set HoraInicio='"+horainicio+"', HoraFin='"+horafin+"', Tiempo_Invertido='"+tiempoinvertido+"', Estado='3' where Id_SubMantenimiento='"+idTrabajo+"'");
            ////ValoresEstaticos.espere.setVisible(false);
            //JOptionPane.showMessageDialog(rootPane, s,"Sistema Control de Indicadores", JOptionPane.INFORMATION_MESSAGE);
            getTrabajosOrden();
            if (s.contains("concluyó")){
                jFechaFinTrabajo.setText("");
                jHoraInicioTrabajo.setText("");
                jhoraFinTrabajo.setText("");
                
            }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(rootPane, "Debe digitar todos los campos de fecha inicial y fecha final para cerrar el trabajo seleccionado","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
           // //ValoresEstaticos.espere.setVisible(false);
        }
        }
        }.start();
        ////ValoresEstaticos.espere.setVisible(true);
        
        
        
        
    }//GEN-LAST:event_jAgregarActionPerformed

    private void jAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAgregar1ActionPerformed
        // TODO add your handling code here:
        if (saveOrden==false){
        saveOrden=true;
        
        ordentrabajo++;
        jTitulo.setText("ORDEN DE TRABAJO # "+ordentrabajo);
       
        int i=0;
        int count=tablemodel.getRowCount();
        while (i<count){
           //JOptionPane.showMessageDialog(rootPane, tablemodel.getRowCount()+" "+i);
            tablemodel.removeRow(0);
            //jTablaTareas.remove(i);
            i++;
        }
        
        }
    }//GEN-LAST:event_jAgregar1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jAgregar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAgregar2ActionPerformed
        // TODO add your handling code here:
         java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new Thread(){
                        @Override
                        public void run(){
                                new ListadoOrdenesTrabajo().setVisible(true);
                        }
                 }.start();
               
            }
        });
        this.dispose();
    }//GEN-LAST:event_jAgregar2ActionPerformed

    private void jFechaInicioTrabajoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFechaInicioTrabajoMouseClicked
        // TODO add your handling code here:
        jFechaInicioTrabajo.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jFechaInicioTrabajoMouseClicked

    private void jFechaFinTrabajoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFechaFinTrabajoMouseClicked
        // TODO add your handling code here:
        jFechaFinTrabajo.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jFechaFinTrabajoMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_control_ingresos){
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Nuevo_Ingreso().setVisible(true);
                }
            });
            this.dispose();}
        else{
            JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_control_llantas){
         java.awt.EventQueue.invokeLater(new Runnable() {
           @Override
            public void run() {
                new Control_Llantas().setVisible(true);
            }
       });
       this.dispose();}
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_control_aceites){
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Control_Aceites().setVisible(true);
                }
            });
            this.dispose();}
        else{
            JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_control_parametros){
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ParametrosEmpresa().setVisible(true);
                }
            });
            this.dispose();}
        else{
            JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_control_baterias){
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Control_Baterias().setVisible(true);
                }
            });
            this.dispose();}
        else{
            JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_creacion_ordenestrabajo || DataUser.permiso_cierre_ordenestrabajo){
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ListadoOrdenesTrabajo().setVisible(true);
                }
            });
            this.dispose();}
        else{
            JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CierreOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CierreOrdenTrabajo(0).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAgregar;
    private javax.swing.JButton jAgregar1;
    private javax.swing.JButton jAgregar2;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jEmpleado;
    private javax.swing.JTextField jFecha;
    private javax.swing.JTextField jFechaFinTrabajo;
    private javax.swing.JTextField jFechaInicioTrabajo;
    private javax.swing.JFormattedTextField jHoraInicioTrabajo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelVersion16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel jTitulo;
    private javax.swing.JComboBox jTrabajos;
    private javax.swing.JFormattedTextField jUnidad;
    private javax.swing.JFormattedTextField jhoraFinTrabajo;
    // End of variables declaration//GEN-END:variables
    static int ordentrabajo;
    static int idTrabajo;
    static int idPlantel;
    static ModuloMySQL modulo=new ModuloMySQL();
    static OrdenesTrabajo datos=new OrdenesTrabajo();
    static int idTipoMantenimiento;
    static int idSubmanteniento;
    static int idEmpleado;
    static int idPuesto;
    static ResultSet trabajos_realizar;
    static ResultSet result_trabajos;
    static ResultSet result_empleados;
    static boolean saveOrden=true;
    static final Object[] columnames=new Object[]{"Consecutivo","Unidad","Trabajo a Realizar","Encargado"};
    static DefaultTableModel tablemodel=new DefaultTableModel(columnames,0);
    String trabajoselecionado;
    String nombreempleado;
}
