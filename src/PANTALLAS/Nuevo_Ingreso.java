/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PANTALLAS;

import CLASES.ControlMarias;
import CLASES.DataUser;
import CLASES.ModuloMySQL;
import CLASES.ModuloMySQLSOL;
import CLASES.ValoresEstaticos;
import CLASES.ValoresIngreso;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public class Nuevo_Ingreso extends javax.swing.JFrame {

    /**
     * Creates new form Nuevo_Ingreso
     */
    public Nuevo_Ingreso() {
        
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();   
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        jLabelTitulo.setText(jLabelTitulo.getText()+" "+ValoresEstaticos.getNombreEmpresa().toUpperCase());
        jLabelVersion.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
        setListenerPistola();
        getListRamales();
        getListUnidades();
         Image i=new ImageIcon("C://SCI//SCI.png").getImage();
        this.setIconImage(i);
        
    }
    public Nuevo_Ingreso(int cons) {
        try {
            initComponents();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            consecutivo=cons;
            getListRamales();
            getListUnidades();
            setListenerPistola();
            //ValoresEstaticos.espere.setVisible(false);
            int w = this.getSize().width;
            int h = this.getSize().height;
            int x = (dim.width-w)/2;
            int y = (dim.height-h)/2;
            this.setLocation(x, y);
            isActualizacion=true;
            jLabelTitulo.setText(jLabelTitulo.getText()+" "+ValoresEstaticos.getNombreEmpresa().toUpperCase());
            jLabelVersion.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
            ResultSet rs=modulo.Listar("Select Consecutivo, Fecha, Unidad, Litros_Dispensados, Kilometros_Recorridos, Plantel, User from Ingreso_Datos Where CodigoEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' and Consecutivo ='"+consecutivo+"'");
            rs.first();
            SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
            jTxtFecha.setText(df.format(rs.getDate("Fecha")));
            jTxtUnidad.setSelectedItem(rs.getString("Unidad"));
            jTextLitros.setText(rs.getString("Litros_Dispensados"));
            jTxtKilometraje.setText(rs.getString("Kilometros_Recorridos"));
            idPlantel=rs.getInt("Plantel");
            
            Image i=new ImageIcon("C://SCI//SCI.png").getImage();
            this.setIconImage(i);
            //ValoresEstaticos.espere.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(Nuevo_Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            //ValoresEstaticos.espere.setVisible(false);
        }
    }
   
    public boolean testMarias(){
        return Float.parseFloat(jDiferenciaM1.getText())!=0||Integer.parseInt(jDiferenciaM2.getText())!=0||Integer.parseInt(jDiferenciaM3.getText())!=0||Integer.parseInt(jDiferenciaM4.getText())!=0;
           
        
    }
public void saveData(){
        if (insertMarias){
            
            try {
                if (testMarias()){
                
                marias.setAperturaM1(Float.parseFloat(jAperturaMaria1.getText()));
                marias.setCierreM1(Float.parseFloat(jCierreMaria1.getText()));
                marias.setAperturaM2(Float.parseFloat(jAperturaMaria2.getText()));
                marias.setCierreM2(Float.parseFloat(jCierreMaria2.getText()));
                marias.setAperturaM3(Float.parseFloat(jAperturaMaria3.getText()));
                marias.setCierreM3(Float.parseFloat(jCierreMaria3.getText()));
                marias.setAperturaM4(Float.parseFloat(jAperturaMaria4.getText()));
                marias.setCierreM4(Float.parseFloat(jCierreMaria4.getText()));
                marias.setFecha(new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(jTxtFecha.getText()).getTime()));
                marias.setIdPlantel(idPlantel);
                if (marias.saveControlMarias()==false){
                    return;
                }
                else{
                    idMarias=marias.getIdMaria();
                    insertMarias=false;
                    jAperturaMaria1.setEditable(false);
                    jCierreMaria1.setEditable(false);
                    jAperturaMaria2.setEditable(false);
                    jCierreMaria2.setEditable(false);
                    jAperturaMaria3.setEditable(false);
                    jCierreMaria3.setEditable(false);
                    jAperturaMaria4.setEditable(false);
                    jCierreMaria4.setEditable(false);
                    diferenciaM1=Float.parseFloat(jDiferenciaM1.getText());
                    diferenciaM2=Float.parseFloat(jDiferenciaM2.getText());
                    diferenciaM3=Float.parseFloat(jDiferenciaM3.getText());
                    diferenciaM4=Float.parseFloat(jDiferenciaM4.getText());
                    if (diferenciaM1<0){
                        diferenciaM1=-(diferenciaM1);
                    }
                    if (diferenciaM2<0){
                        diferenciaM2=-(diferenciaM2);
                    }
                    if (diferenciaM3<0){
                        diferenciaM3=-(diferenciaM3);
                    }
                    if (diferenciaM4<0){
                        diferenciaM4=-(diferenciaM4);
                    }
                    
                }
            }
                else{
                    JOptionPane.showMessageDialog(rootPane, "No se ingresaron datos de marias","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (ParseException ex) {
                Logger.getLogger(Nuevo_Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            valores.setCodigoEmpresa(ValoresEstaticos.getIdEmpresa());
            SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
            Date d=df.parse(jTxtFecha.getText());
            java.sql.Date dat=new java.sql.Date(d.getTime());
            if (getDateServer().after(d)){
            valores.setFecha(dat);
            valores.setIdMaria(idMarias);
            valores.setIdPlantel(idPlantel);
            valores.setKilometraje(Float.parseFloat(jTxtKilometraje.getText()));
            valores.setLitrosDispensados(Float.parseFloat(jTextLitros.getText()));
            valores.setUnidad(jTxtUnidad.getSelectedItem().toString());
            if (valores.saveDataMySql()){
                switch (idPistola){
                    case (1):diferenciaM1=diferenciaM1-Float.parseFloat(jTextLitros.getText());
                             jDiferenciaM1.setText(String.valueOf(diferenciaM1));
                             break;
                    case (2):diferenciaM2=diferenciaM2-Float.parseFloat(jTextLitros.getText());
                             jDiferenciaM2.setText(String.valueOf(diferenciaM2));
                             break;
                    case (3):diferenciaM3=diferenciaM3-Float.parseFloat(jTextLitros.getText());
                             jDiferenciaM3.setText(String.valueOf(diferenciaM3));
                             break;
                    case (4):diferenciaM4=diferenciaM4-Float.parseFloat(jTextLitros.getText());
                             jDiferenciaM4.setText(String.valueOf(diferenciaM4));
                             break;
                }
                jLabelTotalDiesel.setText(String.valueOf(valores.getTotalLitros()));
                jLabelTotalKmRecorridos.setText(String.valueOf(valores.getTotalKmRecorridos()));
                valores.clearData();
                clearData();
                unidad=Integer.parseInt(jTxtUnidad.getSelectedItem().toString());
                
            }  
            }
            else{
             JOptionPane.showMessageDialog(rootPane, "Error la fecha digitada es mayor a la fecha actual, por favor corregirla","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
            }
        }
            catch (ParseException ex) {
            Logger.getLogger(Nuevo_Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Error. Formato de Fecha no Valido. Debe ser dd-MM-yyyy","Sistema Control Indicadores",JOptionPane.ERROR_MESSAGE);
        }
}
public void setListenerPistola(){
    jTxtPistola.addItemListener(new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
           if (e.getStateChange()==ItemEvent.SELECTED){
               idPistola=jTxtPistola.getSelectedIndex()+1;
           }
        }
    });
}
public void updateData(){
        try {
            SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
            Date d=df.parse(jTxtFecha.getText());
            java.sql.Date dat=new java.sql.Date(d.getTime());
            if (getDateServer().after(d)){
            valores.setFecha(dat);
            valores.setConsecutivo(consecutivo);
            valores.setIdPlantel(idPlantel);
            valores.setKilometraje(Float.parseFloat(jTxtKilometraje.getText()));
            valores.setLitrosDispensados(Float.parseFloat(jTextLitros.getText()));
            valores.setUnidad(jTxtUnidad.getSelectedItem().toString());
            valores.updateDataMySql();
             
            }
            else{
             JOptionPane.showMessageDialog(rootPane, "Error la fecha digitada es mayor a la fecha actual, por favor corregirla","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
            }
        }
            catch (ParseException ex) {
            Logger.getLogger(Nuevo_Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Error. Formato de Fecha no Valido. Debe ser dd-MM-yyyy","Sistema Control Indicadores",JOptionPane.ERROR_MESSAGE);
        }
}
public Timestamp getDateServer(){
        try {
            ResultSet rs=modulo.Listar("select CURRENT_TIMESTAMP() as Fecha_Servidor");
            rs.first();
            return rs.getTimestamp("Fecha_Servidor");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error no se pudo comprobar la hora. Vuelva a intentarlo y revise si tiene conexion a internet "+ex.getMessage(), "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
            return null;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelTotalKmRecorridos = new javax.swing.JLabel();
        jLabelTotalDiesel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabelTotalCarreras = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabelTotalPasajeros = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabelTotalAdultMayor = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabelTotalHorasBarras = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTxtRol = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTxtCedulas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTxtKilometraje = new javax.swing.JTextField();
        jTextLitros = new javax.swing.JTextField();
        jTxtPasajeros = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTxtRamal = new javax.swing.JComboBox();
        jTxtIngresos = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTxtFecha = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtCarreras = new javax.swing.JTextField();
        jTxtBarras = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTxtConductor = new javax.swing.JTextField();
        jTxtUnidad = new javax.swing.JComboBox();
        jButtonListado = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTxtPistola = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jDiferenciaM1 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jDiferenciaM4 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jDiferenciaM2 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabelTotalHorasBarras1 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jAperturaMaria1 = new javax.swing.JTextField();
        jAperturaMaria2 = new javax.swing.JTextField();
        jAperturaMaria3 = new javax.swing.JTextField();
        jAperturaMaria4 = new javax.swing.JTextField();
        jCierreMaria1 = new javax.swing.JTextField();
        jCierreMaria2 = new javax.swing.JTextField();
        jCierreMaria3 = new javax.swing.JTextField();
        jCierreMaria4 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jDiferenciaM3 = new javax.swing.JLabel();
        jEditarMarias = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Resumen Datos Ingresados");
        jLabel12.setOpaque(true);

        jLabel14.setBackground(new java.awt.Color(255, 102, 0));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Total Kilometros Recorridos");
        jLabel14.setOpaque(true);

        jLabelTotalKmRecorridos.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTotalKmRecorridos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTotalKmRecorridos.setForeground(new java.awt.Color(0, 51, 102));
        jLabelTotalKmRecorridos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalKmRecorridos.setText("0");

        jLabelTotalDiesel.setBackground(new java.awt.Color(0, 204, 0));
        jLabelTotalDiesel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTotalDiesel.setForeground(new java.awt.Color(0, 51, 102));
        jLabelTotalDiesel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalDiesel.setText("0");

        jLabel16.setBackground(new java.awt.Color(255, 102, 0));
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Total Litros Dispensados");
        jLabel16.setOpaque(true);

        jLabelTotalCarreras.setBackground(new java.awt.Color(0, 204, 0));
        jLabelTotalCarreras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTotalCarreras.setForeground(new java.awt.Color(0, 51, 102));
        jLabelTotalCarreras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalCarreras.setText("0");

        jLabel18.setBackground(new java.awt.Color(255, 102, 0));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Total Carreras");
        jLabel18.setOpaque(true);

        jLabelTotalPasajeros.setBackground(new java.awt.Color(0, 204, 0));
        jLabelTotalPasajeros.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTotalPasajeros.setForeground(new java.awt.Color(0, 51, 102));
        jLabelTotalPasajeros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalPasajeros.setText("0");

        jLabel20.setBackground(new java.awt.Color(255, 102, 0));
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Total Pasajeros");
        jLabel20.setOpaque(true);

        jLabelTotalAdultMayor.setBackground(new java.awt.Color(0, 204, 0));
        jLabelTotalAdultMayor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTotalAdultMayor.setForeground(new java.awt.Color(0, 51, 102));
        jLabelTotalAdultMayor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalAdultMayor.setText("0");

        jLabel22.setBackground(new java.awt.Color(255, 102, 0));
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Total Adulto Mayor");
        jLabel22.setOpaque(true);

        jLabelTotalHorasBarras.setBackground(new java.awt.Color(0, 204, 0));
        jLabelTotalHorasBarras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTotalHorasBarras.setForeground(new java.awt.Color(0, 51, 102));
        jLabelTotalHorasBarras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalHorasBarras.setText("0");

        jLabel24.setBackground(new java.awt.Color(255, 102, 0));
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Horas Barras");
        jLabel24.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                .addComponent(jLabelTotalKmRecorridos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTotalCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                .addComponent(jLabelTotalAdultMayor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTotalHorasBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTotalDiesel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTotalPasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalKmRecorridos)
                    .addComponent(jLabelTotalDiesel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalCarreras)
                    .addComponent(jLabelTotalPasajeros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalAdultMayor)
                    .addComponent(jLabelTotalHorasBarras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 153, 204), new java.awt.Color(0, 102, 153)));

        jLabel39.setBackground(new java.awt.Color(0, 0, 0));
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("INGRESO DE DATOS DIARIOS");
        jLabel39.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(188, 74, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ingresos");
        jLabel3.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(188, 74, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Cantidad Pasajeros");
        jLabel4.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(188, 74, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Plantel");
        jLabel6.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(188, 74, 0));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Unidad");
        jLabel7.setOpaque(true);

        jTxtRol.setBackground(new java.awt.Color(102, 102, 102));
        jTxtRol.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtRol.setForeground(new java.awt.Color(255, 255, 255));
        jTxtRol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtRol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtRol.setEnabled(false);
        jTxtRol.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtRolActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(188, 74, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Conductor");
        jLabel2.setOpaque(true);

        jTxtCedulas.setBackground(new java.awt.Color(102, 102, 102));
        jTxtCedulas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtCedulas.setForeground(new java.awt.Color(255, 255, 255));
        jTxtCedulas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtCedulas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtCedulas.setEnabled(false);
        jTxtCedulas.setSelectionColor(new java.awt.Color(0, 51, 102));

        jLabel9.setBackground(new java.awt.Color(188, 74, 0));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Adulto Mayor");
        jLabel9.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(188, 74, 0));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Litros Dispensados");
        jLabel8.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(188, 74, 0));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Horas Rol");
        jLabel11.setOpaque(true);

        jTxtKilometraje.setBackground(new java.awt.Color(102, 102, 102));
        jTxtKilometraje.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtKilometraje.setForeground(new java.awt.Color(255, 255, 255));
        jTxtKilometraje.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtKilometraje.setText("0");
        jTxtKilometraje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtKilometraje.setSelectionColor(new java.awt.Color(0, 51, 102));

        jTextLitros.setBackground(new java.awt.Color(102, 102, 102));
        jTextLitros.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextLitros.setForeground(new java.awt.Color(255, 255, 255));
        jTextLitros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextLitros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextLitros.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTextLitros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextLitrosKeyPressed(evt);
            }
        });

        jTxtPasajeros.setBackground(new java.awt.Color(102, 102, 102));
        jTxtPasajeros.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtPasajeros.setForeground(new java.awt.Color(255, 255, 255));
        jTxtPasajeros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtPasajeros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtPasajeros.setEnabled(false);
        jTxtPasajeros.setSelectionColor(new java.awt.Color(0, 51, 102));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/save_1.png"))); // NOI18N
        jButton1.setText("GUARDAR");
        jButton1.setBorder(null);
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTxtRamal.setBackground(new java.awt.Color(102, 102, 102));
        jTxtRamal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtRamal.setForeground(new java.awt.Color(255, 255, 255));

        jTxtIngresos.setBackground(new java.awt.Color(102, 102, 102));
        jTxtIngresos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtIngresos.setForeground(new java.awt.Color(255, 255, 255));
        jTxtIngresos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtIngresos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtIngresos.setEnabled(false);
        jTxtIngresos.setSelectionColor(new java.awt.Color(0, 51, 102));

        jLabel38.setBackground(new java.awt.Color(188, 74, 0));
        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Kilometraje");
        jLabel38.setOpaque(true);

        jTxtFecha.setBackground(new java.awt.Color(102, 102, 102));
        jTxtFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTxtFecha.setForeground(new java.awt.Color(255, 255, 255));
        jTxtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxtFechaMouseClicked(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(188, 74, 0));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("FECHA");
        jLabel15.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(188, 74, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Medias Carreras");
        jLabel5.setOpaque(true);

        jTxtCarreras.setBackground(new java.awt.Color(102, 102, 102));
        jTxtCarreras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtCarreras.setForeground(new java.awt.Color(255, 255, 255));
        jTxtCarreras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtCarreras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtCarreras.setEnabled(false);
        jTxtCarreras.setSelectionColor(new java.awt.Color(0, 51, 102));

        jTxtBarras.setBackground(new java.awt.Color(102, 102, 102));
        jTxtBarras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtBarras.setForeground(new java.awt.Color(255, 255, 255));
        jTxtBarras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtBarras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtBarras.setEnabled(false);
        jTxtBarras.setSelectionColor(new java.awt.Color(0, 51, 102));

        jLabel10.setBackground(new java.awt.Color(188, 74, 0));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Horas Barras");
        jLabel10.setOpaque(true);

        jTxtConductor.setBackground(new java.awt.Color(102, 102, 102));
        jTxtConductor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTxtConductor.setForeground(new java.awt.Color(204, 204, 204));
        jTxtConductor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTxtConductor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTxtConductor.setEnabled(false);
        jTxtConductor.setSelectionColor(new java.awt.Color(0, 51, 102));
        jTxtConductor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtConductorFocusGained(evt);
            }
        });

        jTxtUnidad.setBackground(new java.awt.Color(102, 102, 102));
        jTxtUnidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtUnidad.setForeground(new java.awt.Color(255, 255, 255));

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

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/save_1.png"))); // NOI18N
        jButton3.setText("ACTUALIZAR");
        jButton3.setBorder(null);
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(51, 51, 51));
        jButton10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/list.png"))); // NOI18N
        jButton10.setText("NUEVAS MARIAS");
        jButton10.setBorder(null);
        jButton10.setFocusable(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTxtPistola.setBackground(new java.awt.Color(102, 102, 102));
        jTxtPistola.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTxtPistola.setForeground(new java.awt.Color(255, 255, 255));
        jTxtPistola.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));

        jLabel31.setBackground(new java.awt.Color(188, 74, 0));
        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Pistola");
        jLabel31.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTxtFecha, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(27, 27, 27)
                                .addComponent(jButtonListado, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jTxtPasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTxtRamal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTxtUnidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTxtCedulas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                            .addComponent(jTxtIngresos))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTxtCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTxtBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTxtPistola, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextLitros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtRol, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtKilometraje)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonListado, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel38)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtRamal, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextLitros, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtPistola, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtBarras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtCedulas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtRol, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtPasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/SCI.png"))); // NOI18N
        jLabel17.setOpaque(true);

        jLabelTitulo.setBackground(new java.awt.Color(51, 51, 51));
        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("INGRESO DE DATOS AL SISTEMA SCI");
        jLabelTitulo.setOpaque(true);

        jButton9.setBackground(new java.awt.Color(51, 51, 51));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/reportes.png"))); // NOI18N
        jButton9.setText("Reporteria");
        jButton9.setBorder(null);
        jButton9.setFocusable(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 102, 153));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Control de Ingresos Diarios");

        jLabelVersion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelVersion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVersion.setText("Version.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 1034, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(jLabelVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(166, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jLabelVersion)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel6.setBackground(new java.awt.Color(255, 204, 153));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel21.setBackground(new java.awt.Color(0, 0, 0));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Detalle de Marias");
        jLabel21.setOpaque(true);

        jLabel23.setBackground(new java.awt.Color(255, 102, 0));
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Maria 1");
        jLabel23.setOpaque(true);

        jLabel25.setBackground(new java.awt.Color(255, 102, 0));
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Maria 2");
        jLabel25.setOpaque(true);

        jDiferenciaM1.setBackground(new java.awt.Color(0, 204, 0));
        jDiferenciaM1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jDiferenciaM1.setForeground(new java.awt.Color(0, 51, 102));
        jDiferenciaM1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jDiferenciaM1.setText("0");

        jLabel26.setBackground(new java.awt.Color(255, 102, 0));
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Inicio");
        jLabel26.setOpaque(true);

        jDiferenciaM4.setBackground(new java.awt.Color(0, 204, 0));
        jDiferenciaM4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jDiferenciaM4.setForeground(new java.awt.Color(0, 51, 102));
        jDiferenciaM4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jDiferenciaM4.setText("0");

        jLabel27.setBackground(new java.awt.Color(255, 102, 0));
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Maria 3");
        jLabel27.setOpaque(true);

        jDiferenciaM2.setBackground(new java.awt.Color(0, 204, 0));
        jDiferenciaM2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jDiferenciaM2.setForeground(new java.awt.Color(0, 51, 102));
        jDiferenciaM2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jDiferenciaM2.setText("0");

        jLabel28.setBackground(new java.awt.Color(255, 102, 0));
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Fin");
        jLabel28.setOpaque(true);

        jLabelTotalHorasBarras1.setBackground(new java.awt.Color(0, 204, 0));
        jLabelTotalHorasBarras1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTotalHorasBarras1.setForeground(new java.awt.Color(0, 51, 102));
        jLabelTotalHorasBarras1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTotalHorasBarras1.setText("0");

        jLabel29.setBackground(new java.awt.Color(255, 102, 0));
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Maria 4");
        jLabel29.setOpaque(true);

        jAperturaMaria1.setBackground(new java.awt.Color(255, 204, 102));
        jAperturaMaria1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jAperturaMaria1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jAperturaMaria1.setText("0");
        jAperturaMaria1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jAperturaMaria1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jAperturaMaria1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jAperturaMaria1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jAperturaMaria1KeyTyped(evt);
            }
        });

        jAperturaMaria2.setBackground(new java.awt.Color(255, 204, 102));
        jAperturaMaria2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jAperturaMaria2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jAperturaMaria2.setText("0");
        jAperturaMaria2.setToolTipText("");
        jAperturaMaria2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jAperturaMaria2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jAperturaMaria2KeyReleased(evt);
            }
        });

        jAperturaMaria3.setBackground(new java.awt.Color(255, 204, 102));
        jAperturaMaria3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jAperturaMaria3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jAperturaMaria3.setText("0");
        jAperturaMaria3.setToolTipText("");
        jAperturaMaria3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jAperturaMaria3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jAperturaMaria3KeyReleased(evt);
            }
        });

        jAperturaMaria4.setBackground(new java.awt.Color(255, 204, 102));
        jAperturaMaria4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jAperturaMaria4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jAperturaMaria4.setText("0");
        jAperturaMaria4.setToolTipText("");
        jAperturaMaria4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jAperturaMaria4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jAperturaMaria4KeyReleased(evt);
            }
        });

        jCierreMaria1.setBackground(new java.awt.Color(255, 204, 102));
        jCierreMaria1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jCierreMaria1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jCierreMaria1.setText("0");
        jCierreMaria1.setToolTipText("");
        jCierreMaria1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jCierreMaria1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCierreMaria1KeyReleased(evt);
            }
        });

        jCierreMaria2.setBackground(new java.awt.Color(255, 204, 102));
        jCierreMaria2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jCierreMaria2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jCierreMaria2.setText("0");
        jCierreMaria2.setToolTipText("");
        jCierreMaria2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jCierreMaria2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCierreMaria2KeyReleased(evt);
            }
        });

        jCierreMaria3.setBackground(new java.awt.Color(255, 204, 102));
        jCierreMaria3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jCierreMaria3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jCierreMaria3.setText("0");
        jCierreMaria3.setToolTipText("");
        jCierreMaria3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jCierreMaria3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCierreMaria3KeyReleased(evt);
            }
        });

        jCierreMaria4.setBackground(new java.awt.Color(255, 204, 102));
        jCierreMaria4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jCierreMaria4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jCierreMaria4.setText("0");
        jCierreMaria4.setToolTipText("");
        jCierreMaria4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jCierreMaria4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCierreMaria4KeyReleased(evt);
            }
        });

        jLabel30.setBackground(new java.awt.Color(255, 102, 0));
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("DIF");
        jLabel30.setOpaque(true);

        jDiferenciaM3.setBackground(new java.awt.Color(0, 204, 0));
        jDiferenciaM3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jDiferenciaM3.setForeground(new java.awt.Color(0, 51, 102));
        jDiferenciaM3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jDiferenciaM3.setText("0");

        jEditarMarias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jEditarMarias.setForeground(new java.awt.Color(255, 102, 0));
        jEditarMarias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jEditarMarias.setText("EDITAR MARIAS");
        jEditarMarias.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        jEditarMarias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jEditarMariasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jAperturaMaria1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jAperturaMaria2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jAperturaMaria3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jAperturaMaria4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jCierreMaria1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jCierreMaria2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jCierreMaria3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jCierreMaria4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jDiferenciaM1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDiferenciaM2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDiferenciaM3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addComponent(jEditarMarias, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jDiferenciaM4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTotalHorasBarras1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel23)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jAperturaMaria1)
                    .addComponent(jAperturaMaria4)
                    .addComponent(jAperturaMaria2)
                    .addComponent(jAperturaMaria3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCierreMaria2)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jCierreMaria1)
                    .addComponent(jCierreMaria3)
                    .addComponent(jCierreMaria4))
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jDiferenciaM1)
                    .addComponent(jDiferenciaM4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDiferenciaM2)
                    .addComponent(jLabelTotalHorasBarras1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDiferenciaM3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEditarMarias)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel13.setBackground(new java.awt.Color(0, 51, 102));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("SELECCIONE UN TIPO DE CONTROL");
        jLabel13.setOpaque(true);

        jButton2.setBackground(new java.awt.Color(0, 204, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
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

        jButton11.setBackground(new java.awt.Color(51, 51, 51));
        jButton11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 153, 51));
        jButton11.setText("Ordenes de Trabajo");
        jButton11.setFocusable(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
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
                .addComponent(jButton11)
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
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (ValoresEstaticos.getIdEmpresa()==3)
            {
                if (unidad!=Integer.parseInt(jTxtUnidad.getSelectedItem().toString())){
                    saveData();
                }
                else{
                     int r=JOptionPane.showConfirmDialog(rootPane, "Error. Posiblemente no ha seleccionado una unidad diferente luego de haber guardado un registro. Desea guardar un nuevo registro para la unidad "+unidad, "Sistema de Control de Indicadores", JOptionPane.INFORMATION_MESSAGE);
                     if (r==JOptionPane.YES_OPTION){
                         saveData();
                     }
                     else{
                         jTxtUnidad.grabFocus();
                     }
                     
                }
                
            }
            else{
            valores.setCantidadAdultoMayor(Integer.parseInt(jTxtCedulas.getText()));
            valores.setCantidadCarreras(Float.parseFloat(jTxtCarreras.getText()));
            valores.setCantidadPasajeros(Integer.parseInt(jTxtPasajeros.getText()));
            valores.setCodigoConductor(jTxtConductor.getText().toString());
            valores.setCodigoEmpresa(ValoresEstaticos.getIdEmpresa());
            SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
            Date d=df.parse(jTxtFecha.getText());
            java.sql.Date dat=new java.sql.Date(d.getTime());
            valores.setFecha(dat);
            valores.setHorasBarras(Float.parseFloat(jTxtBarras.getText()));
            valores.setHorasRol(Float.parseFloat(jTxtRol.getText()));
            valores.setIngresos(Float.parseFloat(jTxtIngresos.getText()));
            valores.setKilometraje(Float.parseFloat(jTxtKilometraje.getText()));
            valores.setLitrosDispensados(Float.parseFloat(jTextLitros.getText()));
            valores.setRamal(jTxtRamal.getSelectedItem().toString());
            valores.setUnidad(jTxtUnidad.getSelectedItem().toString());
            if (valores.saveDataMySql()){
                jLabelTotalAdultMayor.setText(String.valueOf(valores.getTotalTotalAdultMayor()));
                jLabelTotalCarreras.setText(String.valueOf(valores.getTotalCarreras()));
                jLabelTotalDiesel.setText(String.valueOf(valores.getTotalLitros()));
                jLabelTotalHorasBarras.setText(String.valueOf(valores.getTotalHorasBarras()));
                jLabelTotalKmRecorridos.setText(String.valueOf(valores.getTotalKmRecorridos()));
                jLabelTotalPasajeros.setText(String.valueOf(valores.getTotalTotalAdultMayor()));
                valores.clearData();
                clearData();
            }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Control_Llantas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTxtRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtRolActionPerformed

    private void jTxtFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtFechaMouseClicked
        // TODO add your handling code here:
        this.jTxtFecha.setText(new DatePicker(this).setPickedDate());
        
    }//GEN-LAST:event_jTxtFechaMouseClicked

    private void jTxtConductorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtConductorFocusGained
        // TODO add your handling code here:
        jTxtRamal.grabFocus();
        BusquedaConductor bus=new BusquedaConductor(this, true);
        bus.setVisible(true);
        this.jTxtConductor.setText(bus.getConductor());
    }//GEN-LAST:event_jTxtConductorFocusGained

    private void jTextLitrosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextLitrosKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButton1ActionPerformed(null);
        }
    }//GEN-LAST:event_jTextLitrosKeyPressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
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
            JOptionPane.showMessageDialog(rootPane, "Usuario "+DataUser.username+" no tiene los permisos necesarios para realizar esta función","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
            
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButtonListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListadoActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListadoControlIngresos().setVisible(true);
                
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonListadoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (isActualizacion){
        try {
            if (ValoresEstaticos.getIdEmpresa()==3)
            {
                if (unidad!=Integer.parseInt(jTxtUnidad.getSelectedItem().toString())){
                    updateData();
                }
                else{
                     int r=JOptionPane.showConfirmDialog(rootPane, "Error. Posiblemente no ha seleccionado una unidad diferente luego de haber guardado un registro. Desea guardar un nuevo registro para la unidad "+unidad, "Sistema de Control de Indicadores", JOptionPane.INFORMATION_MESSAGE);
                     if (r==JOptionPane.YES_OPTION){
                         updateData();
                     }
                     else{
                         jTxtUnidad.grabFocus();
                     }
                     
                }
                
            }
            else{
            valores.setCantidadAdultoMayor(Integer.parseInt(jTxtCedulas.getText()));
            valores.setCantidadCarreras(Float.parseFloat(jTxtCarreras.getText()));
            valores.setCantidadPasajeros(Integer.parseInt(jTxtPasajeros.getText()));
            valores.setCodigoConductor(jTxtConductor.getText().toString());
            valores.setCodigoEmpresa(ValoresEstaticos.getIdEmpresa());
            SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
            Date d=df.parse(jTxtFecha.getText());
            java.sql.Date dat=new java.sql.Date(d.getTime());
            valores.setFecha(dat);
            valores.setHorasBarras(Float.parseFloat(jTxtBarras.getText()));
            valores.setHorasRol(Float.parseFloat(jTxtRol.getText()));
            valores.setIngresos(Float.parseFloat(jTxtIngresos.getText()));
            valores.setKilometraje(Float.parseFloat(jTxtKilometraje.getText()));
            valores.setLitrosDispensados(Float.parseFloat(jTextLitros.getText()));
            valores.setRamal(jTxtRamal.getSelectedItem().toString());
            valores.setUnidad(jTxtUnidad.getSelectedItem().toString());
            if (valores.saveDataMySql()){
                jLabelTotalAdultMayor.setText(String.valueOf(valores.getTotalTotalAdultMayor()));
                jLabelTotalCarreras.setText(String.valueOf(valores.getTotalCarreras()));
                jLabelTotalDiesel.setText(String.valueOf(valores.getTotalLitros()));
                jLabelTotalHorasBarras.setText(String.valueOf(valores.getTotalHorasBarras()));
                jLabelTotalKmRecorridos.setText(String.valueOf(valores.getTotalKmRecorridos()));
                jLabelTotalPasajeros.setText(String.valueOf(valores.getTotalTotalAdultMayor()));
                valores.clearData();
                clearData();
            }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Control_Llantas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       }
        else{
            JOptionPane.showMessageDialog(rootPane, "Debe seleccionar primero el registro a modificar","Sistema de Control de Indicadores",JOptionPane.INFORMATION_MESSAGE);
            java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListadoControlIngresos().setVisible(true);
            }
        });
            this.dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jAperturaMaria1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jAperturaMaria1KeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jAperturaMaria1KeyPressed

    private void jAperturaMaria1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jAperturaMaria1KeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jAperturaMaria1KeyTyped

    private void jAperturaMaria1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jAperturaMaria1KeyReleased
        // TODO add your handling code here:
        if (insertMarias){
        try {
        jDiferenciaM1.setText(String.valueOf(Float.parseFloat(jCierreMaria1.getText())-Float.parseFloat(jAperturaMaria1.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM1.setText("ERROR");
        }
        }
    }//GEN-LAST:event_jAperturaMaria1KeyReleased

    private void jCierreMaria1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCierreMaria1KeyReleased
        // TODO add your handling code here:
        if (insertMarias){
        try {
        jDiferenciaM1.setText(String.valueOf(Float.parseFloat(jCierreMaria1.getText())-Float.parseFloat(jAperturaMaria1.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM1.setText("ERROR");
        }
        }
    }//GEN-LAST:event_jCierreMaria1KeyReleased

    private void jAperturaMaria2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jAperturaMaria2KeyReleased
        // TODO add your handling code here:
        if (insertMarias){
        try {
        jDiferenciaM2.setText(String.valueOf(Integer.parseInt(jCierreMaria2.getText())-Integer.parseInt(jAperturaMaria2.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM2.setText("ERROR");
        }
        }
    }//GEN-LAST:event_jAperturaMaria2KeyReleased

    private void jCierreMaria2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCierreMaria2KeyReleased
        // TODO add your handling code here:
        if (insertMarias){
        try {
        jDiferenciaM2.setText(String.valueOf(Integer.parseInt(jCierreMaria2.getText())-Integer.parseInt(jAperturaMaria2.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM2.setText("ERROR");
        }}
    }//GEN-LAST:event_jCierreMaria2KeyReleased

    private void jAperturaMaria3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jAperturaMaria3KeyReleased
        // TODO add your handling code here:.
        if (insertMarias){
        try {
        jDiferenciaM3.setText(String.valueOf(Integer.parseInt(jCierreMaria3.getText())-Integer.parseInt(jAperturaMaria3.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM3.setText("ERROR");
        }
        }
    }//GEN-LAST:event_jAperturaMaria3KeyReleased

    private void jCierreMaria3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCierreMaria3KeyReleased
        // TODO add your handling code here:
        if (insertMarias){
        try {
        jDiferenciaM3.setText(String.valueOf(Integer.parseInt(jCierreMaria3.getText())-Integer.parseInt(jAperturaMaria3.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM3.setText("ERROR");
        }
        }
    }//GEN-LAST:event_jCierreMaria3KeyReleased

    private void jAperturaMaria4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jAperturaMaria4KeyReleased
        // TODO add your handling code here:
        if (insertMarias){
        try {
        jDiferenciaM4.setText(String.valueOf(Integer.parseInt(jCierreMaria4.getText())-Integer.parseInt(jAperturaMaria4.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM4.setText("ERROR");
        }
        }
    }//GEN-LAST:event_jAperturaMaria4KeyReleased

    private void jCierreMaria4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCierreMaria4KeyReleased
        // TODO add your handling code here:
        if (insertMarias){
        try {
        jDiferenciaM4.setText(String.valueOf(Integer.parseInt(jCierreMaria4.getText())-Integer.parseInt(jAperturaMaria4.getText())));}
        catch (NumberFormatException e){
            jDiferenciaM4.setText("ERROR");
        }
        }
    }//GEN-LAST:event_jCierreMaria4KeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        jAperturaMaria1.setText("0");
        jCierreMaria1.setText("0");
        jDiferenciaM1.setText("0");
        jAperturaMaria2.setText("0");
        jCierreMaria2.setText("0");
        jDiferenciaM2.setText("0");
        jAperturaMaria3.setText("0");
        jCierreMaria3.setText("0");
        jDiferenciaM3.setText("0");
        jAperturaMaria4.setText("0");
        jCierreMaria4.setText("0");
        jDiferenciaM4.setText("0");
        marias.clearData();
        jAperturaMaria1.setEditable(true);
        jCierreMaria1.setEditable(true);
        jAperturaMaria2.setEditable(true);
        jCierreMaria2.setEditable(true);
        jAperturaMaria3.setEditable(true);
        jCierreMaria3.setEditable(true);
        jAperturaMaria4.setEditable(true);
        jCierreMaria4.setEditable(true);
        insertMarias=true;
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jEditarMariasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEditarMariasMouseClicked
        // TODO add your handling code here:
        if (actualizaMarias){
           try {
                if (testMarias()){
                
                marias.setAperturaM1(Integer.parseInt(jAperturaMaria1.getText()));
                marias.setCierreM1(Integer.parseInt(jCierreMaria1.getText()));
                marias.setAperturaM2(Integer.parseInt(jAperturaMaria2.getText()));
                marias.setCierreM2(Integer.parseInt(jCierreMaria2.getText()));
                marias.setAperturaM3(Integer.parseInt(jAperturaMaria3.getText()));
                marias.setCierreM3(Integer.parseInt(jCierreMaria3.getText()));
                marias.setAperturaM4(Integer.parseInt(jAperturaMaria4.getText()));
                marias.setCierreM4(Integer.parseInt(jCierreMaria4.getText()));
                marias.setFecha(new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(jTxtFecha.getText()).getTime()));
                marias.setIdPlantel(idPlantel);
                if (marias.updateControlMarias(idMarias)==false){
                }
                else{
                    idMarias=marias.getIdMaria();
                    insertMarias=false;
                    jAperturaMaria1.setEditable(false);
                    jCierreMaria1.setEditable(false);
                    jAperturaMaria2.setEditable(false);
                    jCierreMaria2.setEditable(false);
                    jAperturaMaria3.setEditable(false);
                    jCierreMaria3.setEditable(false);
                    jAperturaMaria4.setEditable(false);
                    jCierreMaria4.setEditable(false);
                    diferenciaM1=Integer.parseInt(jDiferenciaM1.getText());
                    diferenciaM2=Integer.parseInt(jDiferenciaM2.getText());
                    diferenciaM3=Integer.parseInt(jDiferenciaM3.getText());
                    diferenciaM4=Integer.parseInt(jDiferenciaM4.getText());
                    if (diferenciaM1<0){
                        diferenciaM1=-(diferenciaM1);
                    }
                    if (diferenciaM2<0){
                        diferenciaM2=-(diferenciaM2);
                    }
                    if (diferenciaM3<0){
                        diferenciaM3=-(diferenciaM3);
                    }
                    if (diferenciaM4<0){
                        diferenciaM4=-(diferenciaM4);
                    }
                    
                }
            }
                else{
                    JOptionPane.showMessageDialog(rootPane, "No se ingresaron datos de marias","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
                    ;
                }
            } catch (ParseException ex) {
                Logger.getLogger(Nuevo_Ingreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           
        
        else{
            jAperturaMaria1.setEditable(true);
            jCierreMaria1.setEditable(true);
            jAperturaMaria2.setEditable(true);
            jCierreMaria2.setEditable(true);
            jAperturaMaria3.setEditable(true);
            jCierreMaria3.setEditable(true);
            jAperturaMaria4.setEditable(true);
            jCierreMaria4.setEditable(true);
            jEditarMarias.setText("Actualizar Marias");
            actualizaMarias=true;
        }
        
    }//GEN-LAST:event_jEditarMariasMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
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

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
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
    }//GEN-LAST:event_jButton11ActionPerformed
    
    public void getListRamales(){
        try {
            final ResultSet rs=modulo.Listar("select IdPlantel, Plantel from Planteles_Empresa Where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'");
            
            while (rs.next()){
                jTxtRamal.addItem(rs.getString("Plantel"));
                
            }
            jTxtRamal.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    try {
                        idPlantel=rs.getInt("IdPlantel");
                    } catch (SQLException ex) {
                        idPlantel=0;
                    }
                }
            });
            rs.first();
            idPlantel=rs.getInt("IdPlantel");
            
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(Control_Llantas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Error en conexión con el servidor SQL. El sistema se cerrará. Intente nuevamente");
            System.exit(0);
        }
    }
    public void getListUnidades(){
        try {
            ResultSet rs=modulo.Listar("select Unidad from UnidadesxEmpresa Where IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' order by Unidad");
            
            while (rs.next()){
                jTxtUnidad.addItem(rs.getString("Unidad"));
                
            }
            jTxtUnidad.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange()==ItemEvent.SELECTED){
                        try {
                            Date d=df.parse(jTxtFecha.getText());
                            rskm=modulosol.Listar("CALL `CalculoKilometraje`('"+new java.sql.Date(d.getTime())+"', '"+jTxtUnidad.getSelectedItem().toString()+"')");
                            if (rskm!=null){
                                rskm.first();
                                jTxtKilometraje.setText(String.valueOf(rskm.getFloat("km")));
                                jTxtPasajeros.setText(String.valueOf(rskm.getInt("entradas")));
                                
                            }
                        } catch (ParseException | SQLException ex) {
                            Logger.getLogger(Nuevo_Ingreso.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            });
            
        } catch (SQLException ex) {
            Logger.getLogger(Control_Llantas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Error en conexión con el servidor SQL. El sistema se cerrará. Intente nuevamente");
            System.exit(0);
        }
        catch (NullPointerException ex) {
            Logger.getLogger(Control_Llantas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Error en conexión con el servidor SQL. El sistema se cerrará. Intente nuevamente");
            System.exit(0);
        }
    }
    public void clearData(){
        jTextLitros.setText("");
        jTxtBarras.setText("");
        jTxtCarreras.setText("");
        jTxtCedulas.setText("");        
       isActualizacion=false;
        jTxtIngresos.setText("");
        if (ValoresEstaticos.getIdEmpresa()==3){
            jTxtKilometraje.setText("0");}
        else{
            jTxtKilometraje.setText("");
        }
        jTxtPasajeros.setText("");        
        jTxtRol.setText("");
        
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
            java.util.logging.Logger.getLogger(Control_Llantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Control_Llantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Control_Llantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Control_Llantas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Control_Llantas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jAperturaMaria1;
    private javax.swing.JTextField jAperturaMaria2;
    private javax.swing.JTextField jAperturaMaria3;
    private javax.swing.JTextField jAperturaMaria4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonListado;
    private javax.swing.JTextField jCierreMaria1;
    private javax.swing.JTextField jCierreMaria2;
    private javax.swing.JTextField jCierreMaria3;
    private javax.swing.JTextField jCierreMaria4;
    private javax.swing.JLabel jDiferenciaM1;
    private javax.swing.JLabel jDiferenciaM2;
    private javax.swing.JLabel jDiferenciaM3;
    private javax.swing.JLabel jDiferenciaM4;
    private javax.swing.JLabel jEditarMarias;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelTotalAdultMayor;
    private javax.swing.JLabel jLabelTotalCarreras;
    private javax.swing.JLabel jLabelTotalDiesel;
    private javax.swing.JLabel jLabelTotalHorasBarras;
    private javax.swing.JLabel jLabelTotalHorasBarras1;
    private javax.swing.JLabel jLabelTotalKmRecorridos;
    private javax.swing.JLabel jLabelTotalPasajeros;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextLitros;
    private javax.swing.JTextField jTxtBarras;
    private javax.swing.JTextField jTxtCarreras;
    private javax.swing.JTextField jTxtCedulas;
    private javax.swing.JTextField jTxtConductor;
    private javax.swing.JTextField jTxtFecha;
    private javax.swing.JTextField jTxtIngresos;
    private javax.swing.JTextField jTxtKilometraje;
    private javax.swing.JTextField jTxtPasajeros;
    private javax.swing.JComboBox jTxtPistola;
    private javax.swing.JComboBox jTxtRamal;
    private javax.swing.JTextField jTxtRol;
    private javax.swing.JComboBox jTxtUnidad;
    // End of variables declaration//GEN-END:variables
    static ValoresIngreso valores=new ValoresIngreso();
    static ModuloMySQL modulo=new ModuloMySQL();
    static int unidad;
    static int idPlantel;
    static int consecutivo;
    static boolean isActualizacion;
    static boolean actualizaMarias=false;
    static boolean insertMarias=true;
    static int idMarias;
    static int idPistola=1;
    static float diferenciaM1;
    static float diferenciaM2;
    static float diferenciaM3;
    static float diferenciaM4;
    static ModuloMySQLSOL modulosol=new ModuloMySQLSOL();
    static ControlMarias marias=new ControlMarias();
    static SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
    ResultSet rskm;
}
