/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PANTALLAS;

import CLASES.ControlMovimientoLlantas;
import CLASES.ControlReencauches;
import CLASES.DataUser;
import CLASES.ModuloMySQL;
import CLASES.ValoresEstaticos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public final class Control_ReencauchesLlantas extends javax.swing.JFrame {

    /**
     * Creates new form Nuevo_Ingreso
     */
    public Control_ReencauchesLlantas() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();   
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        getProveedores();
        getCodLlantasPendientes();
        jLabelTitulo.setText(jLabelTitulo.getText()+" "+ValoresEstaticos.getNombreEmpresa().toUpperCase());
        if (DataUser.permiso_control_llantas){
        getListEstados();}
        else{
           
            jTxtBoletaTapachula.setEnabled(false);
           
            jTxtFechaEntrante.setEnabled(false);
            jTxtFactura.setEnabled(false);
            jTxtCosto.setEnabled(false);
            jTxtBoletaProveedor.setEnabled(false);
            jComboBoxProveedores.setEnabled(false);
            jButtonGuardar.setEnabled(false);
            jButtonControlSemanal.setEnabled(false);
            jButtonListado.setEnabled(false);
            jButtonIngrLlantasNuevas.setEnabled(false);
            jComboBoxEstado.setEnabled(false);
            
            
            
        }
         Image i=new ImageIcon("C://SCI//SCI.png").getImage();
        this.setIconImage(i);
        jLabelVersion.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
        //getListConductores();
        //ValoresEstaticos.espere.setVisible(false);
    }
    public Control_ReencauchesLlantas(int cons) {
        try {
            initComponents();
           getProveedores();
           //getListEstados();
           getCodLlantasPendientes();
           isActualizacion=true;
            consecutivo=cons;
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            ResultSet rs=modulo.Listar("Select Fecha_Boleta, Proveedor, Boleta_Proveedor, Boleta_Tapachula, CodLlanta, Fecha_Entrada, Factura, Costo, Estado from Control_ReencauchesLlantas where Consecutivo='"+consecutivo+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'");
            SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
            rs.first();
            Date fechaentra=rs.getDate("Fecha_Entrada");
            Date fechasale=rs.getDate("Fecha_Boleta");
            if (fechaentra!=null){
            jTxtFechaEntrante.setText(sf.format(fechaentra));}
            jTxtFechaSalida.setText(sf.format(fechasale));
            idproveedor=rs.getInt("Proveedor");
            jComboBoxProveedores.setSelectedItem(rs.getString("Proveedor"));
            jTxtFactura.setText(rs.getString("Factura"));
            
            idEstado=rs.getInt("Estado");
            jComboBoxEstado.setSelectedItem(rs.getObject("Estado"));
            jComboBoxCodLlanta.setSelectedItem(rs.getObject("CodLlanta"));
            codllanta=rs.getString("CodLlanta");
            jTxtBoletaProveedor.setText(rs.getString("Boleta_Proveedor"));
            jTxtBoletaTapachula.setText(rs.getString("Boleta_Tapachula"));
            jTxtCosto.setText(rs.getString("Costo"));
            int w = this.getSize().width;
            int h = this.getSize().height;
            int x = (dim.width-w)/2;
            int y = (dim.height-h)/2;
            this.setLocation(x, y);
            jLabelTitulo.setText(jLabelTitulo.getText()+" "+ValoresEstaticos.getNombreEmpresa().toUpperCase());
            jLabelVersion.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
            getListEstados();
            Image image=new ImageIcon("C://SCI//SCI.png").getImage();
            this.setIconImage(image);
            //ValoresEstaticos.espere.setVisible(false);
            //getListConductores();
        } catch (SQLException ex) {
            Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(Level.SEVERE, null, ex);
            //ValoresEstaticos.espere.setVisible(false);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtBoletaTapachula = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTxtCosto = new javax.swing.JTextField();
        jTxtFactura = new javax.swing.JTextField();
        jTxtBoletaProveedor = new javax.swing.JTextField();
        jButtonGuardar = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jTxtFechaEntrante = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxEstado = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jButtonListado = new javax.swing.JButton();
        jComboBoxProveedores = new javax.swing.JComboBox();
        jLabel43 = new javax.swing.JLabel();
        jTxtFechaSalida = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButtonGuardar2 = new javax.swing.JButton();
        jComboBoxCodLlanta = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButtonControlMovimientos = new javax.swing.JButton();
        jButtonIngrLlantasNuevas = new javax.swing.JButton();
        jButtonControlSemanal = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jButtonControlMovimientos1 = new javax.swing.JButton();
        jButtonControlSemanal1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButtonReporteria = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Control de Indicadores.");
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 153, 204), new java.awt.Color(0, 102, 153)));

        jLabel39.setBackground(new java.awt.Color(0, 0, 0));
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("CONTROL DE REENCAUCHES DE LLANTAS");
        jLabel39.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(188, 74, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Factura");
        jLabel3.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(188, 74, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Proveedor");
        jLabel2.setOpaque(true);

        jTxtBoletaTapachula.setBackground(new java.awt.Color(102, 102, 102));
        jTxtBoletaTapachula.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtBoletaTapachula.setForeground(new java.awt.Color(255, 255, 255));
        jTxtBoletaTapachula.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtBoletaTapachula.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtBoletaTapachula.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtBoletaTapachula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtBoletaTapachulaKeyPressed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(188, 74, 0));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Boleta Proveedor");
        jLabel8.setOpaque(true);

        jTxtCosto.setBackground(new java.awt.Color(102, 102, 102));
        jTxtCosto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtCosto.setForeground(new java.awt.Color(255, 255, 255));
        jTxtCosto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtCosto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtCosto.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCostoKeyPressed(evt);
            }
        });

        jTxtFactura.setBackground(new java.awt.Color(102, 102, 102));
        jTxtFactura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtFactura.setForeground(new java.awt.Color(255, 255, 255));
        jTxtFactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtFactura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtFactura.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtFacturaKeyPressed(evt);
            }
        });

        jTxtBoletaProveedor.setBackground(new java.awt.Color(102, 102, 102));
        jTxtBoletaProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtBoletaProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jTxtBoletaProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtBoletaProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtBoletaProveedor.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtBoletaProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtBoletaProveedorKeyPressed(evt);
            }
        });

        jButtonGuardar.setBackground(new java.awt.Color(51, 51, 51));
        jButtonGuardar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonGuardar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/save_1.png"))); // NOI18N
        jButtonGuardar.setText("GUARDAR");
        jButtonGuardar.setBorder(null);
        jButtonGuardar.setFocusable(false);
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jLabel38.setBackground(new java.awt.Color(188, 74, 0));
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Cod. Llanta");
        jLabel38.setOpaque(true);

        jTxtFechaEntrante.setBackground(new java.awt.Color(102, 102, 102));
        jTxtFechaEntrante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTxtFechaEntrante.setForeground(new java.awt.Color(255, 255, 255));
        jTxtFechaEntrante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtFechaEntrante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtFechaEntrante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxtFechaEntranteMouseClicked(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(188, 74, 0));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("FECHA");
        jLabel15.setOpaque(true);

        jLabel40.setBackground(new java.awt.Color(0, 0, 0));
        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("CONTROL DE ENTRADA");
        jLabel40.setOpaque(true);

        jLabel42.setBackground(new java.awt.Color(188, 74, 0));
        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Boleta Tapachula");
        jLabel42.setOpaque(true);

        jLabel14.setBackground(new java.awt.Color(188, 74, 0));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Costo");
        jLabel14.setOpaque(true);

        jComboBoxEstado.setBackground(new java.awt.Color(102, 102, 102));
        jComboBoxEstado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBoxEstado.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(188, 74, 0));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Estado");
        jLabel7.setOpaque(true);

        jButtonListado.setBackground(new java.awt.Color(51, 51, 51));
        jButtonListado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonListado.setForeground(new java.awt.Color(255, 255, 255));
        jButtonListado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/list.png"))); // NOI18N
        jButtonListado.setText("VER LISTADO");
        jButtonListado.setBorder(null);
        jButtonListado.setFocusable(false);
        jButtonListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListadoActionPerformed(evt);
            }
        });

        jComboBoxProveedores.setBackground(new java.awt.Color(102, 102, 102));
        jComboBoxProveedores.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBoxProveedores.setForeground(new java.awt.Color(255, 255, 255));

        jLabel43.setBackground(new java.awt.Color(0, 0, 0));
        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("CONTROL DE SALIDA");
        jLabel43.setOpaque(true);

        jTxtFechaSalida.setBackground(new java.awt.Color(102, 102, 102));
        jTxtFechaSalida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTxtFechaSalida.setForeground(new java.awt.Color(255, 255, 255));
        jTxtFechaSalida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtFechaSalida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtFechaSalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxtFechaSalidaMouseClicked(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(188, 74, 0));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("FECHA");
        jLabel16.setOpaque(true);

        jButtonGuardar2.setBackground(new java.awt.Color(51, 51, 51));
        jButtonGuardar2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonGuardar2.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/save_1.png"))); // NOI18N
        jButtonGuardar2.setText("Actualizar");
        jButtonGuardar2.setBorder(null);
        jButtonGuardar2.setFocusable(false);
        jButtonGuardar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardar2ActionPerformed(evt);
            }
        });

        jComboBoxCodLlanta.setBackground(new java.awt.Color(102, 102, 102));
        jComboBoxCodLlanta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBoxCodLlanta.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(jTxtFechaSalida))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jComboBoxCodLlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtBoletaProveedor))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTxtBoletaTapachula, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jButtonListado, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtFechaEntrante)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTxtFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTxtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jButtonGuardar2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel38)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtBoletaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtBoletaTapachula, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonListado, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCodLlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel3)
                    .addComponent(jLabel14)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtFechaEntrante, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGuardar2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel13.setBackground(new java.awt.Color(0, 51, 102));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("SELECCIONE UN TIPO DE CONTROL");
        jLabel13.setOpaque(true);

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 153, 51));
        jButton2.setText("Ingresos Diarios");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 204, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setText("Control de Llantas");
        jButton4.setFocusable(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 153, 51));
        jButton5.setText("Control de Aceites");
        jButton5.setFocusable(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(51, 51, 51));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 153, 51));
        jButton6.setText("SALIR");
        jButton6.setFocusable(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(51, 51, 51));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 153, 51));
        jButton7.setText("Parametros de Empresa");
        jButton7.setFocusable(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(51, 51, 51));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 153, 51));
        jButton8.setText("Control de Baterias");
        jButton8.setFocusable(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(51, 51, 51));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 153, 51));
        jButton9.setText("Ordenes de Trabajo");
        jButton9.setFocusable(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addGap(30, 30, 30)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jButtonControlMovimientos.setBackground(new java.awt.Color(0, 0, 0));
        jButtonControlMovimientos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonControlMovimientos.setForeground(new java.awt.Color(255, 255, 255));
        jButtonControlMovimientos.setText("Control de Movimientos");
        jButtonControlMovimientos.setFocusable(false);
        jButtonControlMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonControlMovimientosActionPerformed(evt);
            }
        });

        jButtonIngrLlantasNuevas.setBackground(new java.awt.Color(0, 0, 0));
        jButtonIngrLlantasNuevas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonIngrLlantasNuevas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonIngrLlantasNuevas.setText("Ingreso de Llantas Nuevas");
        jButtonIngrLlantasNuevas.setFocusable(false);
        jButtonIngrLlantasNuevas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngrLlantasNuevasActionPerformed(evt);
            }
        });

        jButtonControlSemanal.setBackground(new java.awt.Color(0, 0, 0));
        jButtonControlSemanal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonControlSemanal.setForeground(new java.awt.Color(255, 255, 255));
        jButtonControlSemanal.setText("Control Semanal Profundidades");
        jButtonControlSemanal.setFocusable(false);
        jButtonControlSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonControlSemanalActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("SELECCIONE UNA FUNCION");
        jLabel12.setOpaque(true);

        jButtonControlMovimientos1.setBackground(new java.awt.Color(255, 255, 255));
        jButtonControlMovimientos1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonControlMovimientos1.setText("Control de Reencauches");
        jButtonControlMovimientos1.setFocusable(false);
        jButtonControlMovimientos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonControlMovimientos1ActionPerformed(evt);
            }
        });

        jButtonControlSemanal1.setBackground(new java.awt.Color(0, 0, 0));
        jButtonControlSemanal1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonControlSemanal1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonControlSemanal1.setText("Control Semanal Presiones");
        jButtonControlSemanal1.setFocusable(false);
        jButtonControlSemanal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonControlSemanal1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonControlMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonIngrLlantasNuevas, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonControlSemanal, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonControlSemanal1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonControlMovimientos1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 37, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonControlMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonControlSemanal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonIngrLlantasNuevas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonControlMovimientos1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonControlSemanal1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabelTitulo.setBackground(new java.awt.Color(51, 51, 51));
        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("INGRESO DE DATOS AL SISTEMA SCI");
        jLabelTitulo.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/SCI.png"))); // NOI18N
        jLabel9.setOpaque(true);

        jButtonReporteria.setBackground(new java.awt.Color(51, 51, 51));
        jButtonReporteria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonReporteria.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/reportes.png"))); // NOI18N
        jButtonReporteria.setText("Reporteria");
        jButtonReporteria.setBorder(null);
        jButtonReporteria.setFocusable(false);
        jButtonReporteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonReporteria, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonReporteria, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 102, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Control de Llantas");

        jLabelVersion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelVersion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVersion.setText("Version.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelVersion, javax.swing.GroupLayout.DEFAULT_SIZE, 1201, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jLabelVersion)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void getProveedores(){
        //String s="Select Unidad from UnidadesxEmpresa where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
        final ResultSet rs=modulo.Listar("Select IdProveedor, Nombre_Proveedor from Mantenimiento_ProveedoresLlantas where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'");
       //  Logger.getLogger(Control_Aceites.class.getName()).log(Level.SEVERE, s);
        if (rs!=null){
            try {
                while(rs.next()){
                 jComboBoxProveedores.addItem(rs.getString("Nombre_Proveedor"));
                  
                }
                jComboBoxProveedores.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        try {
                            int index=jComboBoxProveedores.getSelectedIndex()+1;
                            rs.absolute(index);
                            idproveedor=rs.getInt("IdProveedor");
                            //JOptionPane.showMessageDialog(rootPane, idPlantel);
                        } catch (SQLException ex) {
                            Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
                        }
                       
                    }
                });
                rs.first();
                idproveedor=rs.getInt("IdProveedor");
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente");
        }
    }
    public void getCodLlantasPendientes(){
        //String s="Select Unidad from UnidadesxEmpresa where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
        final ResultSet rs=modulo.Listar("Select Consecutivo, Cod_Llanta from Mantenimiento_IngresoLlantasNuevas where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' and Status='2'");
       //  Logger.getLogger(Control_Aceites.class.getName()).log(Level.SEVERE, s);
        if (rs!=null){
            try {
                while(rs.next()){
                 jComboBoxCodLlanta.addItem(rs.getString("Cod_Llanta"));
                  
                }
                jComboBoxCodLlanta.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        try {
                            int index=jComboBoxCodLlanta.getSelectedIndex()+1;
                            rs.absolute(index);
                            codllanta=rs.getString("Cod_Llanta");
                            consecutivo=rs.getInt("Consecutivo");
                            //JOptionPane.showMessageDialog(rootPane, idPlantel);
                        } catch (SQLException ex) {
                            Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
                        }
                       
                    }
                });
                rs.first();
                codllanta=rs.getString("Cod_Llanta");
                consecutivo=rs.getInt("Consecutivo");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente");
        }
    }
    public void getListEstados(){
        final ResultSet rs=modulo.Listar("Select * from ListadoStatus where IdEstado>2");
        if (rs!=null){
            try {
                while(rs.next()){
                 jComboBoxEstado.addItem(rs.getString("Descripcion Estado"));
                  
                }
                jComboBoxEstado.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        try {
                            idEstado=rs.getInt("IdEstado");
                        } catch (SQLException ex) {
                            Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
                        }
                       
                    }
                });
                rs.first();
                idEstado=rs.getInt("IdEstado");
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente");
        }
    }
   
    public boolean isLlantaEntrantePendiente(String codllanta){
        String sentencia="Select Status from Mantenimiento_IngresoLlantasNuevas Where Cod_Llanta='"+codllanta+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
        ResultSet rs=modulo.Listar(sentencia);
        if (rs!=null){
            try {
                if (rs.first()){
                    statusllanteentrante=rs.getInt("Status");
                    return statusllanteentrante==3;
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Codigo de Llanta Invalido.");
                    return false;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Error de comunicacion con servidor SQL. Verifique que tenga internet");
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Codigo de llanta invalido.");
            return false;
        }
    }
    public boolean testLlantaSaliente(String codllanta, String position, int unidad){
        String sentencia="Select Unidad, Cod_Llanta_Entra, Posicion_Llanta_Entra from Mantenimiento_MovimientoLlantas  Where Unidad='"+unidad+"' and Posicion_Llanta_Entra='"+position+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' order by Fecha Desc Limit 1";
        
        ResultSet rs=modulo.Listar(sentencia);
        if(rs!=null){
            try {
                if (rs.first()){
                    
                    return rs.getString("Cod_Llanta_Entra").toUpperCase().contains(codllanta.toUpperCase());
                }
                else{
                    return true;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(Control_Llantas.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, "Error de Comunicacion con el servidor SQL. Por favor verique la conexion a Internet");
                return false;
                
            }
        }
        else{
            return false;
        }
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
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
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButtonControlMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonControlMovimientosActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
           @Override
            public void run() {
                new Control_Llantas().setVisible(true);
            }
       });
       this.dispose();
    }//GEN-LAST:event_jButtonControlMovimientosActionPerformed

    private void jButtonIngrLlantasNuevasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngrLlantasNuevasActionPerformed
        // TODO add your handling code here:
       java.awt.EventQueue.invokeLater(new Runnable() {
           @Override
            public void run() {
                new Ingreso_Llantas_Nuevas().setVisible(true);
            }
       });
       this.dispose();
    }//GEN-LAST:event_jButtonIngrLlantasNuevasActionPerformed

    private void jButtonControlSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonControlSemanalActionPerformed
        // TODO add your handling code here:
        jButtonControlSemanal.setBackground(Color.WHITE);
        jButtonControlSemanal.setForeground(Color.BLACK);
        jButtonControlMovimientos.setBackground(Color.BLACK);
        jButtonControlMovimientos.setForeground(Color.WHITE);
        jButtonIngrLlantasNuevas.setBackground(Color.BLACK);
        jButtonIngrLlantasNuevas.setForeground(Color.WHITE);
         java.awt.EventQueue.invokeLater(new Runnable() {
           @Override
            public void run() {
                new Control_LlantasSemanal().setVisible(true);
            }
       });
       this.dispose();
        
    }//GEN-LAST:event_jButtonControlSemanalActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
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
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
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
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButtonReporteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteriaActionPerformed
        // TODO add your handling code here:
        if (DataUser.permitereporteria){
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Reporteria().setVisible(true);
                }
            });
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_jButtonReporteriaActionPerformed

    private void jButtonControlMovimientos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonControlMovimientos1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonControlMovimientos1ActionPerformed

    private void jTxtFechaSalidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtFechaSalidaMouseClicked
        // TODO add your handling code here:
        this.jTxtFechaSalida.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jTxtFechaSalidaMouseClicked

    private void jButtonListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListadoActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListadoReencauchesLlantas().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonListadoActionPerformed

    private void jTxtFechaEntranteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtFechaEntranteMouseClicked
        // TODO add your handling code here:
        this.jTxtFechaEntrante.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jTxtFechaEntranteMouseClicked

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        try {
            if (isActualizacion){
                JOptionPane.showMessageDialog(rootPane, "Error. Utilice el boton actualizar para guardar los cambios realizados", "Sistema Control de Indicadores", JOptionPane.ERROR_MESSAGE);
                return;
            }
            datos.setBoletaProveedor(Integer.parseInt(jTxtBoletaProveedor.getText()));
            datos.setBoletaTapachula(Integer.parseInt(jTxtBoletaTapachula.getText()));
            datos.setCodigoLlanta(codllanta);
            
            SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date fechasalida=df.parse(jTxtFechaSalida.getText());
            datos.setFechaBoleta(new java.sql.Date(fechasalida.getTime()));
            datos.setIdEmpresa(ValoresEstaticos.idempresa);
            datos.setProveedor(idproveedor);
            if (datos.saveData()){
                JOptionPane.showMessageDialog(rootPane, "Registro ingresado correctamente", "Sistema Control Indicadores", JOptionPane.INFORMATION_MESSAGE);
                clearData();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jTxtBoletaProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtBoletaProveedorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtBoletaProveedorKeyPressed

    private void jTxtFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFacturaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtFacturaKeyPressed

    private void jTxtCostoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCostoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtCostoKeyPressed

    private void jTxtBoletaTapachulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtBoletaTapachulaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtBoletaTapachulaKeyPressed

    private void jButtonGuardar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardar2ActionPerformed
        // TODO add your handling code here:
         try {
            datos.setBoletaProveedor(Integer.parseInt(jTxtBoletaProveedor.getText()));
            datos.setBoletaTapachula(Integer.parseInt(jTxtBoletaTapachula.getText()));
            datos.setCodigoLlanta(codllanta);
            datos.setCosto(Float.parseFloat(jTxtCosto.getText()));
            datos.setEstado(idEstado);
            datos.setFactura(jTxtFactura.getText());
            SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date fechasalida=df.parse(jTxtFechaSalida.getText());
            datos.setFechaBoleta(new java.sql.Date(fechasalida.getTime()));
            datos.setFechaEntrada(new java.sql.Date(df.parse(jTxtFechaEntrante.getText()).getTime()));
            datos.setIdEmpresa(ValoresEstaticos.idempresa);
            datos.setProveedor(idproveedor);
            datos.updateData(consecutivo);
        } catch (ParseException ex) {
            Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonGuardar2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_cierre_ordenestrabajo || DataUser.permiso_creacion_ordenestrabajo){
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
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButtonControlSemanal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonControlSemanal1ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
           @Override
            public void run() {
                new Control_PresionesLlantasSemanal().setVisible(true);
            }
       });
       this.dispose();
    }//GEN-LAST:event_jButtonControlSemanal1ActionPerformed
    

    public void clearData(){
        jTxtBoletaProveedor.setText("");
        jTxtBoletaTapachula.setText("");
        jTxtCosto.setText("");
        jTxtFactura.setText("");
        codllanta="";
        isActualizacion=false;
        jTxtBoletaProveedor.grabFocus();
    }
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
            java.util.logging.Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Control_ReencauchesLlantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Control_ReencauchesLlantas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonControlMovimientos;
    private javax.swing.JButton jButtonControlMovimientos1;
    private javax.swing.JButton jButtonControlSemanal;
    private javax.swing.JButton jButtonControlSemanal1;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonGuardar2;
    private javax.swing.JButton jButtonIngrLlantasNuevas;
    private javax.swing.JButton jButtonListado;
    private javax.swing.JButton jButtonReporteria;
    private javax.swing.JComboBox jComboBoxCodLlanta;
    private javax.swing.JComboBox jComboBoxEstado;
    private javax.swing.JComboBox jComboBoxProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTxtBoletaProveedor;
    private javax.swing.JTextField jTxtBoletaTapachula;
    private javax.swing.JTextField jTxtCosto;
    private javax.swing.JTextField jTxtFactura;
    private javax.swing.JTextField jTxtFechaEntrante;
    private javax.swing.JTextField jTxtFechaSalida;
    // End of variables declaration//GEN-END:variables
    
    static ModuloMySQL modulo=new ModuloMySQL();
    static int idEstado;
    //static java.sql.Date fechallantasale;
    //static int unidadllantaentra;
    //static float kmllantasale;
    static int statusllanteentrante=0;
    //static java.sql.Date fechallantaentra;
    static String id=ValoresEstaticos.getIdEmpresa()+"#";
    //static int tipoinventariollanta;
    //static int disponibilidadllanta;
   
    static boolean isActualizacion;
    //static String codLlantaSaleOld;
    //static String codLlantaEntraOld;
    static int consecutivoOld;
    static int idproveedor;
    static String codllanta;
    static int consecutivo;
    static ControlReencauches datos=new ControlReencauches();
}
