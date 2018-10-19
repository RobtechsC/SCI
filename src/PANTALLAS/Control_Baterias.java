/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PANTALLAS;

import CLASES.ControlMovimientoBaterias;
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
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Esteban
 */
public final class Control_Baterias extends javax.swing.JFrame {

    /**
     * Creates new form Nuevo_Ingreso
     */
    public Control_Baterias() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();   
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        jLabelTitulo.setText(jLabelTitulo.getText()+" "+ValoresEstaticos.getNombreEmpresa().toUpperCase());        
        getListDestinos();
        getBuses();
        Image i=new ImageIcon("C://SCI//SCI.png").getImage();
        this.setIconImage(i);
        jLabelVersion.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
        //getListConductores();
       // //ValoresEstaticos.espere.setVisible(false);
    }
    public Control_Baterias(int consecutivo) {
        try {
            initComponents();
            getBuses();
            consecutivoOld=consecutivo;
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            ResultSet rs=modulo.Listar("Select FechaBateriaSale, Unidad, FechaBateriaEntra, CodBateriaSaliente, Destino, CodBateriaEntrante, Kilometraje, Responsable, IdEmpresa, User from Mantenimiento_MovimientoBaterias where Consecutivo='"+consecutivo+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'");
            SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
            rs.first();
            Date date=rs.getDate("FechaBateriaSale");
            Date date2=rs.getDate("FechaBateriaEntra");
            String codBateriaSale=rs.getString("CodBateriaSaliente");
            String codBateriaEntra=rs.getString("CodBateriaEntrante");
            int i=codBateriaEntra.indexOf("#")+1;
            int i2=codBateriaSale.indexOf("#")+1;
            char[] bateriasal=new char[codBateriaSale.length()-i2];
            char[] bateriaentr=new char[codBateriaEntra.length()-i];
            codBateriaSale.getChars(i2, codBateriaSale.length(), bateriasal, 0);
            codBateriaEntra.getChars(i, codBateriaEntra.length(), bateriaentr, 0);
            isActualizacion=true;
            jTxtFechaSale.setText(sf.format(date));
            if (date2!=null){
               jTxtFechaEntra.setText(sf.format(date2));
            }
            jComboBoxBuses.setSelectedItem(rs.getString("Unidad"));
            unidad=Integer.parseInt(rs.getString("Unidad"));
           
            jTxtKilometraje.setText(rs.getString("Kilometraje"));            
            jComboBoxDestino.setSelectedItem(rs.getObject("Destino"));
            jTxtCodigoBateriaSale.setText(String.valueOf(bateriasal));
            codBateriaSaleOld=rs.getString("CodBateriaSaliente");
            jTxtCodBateriaEntra.setText(String.valueOf(bateriaentr));
            codBateriaEntraOld=rs.getString("CodBateriaEntrante");
            
            jTxtResponsable.setText(rs.getString("Responsable"));
            
            int w = this.getSize().width;
            int h = this.getSize().height;
            int x = (dim.width-w)/2;
            int y = (dim.height-h)/2;
            this.setLocation(x, y);
            jLabelTitulo.setText(jLabelTitulo.getText()+" "+ValoresEstaticos.getNombreEmpresa().toUpperCase());
            getListDestinos();
            Image image=new ImageIcon("C://SCI//SCI.png").getImage();
            this.setIconImage(image);
            jLabelVersion.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
            //getListConductores();
            ////ValoresEstaticos.espere.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(Control_Baterias.class.getName()).log(Level.SEVERE, null, ex);
            ////ValoresEstaticos.espere.setVisible(false);
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
        jTxtCodBateriaEntra = new javax.swing.JTextField();
        jTxtResponsable = new javax.swing.JTextField();
        jTxtKilometraje = new javax.swing.JTextField();
        jButtonGuardar = new javax.swing.JButton();
        jTxtCodigoBateriaSale = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTxtFechaEntra = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxDestino = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jButtonListado = new javax.swing.JButton();
        jComboBoxBuses = new javax.swing.JComboBox();
        jTxtFechaSale = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListBaterias = new javax.swing.JList();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButtonControlMovimientos = new javax.swing.JButton();
        jButtonIngrBateriasNuevas = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButtonReporteria = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Control de Indicadores.");
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 153, 204), new java.awt.Color(0, 102, 153)));

        jLabel39.setBackground(new java.awt.Color(0, 0, 0));
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("CONTROL DE MOVIMIENTOS");
        jLabel39.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(188, 74, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Kilometraje");
        jLabel3.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(188, 74, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Unidad");
        jLabel2.setOpaque(true);

        jTxtCodBateriaEntra.setBackground(new java.awt.Color(102, 102, 102));
        jTxtCodBateriaEntra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtCodBateriaEntra.setForeground(new java.awt.Color(255, 255, 255));
        jTxtCodBateriaEntra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtCodBateriaEntra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtCodBateriaEntra.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtCodBateriaEntra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCodBateriaEntraKeyPressed(evt);
            }
        });

        jTxtResponsable.setBackground(new java.awt.Color(102, 102, 102));
        jTxtResponsable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtResponsable.setForeground(new java.awt.Color(255, 255, 255));
        jTxtResponsable.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtResponsable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtResponsable.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtResponsable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtResponsableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtResponsableKeyReleased(evt);
            }
        });

        jTxtKilometraje.setBackground(new java.awt.Color(102, 102, 102));
        jTxtKilometraje.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtKilometraje.setForeground(new java.awt.Color(255, 255, 255));
        jTxtKilometraje.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtKilometraje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtKilometraje.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtKilometraje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtKilometrajeKeyPressed(evt);
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

        jTxtCodigoBateriaSale.setBackground(new java.awt.Color(102, 102, 102));
        jTxtCodigoBateriaSale.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtCodigoBateriaSale.setForeground(new java.awt.Color(255, 255, 255));
        jTxtCodigoBateriaSale.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtCodigoBateriaSale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtCodigoBateriaSale.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtCodigoBateriaSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCodigoBateriaSaleKeyPressed(evt);
            }
        });

        jLabel38.setBackground(new java.awt.Color(188, 74, 0));
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Cod. Bateria");
        jLabel38.setOpaque(true);

        jTxtFechaEntra.setBackground(new java.awt.Color(102, 102, 102));
        jTxtFechaEntra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTxtFechaEntra.setForeground(new java.awt.Color(255, 255, 255));
        jTxtFechaEntra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtFechaEntra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtFechaEntra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxtFechaEntraMouseClicked(evt);
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
        jLabel40.setText("INFORMACION BATERIAS SALEN");
        jLabel40.setOpaque(true);

        jLabel41.setBackground(new java.awt.Color(0, 0, 0));
        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("INFORMACION BATERIAS ENTRAN");
        jLabel41.setOpaque(true);

        jLabel42.setBackground(new java.awt.Color(188, 74, 0));
        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Cod. Bateria");
        jLabel42.setOpaque(true);

        jLabel14.setBackground(new java.awt.Color(188, 74, 0));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Responsable");
        jLabel14.setOpaque(true);

        jComboBoxDestino.setBackground(new java.awt.Color(102, 102, 102));
        jComboBoxDestino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBoxDestino.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(188, 74, 0));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Destino");
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

        jComboBoxBuses.setBackground(new java.awt.Color(102, 102, 102));
        jComboBoxBuses.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBoxBuses.setForeground(new java.awt.Color(255, 255, 255));

        jTxtFechaSale.setBackground(new java.awt.Color(102, 102, 102));
        jTxtFechaSale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTxtFechaSale.setForeground(new java.awt.Color(255, 255, 255));
        jTxtFechaSale.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtFechaSale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTxtFechaSale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxtFechaSaleMouseClicked(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(188, 74, 0));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("FECHA");
        jLabel16.setOpaque(true);

        jListBaterias.setBackground(new java.awt.Color(51, 51, 51));
        jListBaterias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jListBaterias.setForeground(new java.awt.Color(255, 255, 255));
        jListBaterias.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListBaterias);

        jLabel17.setBackground(new java.awt.Color(188, 74, 0));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Baterias en Unidad");
        jLabel17.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jComboBoxBuses, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTxtKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(74, 74, 74)
                                        .addComponent(jButtonListado, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                .addComponent(jTxtFechaSale))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTxtCodigoBateriaSale, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jComboBoxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTxtFechaEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxtCodBateriaEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxtResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(32, 32, 32)
                                .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonListado, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxBuses, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jLabel42)
                            .addComponent(jLabel14)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtCodigoBateriaSale, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtCodBateriaEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtFechaEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtFechaSale, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jButtonControlMovimientos.setBackground(new java.awt.Color(255, 255, 255));
        jButtonControlMovimientos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonControlMovimientos.setText("Control de Movimientos");
        jButtonControlMovimientos.setFocusable(false);
        jButtonControlMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonControlMovimientosActionPerformed(evt);
            }
        });

        jButtonIngrBateriasNuevas.setBackground(new java.awt.Color(0, 0, 0));
        jButtonIngrBateriasNuevas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonIngrBateriasNuevas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonIngrBateriasNuevas.setText("Ingreso de Baterias Nuevas");
        jButtonIngrBateriasNuevas.setFocusable(false);
        jButtonIngrBateriasNuevas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngrBateriasNuevasActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("SELECCIONE UNA FUNCION");
        jLabel12.setOpaque(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jButtonControlMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jButtonIngrBateriasNuevas, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(0, 41, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonControlMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonIngrBateriasNuevas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addGap(28, 28, 28)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(185, 185, 185)
                .addComponent(jButtonReporteria, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonReporteria, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 102, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Control de Baterías");

        jLabelVersion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelVersion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVersion.setText("Version.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 117, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelVersion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 153, 51));
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

        jButton8.setBackground(new java.awt.Color(0, 204, 0));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 51, 102));
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
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   public void getBuses(){
       new Thread(){
           @Override
           public void run(){
               final ResultSet rs=modulo.Listar("Select Unidad from UnidadesxEmpresa where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' and Estado='1' ORDER BY Unidad");
         
        if (rs!=null){
            try {
                while(rs.next()){
                 jComboBoxBuses.addItem(rs.getString("Unidad"));
                  
                }
                jComboBoxBuses.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        try {
                            int index=jComboBoxBuses.getSelectedIndex()+1;
                            rs.absolute(index);
                            unidad=rs.getInt("Unidad");
                            getBateriasUnidad(unidad);
                            //JOptionPane.showMessageDialog(rootPane, idPlantel);
                        } catch (SQLException ex) {
                            Logger.getLogger(Control_Baterias.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
                        }
                       
                    }
                });
                rs.first();
                unidad=rs.getInt("Unidad");
                 getBateriasUnidad(unidad);
                ValoresEstaticos.ocultaMensaje();
            } catch (SQLException ex) {
                ValoresEstaticos.ocultaMensaje();
                JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
            }
        }
        else{
            ValoresEstaticos.ocultaMensaje();
            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente");
        }
           }
       }.start();
        //String s="Select Unidad from UnidadesxEmpresa where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
        ValoresEstaticos.muestreMensajeEsperar("Recuperando Listado de Buses");
    }
    public void getBateriasUnidad(int unidad){
        jListBaterias.removeAll();
        String sentencia="Select CodBateriaEntrante from Mantenimiento_MovimientoBaterias  Where Unidad='"+unidad+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' order by Consecutivo Desc Limit 5";
        ResultSet rs=modulo.Listar(sentencia);
        DefaultListModel df=new DefaultListModel();
        try {
            while (rs.next()){
               df.addElement(rs.getString("CodBateriaEntrante"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error SQLException. Compruebe que tenga internet y los permisos necesarios para ejecutar esta tarea. Sino comunicarse con el administrador del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        jListBaterias.setModel(df);
        jListBaterias.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                jTxtCodigoBateriaSale.setText(jListBaterias.getSelectedValue().toString());
            }
        });
    }
    public void getListDestinos(){
        new Thread (){
            @Override
            public void run(){
               final ResultSet rs=modulo.Listar("Select * from DestinoBaterias");
        if (rs!=null){
            try {
                while(rs.next()){
                 jComboBoxDestino.addItem(rs.getString("Descripcion"));
                  
                }
                jComboBoxDestino.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        try {
                            idDestino=rs.getInt("IdDestino");
                        } catch (SQLException ex) {
                            Logger.getLogger(Control_Baterias.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
                        }
                       
                    }
                });
                rs.first();
                idDestino=rs.getInt("IdDestino");
                ValoresEstaticos.ocultaMensaje();
            } catch (SQLException ex) {
                ValoresEstaticos.ocultaMensaje();
                JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente "+ex.getMessage());
            }
        }
        else{
            ValoresEstaticos.ocultaMensaje();
            JOptionPane.showMessageDialog(rootPane, "ERROR!!!! No se pudo comunicar con el servidor. Por favor verifique que tiene conexion a internet e intente nuevamente");
        }
            }
        }.start();
        ValoresEstaticos.muestreMensajeEsperar("Recuperando Listado Destinos Llantas");
    }
    
    public boolean isBateriaEntranteDisponible(String codbateria){
        String sentencia="Select Estado from Mantenimiento_IngresoBateriasNuevas Where CodBateria='"+codbateria+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
        //JOptionPane.showMessageDialog(rootPane, sentencia);
        ResultSet rs=modulo.Listar(sentencia);
        if (rs!=null){
            try {
                if (rs.first()){
                    statusllanteentrante=rs.getInt("Estado");
                    return statusllanteentrante==1;
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Codigo de Bateria Invalido.");
                    return false;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Error de comunicacion con servidor SQL. Verifique que tenga internet "+ex.getMessage());
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Codigo de Bateria inválido.");
            return false;
        }
    }
    public boolean testBateriaSaliente(String codllanta, int unidad){
        String sentencia="Select Unidad from Mantenimiento_MovimientoBaterias  Where CodBateriaEntrante='"+codllanta+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' order by FechaBateriaSale Desc Limit 1";
        
        ResultSet rs=modulo.Listar(sentencia);
        if(rs!=null){
            try {
                if (rs.first()){
                    
                    return rs.getInt("Unidad")==unidad;
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
    public boolean testCantidadBaterias(int unidad, String codbateria){
        String sentencia="Select CodBateriaEntrante from Mantenimiento_MovimientoBaterias  Where Unidad='"+unidad+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' order by Consecutivo Desc Limit 2";
        JOptionPane.showMessageDialog(rootPane, sentencia+"  "+codbateria);
        ResultSet rs=modulo.Listar(sentencia);
        if(rs!=null){
            try {
                while (rs.next()){
                    if (rs.getString("CodBateriaEntrante").equals(codbateria)){
                        JOptionPane.showMessageDialog(rootPane, "Codigo de Bateria ya esta registrado en esta unidad ");
                        return false;
                    }
                    
                }
                rs.last();
                if (rs.getRow()==2){
                    JOptionPane.showMessageDialog(rootPane, "El bus ya contienen dos codigos de baterias y ninguno concuerda con el codigo que esta solicitando por favor vuelva a verifique los datos ");
                    return false;
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
    public int getDataCodBateria(String codbateria){
        if (testBateriaSaliente(codbateria, unidad)){
        String sentencia="Select FechaBateriaSale, Unidad from Mantenimiento_MovimientoBaterias Where CodBateriaEntrante='"+codbateria+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' order by FechaBateriaSale Desc limit 1";
        ResultSet rs=modulo.Listar(sentencia);
        if (rs!=null){
            try {
                if (rs.first()){
                   //if (position.equals(rs.getString("Posicion_Llanta_Entra"))){
                   fechabateriasale=rs.getDate("FechaBateriaSale");
                   if (fechabateriasale.after(fechabateriaentra)){
                       JOptionPane.showMessageDialog(rootPane, "Fecha entrante es menor a la fecha del ultimo cambio de bateria "+fechabateriasale+". El registro no se guardará. Si necesita guardar el registro por favor comunicarse con el administrador del sistema.");
                       return 2;
                   }
                   unidadbateriaentra=rs.getInt("Unidad");
                   
                   //JOptionPane.showMessageDialog(rootPane, unidadbateriaentra);
                   return 1;//}
                   
                }
                else{
                    return 0;
                }
            } catch (SQLException ex) {
                return -1;
            }
        }
        else{
            return -1;
        }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Codigo de Bateria no concuerda con el ultimo registrado en la unidad. El registro no se guardara");
            
            
            return 2;
        }
        
    }
    public float getKmFromUni(int unidad, java.sql.Date date){
        try {
            String sentencia="Select SUM(Kilometros_Recorridos) from Ingreso_Datos Where Unidad="+unidad+" and fecha BETWEEN '"+date+"' and '"+fechabateriaentra+"' AND CodigoEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
            ResultSet rs=modulo.Listar(sentencia);
            rs.first();
            return rs.getFloat(1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
           return 0;
        }
        
    }
    private void jButtonControlMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonControlMovimientosActionPerformed
        // TODO add your handling code here:
        jButtonIngrBateriasNuevas.setBackground(Color.BLACK);
        jButtonIngrBateriasNuevas.setForeground(Color.WHITE);
        
        jButtonControlMovimientos.setBackground(Color.WHITE);
        jButtonControlMovimientos.setForeground(Color.BLACK);
    }//GEN-LAST:event_jButtonControlMovimientosActionPerformed

    private void jButtonIngrBateriasNuevasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngrBateriasNuevasActionPerformed
        // TODO add your handling code here:
        
        new Thread(){
            @Override
            public void run(){
                
                java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                        ////ValoresEstaticos.espere.setVisible(true);
                        new Ingreso_Baterias_Nuevas().setVisible(true);
                    }
                });
                Control_Baterias.this.dispose();
                }
        }.start();
        ////ValoresEstaticos.espere.setVisible(true);
       
    }//GEN-LAST:event_jButtonIngrBateriasNuevasActionPerformed

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

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
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
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTxtFechaSaleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtFechaSaleMouseClicked
        // TODO add your handling code here:
        this.jTxtFechaSale.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jTxtFechaSaleMouseClicked

    private void jButtonListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListadoActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListadoBaterias().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonListadoActionPerformed

    private void jTxtFechaEntraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtFechaEntraMouseClicked
        // TODO add your handling code here:
        this.jTxtFechaEntra.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jTxtFechaEntraMouseClicked

    private void jTxtCodigoBateriaSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCodigoBateriaSaleKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtCodigoBateriaSaleKeyPressed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        try {
            if (isActualizacion){
                modulo.Ejecutar("update Mantenimiento_IngresoBateriasNuevas  set Estado='1' Where (CodBateria='"+codBateriaEntraOld+"' or CodBateria='"+codBateriaSaleOld+"') and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'");
                modulo.Ejecutar("delete from Mantenimiento_MovimientoBaterias where Consecutivo='"+consecutivoOld+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'");
            }
            
            SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
            Date fecha=sf.parse(jTxtFechaEntra.getText());
            Calendar c=Calendar.getInstance();
            if (fecha.getTime()==c.getTimeInMillis()||fecha.before(new Date(c.getTimeInMillis())))
            {
                fechabateriasale=new java.sql.Date(fecha.getTime());
                datos.setFechaSale(fechabateriasale);
            }

            else {
                JOptionPane.showMessageDialog(rootPane, "Fecha digitada mayor a la fecha actual, no se puede guardar el registro. Revise la fecha digitada y la fecha de la computadora");
                return;
            }
            if (jTxtFechaEntra.getText().length()>0){
            Date fecha2=sf.parse(jTxtFechaEntra.getText());
            
            if (fecha2.getTime()==c.getTimeInMillis()||fecha2.before(new Date(c.getTimeInMillis())))
            {
                fechabateriaentra=new java.sql.Date(fecha2.getTime());
                datos.setFechaEntra(fechabateriaentra);
            }

            else {
                JOptionPane.showMessageDialog(rootPane, "Fecha digitada mayor a la fecha actual, no se puede guardar el registro. Revise la fecha digitada y la fecha de la computadora");
                return;
            }
            }
            if (jTxtCodBateriaEntra.getText().length()==0){
                int i=JOptionPane.showConfirmDialog(rootPane, "No digitó una bateria entrante, desea guardar el registro sin bateria entrante?", "Sistema de Control de Indicadores", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (i!=JOptionPane.YES_OPTION){
                    return;
                }
                else{
                    datos.setCodBateriaEntra(null);
                }

            }
            else{
                if (isBateriaEntranteDisponible(id+jTxtCodBateriaEntra.getText())==false){
                    JOptionPane.showMessageDialog(rootPane, "Codigo Bateria Entrante no valido. Bateria no disponible");
                    return;
                }
                else{
                    datos.setCodBateriaEntra(null);
                }
            }

            int a;
            if (isFirstRegister==false){
                a=getDataCodBateria(id+jTxtCodigoBateriaSale.getText());
            }
            else{
                a=0;
            }

            if (a==1){
                if (jTxtKilometraje.getText().length()>0){
                    datos.setKilometraje(Float.parseFloat(jTxtKilometraje.getText()));
                    datos.setResponsable(jTxtResponsable.getText());
                }
                else{
                    kmbateriasale=getKmFromUni(unidadbateriaentra, fechabateriasale);
                    if (unidadbateriaentra==unidad||statusllanteentrante==3){
                        datos.setKilometraje(kmbateriasale);}
                    else{
                        JOptionPane.showMessageDialog(rootPane, "Unidad digitada no concuerda con la ultima unidad donde se instalo la bateria. Por favor verificar la informacion");
                        return;
                    }
                }
            }
            else{
                if (a==0){
                    fechabateriasale=new java.sql.Date(c.getTimeInMillis());
                    unidadbateriaentra=unidad;
                    kmbateriasale=0;
                    datos.setKilometraje(kmbateriasale);
                    datos.setResponsable(jTxtResponsable.getText());
                }
                if (a==2){
                    return;
                }
                else{
                    if (a==-1){
                        JOptionPane.showMessageDialog(rootPane, "No se pudo determinar el kilometraje de la llanta digitada, por favor verifique que tiene internet.");
                        return;
                    }
                }
            }
            if (isBateriaEntranteDisponible(id+jTxtCodBateriaEntra.getText())){
                datos.setCodBateriaEntra(id+jTxtCodBateriaEntra.getText());
            }
            else{
                if (statusllanteentrante==0){
                    int i=JOptionPane.showConfirmDialog(null, "No se encontro Codigo de Bateria Entrante. Desea guardarla?");
                    if (i==JOptionPane.OK_OPTION)
                    {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run()
                            {
                                new Ingreso_Baterias_Nuevas(jTxtCodBateriaEntra.getText()).setVisible(true);
                            }
                        });

                    }
                }
                JOptionPane.showMessageDialog(rootPane, "Error. No se puede instalar bateria que no este disponible. El ultimo estado de la bateria fue: "+statusllanteentrante);
                return;
            }
            if (jTxtCodigoBateriaSale.getText().length()>0){
                datos.setCodBateriaSale(id+jTxtCodigoBateriaSale.getText());
                datos.setDestinoBateriaSale(idDestino);
            }
            else{
                if (testCantidadBaterias(unidad, id+jTxtCodBateriaEntra.getText())){
                datos.setCodBateriaSale(null);
                if (idDestino!=6){
                    int i=JOptionPane.showConfirmDialog(rootPane, "Se guardará destino como Instalada en Unidad ya que no existe codigo de bateria saliente. Esta de acuerdo, sino no se guardara el registro", "ATENCION!!!!", JOptionPane.WARNING_MESSAGE);
                    if (i==JOptionPane.OK_OPTION){
                        datos.setDestinoBateriaSale(6);
                    }
                    else{
                        return;
                    }
                }
                
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "hhjj");
                    return;
                }

            }
            // datos.setKilometraje(Float.parseFloat(jTxtKilometraje.getText()));

            datos.setUnidad(unidad);
            if (datos.saveDataMySql()){
                String setllaantasalientependiente = null;
                if (jComboBoxDestino.getSelectedItem().toString().contains("Desechar")&jTxtCodigoBateriaSale.getText().length()>0){
                    setllaantasalientependiente="update Mantenimiento_IngresoBateriasNuevas set Estado=3 where CodBateria='"+id+jTxtCodigoBateriaSale.getText()+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
                }
                else{
                    if (jComboBoxDestino.getSelectedItem().toString().contains("Inventario")&jTxtCodigoBateriaSale.getText().length()>0){
                        if (tipoinventariollanta==1){
                            setllaantasalientependiente="update Mantenimiento_IngresoBateriasNuevas set Estado=1 where CodBateria='"+id+jTxtCodigoBateriaSale.getText()+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
                        }
                        else{
                            if (jTxtCodigoBateriaSale.getText().length()>0){
                                setllaantasalientependiente="update Mantenimiento_IngresoBateriasNuevas set Estado=2, Inventario=2 where CodBateria='"+id+jTxtCodigoBateriaSale.getText()+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";}
                        }

                    }
                    else{

                        setllaantasalientependiente="update Mantenimiento_IngresoBateriasNuevas set Estado=2 where CodBateria='"+id+jTxtCodigoBateriaSale.getText()+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
                    }
                }
                String setllantaentranteinstalada="update Mantenimiento_IngresoBateriasNuevas set Estado=4 where CodBateria='"+id+jTxtCodBateriaEntra.getText()+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";

                modulo.Ejecutar(setllantaentranteinstalada);
                if (jTxtCodigoBateriaSale.getText().length()>0){
                    modulo.Ejecutar(setllaantasalientependiente);}
                clearData();
            }

        } catch (ParseException ex) {
            Logger.getLogger(Control_Baterias.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jTxtKilometrajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtKilometrajeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtKilometrajeKeyPressed

    private void jTxtResponsableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtResponsableKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtResponsableKeyReleased

    private void jTxtResponsableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtResponsableKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtResponsableKeyPressed

    private void jTxtCodBateriaEntraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCodBateriaEntraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonGuardarActionPerformed(null);
        }
    }//GEN-LAST:event_jTxtCodBateriaEntraKeyPressed
    
   
    public void clearData(){
        
        jTxtResponsable.setText("");
       // jTxtCarreras.setText("");
        jTxtCodBateriaEntra.setText("");        
        codBateriaEntraOld="";
        codBateriaSaleOld="";
        isActualizacion=false;
        jTxtCodigoBateriaSale.setText("");
        jTxtKilometraje.setText("");
       
        //jTxtRol.setText("");
        unidad=0;
        statusllanteentrante=0;
        isFirstRegister=false;
        jComboBoxBuses.grabFocus();
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
            java.util.logging.Logger.getLogger(Control_Baterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Control_Baterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Control_Baterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Control_Baterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Control_Baterias().setVisible(true);
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
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonIngrBateriasNuevas;
    private javax.swing.JButton jButtonListado;
    private javax.swing.JButton jButtonReporteria;
    private javax.swing.JComboBox jComboBoxBuses;
    private javax.swing.JComboBox jComboBoxDestino;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JList jListBaterias;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTxtCodBateriaEntra;
    private javax.swing.JTextField jTxtCodigoBateriaSale;
    private javax.swing.JTextField jTxtFechaEntra;
    private javax.swing.JTextField jTxtFechaSale;
    private javax.swing.JTextField jTxtKilometraje;
    private javax.swing.JTextField jTxtResponsable;
    // End of variables declaration//GEN-END:variables
    ControlMovimientoBaterias datos=new ControlMovimientoBaterias();
    static ModuloMySQL modulo=new ModuloMySQL();
    static int idDestino;
    static java.sql.Date fechabateriasale;
    static int unidadbateriaentra;
    static float kmbateriasale;
    static int statusllanteentrante=0;
    static java.sql.Date fechabateriaentra;
    static String id=ValoresEstaticos.getIdEmpresa()+"#";
    static int tipoinventariollanta;
    static int disponibilidadllanta;
    static boolean isFirstRegister;
    static boolean isActualizacion;
    static String codBateriaSaleOld;
    static String codBateriaEntraOld;
    static int consecutivoOld;
    static int unidad;
}
