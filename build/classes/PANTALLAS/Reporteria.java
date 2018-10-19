/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PANTALLAS;

import CLASES.DataUser;
import CLASES.ModuloMySQL;
import CLASES.ValoresEstaticos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author erobles
 */
public class Reporteria extends javax.swing.JFrame {

    /**
     * Creates new form Reporteria
     */
    public Reporteria() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();   
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        function=0;
        jButtonReportexUnidad.setBackground(Color.ORANGE);
        jButtonReportexRamal.setBackground(Color.BLACK);
        jButtonReportexConductor.setBackground(Color.BLACK);
        jButtonReporteEntreDias.setBackground(Color.BLACK);
        getListEmpresas(); 
        Image i=new ImageIcon("C://SCI//SCI.png").getImage();
        this.setIconImage(i);
        //ValoresEstaticos.espere.setVisible(false);
        //setNamesButtons();
        
    }
    public ResultSet getDataFromSQL(){
        
        if (reportselected<4){
        try {
            SimpleDateFormat dat=new SimpleDateFormat("dd-MM-yyyy");
            Date dateini=dat.parse(jTextParametro1.getText());
            Date datefin=dat.parse(jTxtParametro2.getText());            
            Timestamp fechaini=new Timestamp(dateini.getTime());
            Timestamp fechafin=new Timestamp(datefin.getTime());
            ResultSet rs=modulo.Listar("Select Fecha, CodConductor, Ramal, Unidad, Eficiencia_Horas_Laboradas, Marcas_Totales, Marcas_Efectivas, Marcas_Totales_Carreras, Marcas_Efectivas_Carreras, Marcas_Kilometro, Porcentaje_AdultoMayor, Litros_Carrera, Litros_Pasajero, Litros_Km, Km_Litros, Dinero_Carrera, Dinero_Km from Ingreso_Datos where Fecha BETWEEN '"+fechaini+"' and '"+fechafin+"'");
            return rs;
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Error en formato de fecha");
                        return null;
                    //Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
        }}
        else{
          try {
              String s = null;
              Timestamp fechaini=null;
              Timestamp fechafin=null;
            SimpleDateFormat dat=new SimpleDateFormat("dd-MM-yyyy");
            if ((reportselected==4)==false&(reportselected==10)==false&(reportselected==8)==false&(reportselected==11)==false){
            Date dateini=dat.parse(jTextParametro1.getText());
            fechaini=new Timestamp(dateini.getTime());}
            if (jTxtParametro2.isEnabled()){
            Date datefin=dat.parse(jTxtParametro2.getText());
            fechafin=new Timestamp(datefin.getTime());}         
           
           
            if (reportselected==4){
                s="Select Fecha, Unidad, Kilometraje, Cod_Llanta_Sale, Profundidad_Llanta_Sale, Posicion_Llanta_Sale, Destino_Llanta_Sale, Cod_Llanta_Entra, Profundidad_Llanta_Entra, Presion_Aire_Llanta_Entra from Mantenimiento_MovimientoLlantas where (Cod_Llanta_Sale = '"+ValoresEstaticos.getIdEmpresa()+"#"+jTextParametro1.getText()+"' or Cod_Llanta_Entra='"+ValoresEstaticos.getIdEmpresa()+"#"+jTextParametro1.getText()+"') and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
                //JOptionPane.showMessageDialog(rootPane, s);
            }
            else{
                if (reportselected==5){
                 s="Select Fecha, Unidad, Kilometraje, Cod_Llanta_Sale, Profundidad_Llanta_Sale, Posicion_Llanta_Sale, Destino_Llanta_Sale, Cod_Llanta_Entra, Profundidad_Llanta_Entra, Presion_Aire_Llanta_Entra from Mantenimiento_MovimientoLlantas where Fecha BETWEEN '"+fechaini+"' and '"+fechafin+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' ORDER BY Unidad, Fecha";
                }
                else{
                if (reportselected==6){
                 s="Select Fecha, Unidad, Kilometraje, Cod_Llanta_Sale, Profundidad_Llanta_Sale, Posicion_Llanta_Sale, Destino_Llanta_Sale, Cod_Llanta_Entra, Profundidad_Llanta_Entra, Presion_Aire_Llanta_Entra from Mantenimiento_MovimientoLlantas where Fecha BETWEEN '"+fechaini+"' and '"+fechafin+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' ORDER BY Fecha, Unidad";
                }
                else{
                  if (reportselected==7){
                     s="Select WEEK(Fecha), Fecha, Unidad, Kilometraje, Cod_Llanta_Sale, Profundidad_Llanta_Sale, Posicion_Llanta_Sale, Destino_Llanta_Sale, Cod_Llanta_Entra, Profundidad_Llanta_Entra, Presion_Aire_Llanta_Entra from Mantenimiento_MovimientoLlantas where Fecha BETWEEN '"+fechaini+"' and '"+fechafin+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' ORDER BY Fecha, Unidad";
                   }
                  if (reportselected==8){
                    s="Select fecha, unidad, cantidad_cuartos_galon, plantel from view_control_aceites where unidad='"+jTextParametro1.getText()+"' and nombre_empresa='"+jComboBoxListEmpresas.getSelectedItem().toString()+"' ORDER BY Fecha";
                  }
                  if (reportselected==9){
                     s="Select fecha, unidad, cantidad_cuartos_galon, plantel from view_control_aceites where fecha BETWEEN '"+fechaini+"' and '"+fechafin+"' and nombre_empresa='"+jComboBoxListEmpresas.getSelectedItem().toString()+"' ORDER BY Fecha, Unidad";
                  }
                  if (reportselected==10){
                     s="Select Fecha, Unidad, CodBateriaSaliente, Destino, CodBateriaEntrante, Kilometraje, Responsable from Mantenimiento_MovimientoBaterias where (CodBateriaSaliente ='"+jTextParametro1.getText()+"' or CodBateriaEntrante='"+jTextParametro1.getText()+"') and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' ORDER BY Fecha";
                  }
                  if (reportselected==11){
                    s="Select Fecha, Unidad, CodBateriaSaliente, Destino, CodBateriaEntrante, Kilometraje, Responsable from Mantenimiento_MovimientoBaterias where Unidad ='"+jTextParametro1.getText()+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' ORDER BY Fecha";
                  }
                  if (reportselected==12){
                  s="Select Fecha, Unidad, CodBateriaSaliente, Destino, CodBateriaEntrante, Kilometraje, Responsable from Mantenimiento_MovimientoBaterias where Fecha BETWEEN '"+fechaini+"' and '"+fechafin+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"' ORDER BY Fecha, Unidad";
                  }
                  
                }
                }
            }
            ResultSet rs=modulo.Listar(s);
            return rs;
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Error en formato de fecha");
                        return null;
                    //Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    public void setNamesButtons(){
    //if (function==1){//Se presiono boton cedulas
        
         if (reportselected<4){
        ResultSet rs=getDataFromSQL();
        int rowCount=0;
        if (rs!=null){
            try {
                rs.last();
                rowCount=rs.getRow();
                rs.beforeFirst();
                Object[] columNames={"Fecha","Conductor","Ramal","Unidad","Eficiencia_Horas","Marcas Tot","Marcas_Efectivas","Mt/C","Me/C","M/Km","% Adult. M","L/C","L/M","L/Km","Km/L","Ing/C","Ing/Km"};
                TableModel tm=new DefaultTableModel(columNames, rowCount);
               
                
                //TableModel tm=new DefaultTableModel(rowCount+1, 17);
                
                int row=1;
                int colum=1;
                while (rs.next()){
                    while (colum<=17){
                        if (colum==1){
                            SimpleDateFormat df=new SimpleDateFormat("dd/MM/yy");
                            tm.setValueAt(df.format(rs.getTimestamp(colum)), row-1, colum-1);
                            
                        }
                        else{
                        tm.setValueAt(rs.getObject(colum), row-1, colum-1);}
                        colum++;
                    }
                    colum=1;
                    row++;
                }
                 //jTableDatos.getColumnModel().getColumn(0).setMinWidth(50);
                 jTableDatos.setModel(tm);
                 jTableDatos.getColumnModel().getColumn(0).setWidth(20);
                 
            } catch (SQLException ex) {
                Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
         } 
        else{
        ResultSet rs=getDataFromSQL();
        int rowCount=0;
        if (rs!=null){
            try {
                rs.last();
                rowCount=rs.getRow();
                rs.beforeFirst();
                TableModel tm = null;
                int columns=0;
                if (reportselected<7){
                Object[] columNames={"Fecha","Unidad","Kilometraje","CodLlantaSale","Profunidad_Llanta_Sale","PosicionLlantaSale","DestinoLlantaSale","CodLlantaEntra","ProfundidadLlantaEntra","PresionAire"};
                tm=new DefaultTableModel(columNames, rowCount);
                columns=10;
                }
                if (reportselected==8||reportselected==9){
                    Object[] columNames={"Fecha","Unidad","Cuartos Galon","Lugar"};
                    tm=new DefaultTableModel(columNames, rowCount);
                    columns=4;
                }
                if (reportselected==10||reportselected==11||reportselected==12){
                    Object[] columNames={"Fecha","Unidad","Bateria Sale","Destino","Bateria Entrante","Kilometraje","Responsable"};
                    tm=new DefaultTableModel(columNames, rowCount);
                    columns=7;
                }
                if (reportselected==7)
                {
                Object[] columNames={"Semana", "Fecha","Unidad","Kilometraje","CodLlantaSale","Profunidad_Llanta_Sale","PosicionLlantaSale","DestinoLlantaSale","CodLlantaEntra","ProfundidadLlantaEntra","PresionAire"};
                tm=new DefaultTableModel(columNames, rowCount);
                columns=11;
                }
                
               
                
                //TableModel tm=new DefaultTableModel(rowCount+1, 17);
                
                int row=1;
                int colum=1;
                while (rs.next()){
                    while (colum<=columns){
                        if (reportselected!=7){
                             if (colum==1){
                            SimpleDateFormat df=new SimpleDateFormat("dd/MM/yy");
                            tm.setValueAt(df.format(rs.getTimestamp(colum)), row-1, colum-1);
                            
                            }
                            else{
                            tm.setValueAt(rs.getObject(colum), row-1, colum-1);
                             }
                        }
                        else{
                         if (colum==2){
                            SimpleDateFormat df=new SimpleDateFormat("dd/MM/yy");
                            tm.setValueAt(df.format(rs.getTimestamp(colum)), row-1, colum-1);
                            
                           }
                            else{
                            tm.setValueAt(rs.getObject(colum), row-1, colum-1);}
                        }
                        colum++;
                    }
                    colum=1;
                    row++;
                }
                 //jTableDatos.getColumnModel().getColumn(0).setMinWidth(50);
                 jTableDatos.setModel(tm);
                 jTableDatos.getColumnModel().getColumn(0).setWidth(20);
                 
            } catch (SQLException ex) {
                Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDatos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTxtParametro2 = new javax.swing.JTextField();
        jTextParametro1 = new javax.swing.JTextField();
        jLabelParametro2 = new javax.swing.JLabel();
        jLabelParametro1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButtonGenerarReporte = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jComboBoxListEmpresas = new javax.swing.JComboBox();
        jLabelParametro3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButtonReporteUnidades = new javax.swing.JButton();
        jButtonReporteCodLlanta = new javax.swing.JButton();
        jButtonReporteFechas = new javax.swing.JButton();
        jButtonReporteSemanal = new javax.swing.JButton();
        jButtonReporteRotacionLlantas = new javax.swing.JButton();
        jButtonReporteProximosCambios = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButtonReportexUnidad = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButtonReportexConductor = new javax.swing.JButton();
        jButtonReportexRamal = new javax.swing.JButton();
        jButtonReporteEntreDias = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButtonReporteAceiteUnidad = new javax.swing.JButton();
        jButtonReporteAceitefechas = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButtonReporteCodBateria = new javax.swing.JButton();
        jButtonReporteBateriasUnidades = new javax.swing.JButton();
        jButtonReporteBateriasFechas = new javax.swing.JButton();
        jButtonReporteConsumoAceites = new javax.swing.JButton();
        jButtonReporteConsumoEntreCambios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableDatos.setAutoCreateRowSorter(true);
        jTableDatos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDatos.setFillsViewportHeight(true);
        jTableDatos.setGridColor(new java.awt.Color(0, 102, 153));
        jTableDatos.setIntercellSpacing(new java.awt.Dimension(10, 10));
        jTableDatos.setRowHeight(20);
        jScrollPane1.setViewportView(jTableDatos);

        jTxtParametro2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTxtParametro2.setForeground(new java.awt.Color(0, 51, 51));
        jTxtParametro2.setToolTipText("Parametro 2 del filtro");
        jTxtParametro2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxtParametro2MouseClicked(evt);
            }
        });

        jTextParametro1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextParametro1.setForeground(new java.awt.Color(0, 51, 51));
        jTextParametro1.setToolTipText("Parametro 1 del filtro");
        jTextParametro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextParametro1MouseClicked(evt);
            }
        });

        jLabelParametro2.setBackground(new java.awt.Color(255, 102, 0));
        jLabelParametro2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelParametro2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelParametro2.setText("Fecha Final (dd/MM/yyyy)");
        jLabelParametro2.setOpaque(true);

        jLabelParametro1.setBackground(new java.awt.Color(255, 102, 0));
        jLabelParametro1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelParametro1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelParametro1.setText("Fecha Inicial (dd/MM/yyyy)");
        jLabelParametro1.setOpaque(true);

        jLabel1.setBackground(new java.awt.Color(0, 102, 153));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PARAMETROS");
        jLabel1.setOpaque(true);

        jButtonGenerarReporte.setBackground(new java.awt.Color(204, 102, 0));
        jButtonGenerarReporte.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGenerarReporte.setText("Generar Reporte");
        jButtonGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerarReporteActionPerformed(evt);
            }
        });

        jProgressBar1.setStringPainted(true);

        jComboBoxListEmpresas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBoxListEmpresas.setToolTipText("Parametro 3 del filtro");

        jLabelParametro3.setBackground(new java.awt.Color(255, 102, 0));
        jLabelParametro3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelParametro3.setForeground(new java.awt.Color(255, 255, 255));
        jLabelParametro3.setText("Empresa");
        jLabelParametro3.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextParametro1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtParametro2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelParametro1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelParametro2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxListEmpresas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelParametro3, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonGenerarReporte)
                                .addGap(118, 118, 118)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelParametro1)
                    .addComponent(jLabelParametro2)
                    .addComponent(jLabelParametro3)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextParametro1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtParametro2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxListEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
        );

        jLabel4.setBackground(new java.awt.Color(0, 102, 153));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TABLA DE DATOS");
        jLabel4.setOpaque(true);

        jButton1.setBackground(new java.awt.Color(0, 51, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ver en Tabla");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 51), 1, true));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SISTEMA DE REPORTERIA SCI");

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REPORTES DEL CONTROL DE LLANTAS");
        jLabel2.setOpaque(true);

        jButtonReporteUnidades.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteUnidades.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteUnidades.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteUnidades.setText("General Unidades");
        jButtonReporteUnidades.setFocusable(false);
        jButtonReporteUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteUnidadesActionPerformed(evt);
            }
        });

        jButtonReporteCodLlanta.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteCodLlanta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteCodLlanta.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteCodLlanta.setText("Por Codigo Llanta");
        jButtonReporteCodLlanta.setFocusable(false);
        jButtonReporteCodLlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteCodLlantaActionPerformed(evt);
            }
        });

        jButtonReporteFechas.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteFechas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteFechas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteFechas.setText("General por Fechas");
        jButtonReporteFechas.setFocusable(false);
        jButtonReporteFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteFechasActionPerformed(evt);
            }
        });

        jButtonReporteSemanal.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteSemanal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteSemanal.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteSemanal.setText("Resumen Semanal");
        jButtonReporteSemanal.setFocusable(false);
        jButtonReporteSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteSemanalActionPerformed(evt);
            }
        });

        jButtonReporteRotacionLlantas.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteRotacionLlantas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteRotacionLlantas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteRotacionLlantas.setText("Rotacion Llantas");
        jButtonReporteRotacionLlantas.setFocusable(false);
        jButtonReporteRotacionLlantas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteRotacionLlantasActionPerformed(evt);
            }
        });

        jButtonReporteProximosCambios.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteProximosCambios.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteProximosCambios.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteProximosCambios.setText("Próximos Cambios");
        jButtonReporteProximosCambios.setFocusable(false);
        jButtonReporteProximosCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteProximosCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonReporteCodLlanta)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteUnidades)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteFechas)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteSemanal)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteRotacionLlantas)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteProximosCambios)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReporteCodLlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteSemanal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteRotacionLlantas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteProximosCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jButtonReportexUnidad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReportexUnidad.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReportexUnidad.setText("Detallado por Unidad");
        jButtonReportexUnidad.setFocusable(false);
        jButtonReportexUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReportexUnidadActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REPORTES DEL CONTROL DE INGRESOS");
        jLabel3.setOpaque(true);

        jButtonReportexConductor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReportexConductor.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReportexConductor.setText("Detallado por Conductor");
        jButtonReportexConductor.setFocusable(false);
        jButtonReportexConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReportexConductorActionPerformed(evt);
            }
        });

        jButtonReportexRamal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReportexRamal.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReportexRamal.setText("Detallado Por Ramal");
        jButtonReportexRamal.setFocusable(false);
        jButtonReportexRamal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReportexRamalActionPerformed(evt);
            }
        });

        jButtonReporteEntreDias.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteEntreDias.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteEntreDias.setText("Comparativo entre Dias");
        jButtonReporteEntreDias.setFocusable(false);
        jButtonReporteEntreDias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteEntreDiasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonReportexUnidad)
                .addGap(18, 18, 18)
                .addComponent(jButtonReportexConductor)
                .addGap(18, 18, 18)
                .addComponent(jButtonReportexRamal)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteEntreDias)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReportexUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReportexConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReportexRamal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteEntreDias, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/SCI.png"))); // NOI18N
        jLabel7.setOpaque(true);

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

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("SELECCIONE EL REPORTE A GENERAR");

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("REPORTES DEL CONTROL DE ACEITES");
        jLabel8.setOpaque(true);

        jButtonReporteAceiteUnidad.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteAceiteUnidad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteAceiteUnidad.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteAceiteUnidad.setText("Por Unidad");
        jButtonReporteAceiteUnidad.setFocusable(false);
        jButtonReporteAceiteUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteAceiteUnidadActionPerformed(evt);
            }
        });

        jButtonReporteAceitefechas.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteAceitefechas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteAceitefechas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteAceitefechas.setText("Entre fechas");
        jButtonReporteAceitefechas.setFocusable(false);
        jButtonReporteAceitefechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteAceitefechasActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("REPORTES DEL CONTROL DE BATERIAS");
        jLabel9.setOpaque(true);

        jButtonReporteCodBateria.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteCodBateria.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteCodBateria.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteCodBateria.setText("Por Cod Bateria");
        jButtonReporteCodBateria.setFocusable(false);
        jButtonReporteCodBateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteCodBateriaActionPerformed(evt);
            }
        });

        jButtonReporteBateriasUnidades.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteBateriasUnidades.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteBateriasUnidades.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteBateriasUnidades.setText("General por Unidad");
        jButtonReporteBateriasUnidades.setFocusable(false);
        jButtonReporteBateriasUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteBateriasUnidadesActionPerformed(evt);
            }
        });

        jButtonReporteBateriasFechas.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteBateriasFechas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteBateriasFechas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteBateriasFechas.setText("General x Fechas");
        jButtonReporteBateriasFechas.setFocusable(false);
        jButtonReporteBateriasFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteBateriasFechasActionPerformed(evt);
            }
        });

        jButtonReporteConsumoAceites.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteConsumoAceites.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteConsumoAceites.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteConsumoAceites.setText("Consumo Por Plantel");
        jButtonReporteConsumoAceites.setFocusable(false);
        jButtonReporteConsumoAceites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteConsumoAceitesActionPerformed(evt);
            }
        });

        jButtonReporteConsumoEntreCambios.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReporteConsumoEntreCambios.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonReporteConsumoEntreCambios.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReporteConsumoEntreCambios.setText("Consumo entre Cambios");
        jButtonReporteConsumoEntreCambios.setFocusable(false);
        jButtonReporteConsumoEntreCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReporteConsumoEntreCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButtonReporteCodBateria)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteBateriasUnidades)
                .addGap(18, 18, 18)
                .addComponent(jButtonReporteBateriasFechas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButtonReporteAceiteUnidad)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonReporteAceitefechas)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonReporteConsumoAceites)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonReporteConsumoEntreCambios)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReporteAceiteUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteAceitefechas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteConsumoAceites, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteConsumoEntreCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReporteCodBateria, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteBateriasUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReporteBateriasFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonReportexUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReportexUnidadActionPerformed
       
        if (DataUser.permiso_reporteria_control_ingresos){
        setFunctionReports(0);}
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_jButtonReportexUnidadActionPerformed
    
    public void setBarrProgressDefault(){
        jProgressBar1.setString("Sistema de Control de Indicadores");
        jProgressBar1.setValue(0);
    }
    private void jButtonReportexConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReportexConductorActionPerformed
       
        if (DataUser.permiso_reporteria_control_ingresos){
        setFunctionReports(1);}
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReportexConductorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setNamesButtons();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonReportexRamalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReportexRamalActionPerformed
        
        if (DataUser.permiso_reporteria_control_ingresos){
        setFunctionReports(2);}
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReportexRamalActionPerformed

    private void jButtonGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarReporteActionPerformed
        
        generateReports(reportselected);
       
    }//GEN-LAST:event_jButtonGenerarReporteActionPerformed

    private void jButtonReporteEntreDiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteEntreDiasActionPerformed
        
        if (DataUser.permiso_reporteria_control_ingresos){
        setFunctionReports(3);}
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteEntreDiasActionPerformed

    private void jButtonReporteUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteUnidadesActionPerformed
        
        if (DataUser.permiso_reporteria_control_llantas){
            setFunctionReports(5);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteUnidadesActionPerformed

    private void jButtonReporteCodLlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteCodLlantaActionPerformed
        
        if (DataUser.permiso_reporteria_control_llantas){
        setFunctionReports(4);}
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
       
    }//GEN-LAST:event_jButtonReporteCodLlantaActionPerformed
private void setFunctionReports(int i){
    switch (i){
        /*
        INGRESOS
        0=Ingresos por unidad, 1=Ingresos por conductor, 2=Ingresos por Ramal, 4=Ingresos comparativo entre dias
        LLANTAS
        5=Por codigo llanta, 6=General por unidades, 7=General por fechas, 8= Resumen semanal, 13=Rotacion Llantas, 14=Proximos cambios
        ACEITES
        9=Por unidad, 10=Entre fechas, 15=Consumo Aceites
        BATERIAS
        11=Por codigo, 11=Por unidad, 12=General entre fechas
        */
        case(0): {
            setAllReportsFalse();
            reporteporunidad=true;
           reportselected=0;
            if (jLabelParametro1.getText().contains("Dia")||jLabelParametro1.getText().contains("Llanta")){
                jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
                jLabelParametro2.setText("Fecha Final (dd-MM-yyyy)");
                jTextParametro1.setText("");
                jTxtParametro2.setEnabled(true);
                jLabelParametro2.setEnabled(true);
            }
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.ORANGE);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(1): {
            setAllReportsFalse();
            reporteporconductor=true;
            reportselected=1;
            if (jLabelParametro1.getText().contains("Dia")||jLabelParametro1.getText().contains("Llanta")){
                jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
                jLabelParametro2.setText("Fecha Final (dd-MM-yyyy)");
                jTextParametro1.setText("");
                jTxtParametro2.setEnabled(true);
                jLabelParametro2.setEnabled(true);
            }
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.ORANGE);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(2): {
            setAllReportsFalse();
            reporteporramal=true;
            reportselected=2;
            if (jLabelParametro1.getText().contains("Dia")||jLabelParametro1.getText().contains("Llanta")){
                jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
                jLabelParametro2.setText("Fecha Final (dd-MM-yyyy)");
                jTextParametro1.setText("");
                jTxtParametro2.setEnabled(true);
                jLabelParametro2.setEnabled(true);
            }
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.ORANGE);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(3): {
            setAllReportsFalse();
            reportecompdias=true;
            reportselected=3;
            if (jLabelParametro1.getText().contains("Fecha")||jLabelParametro1.getText().contains("Llanta")){
                jLabelParametro1.setText("Dia 1 (dd-MM-yyyy)");
                jLabelParametro2.setText("Dia 2 (dd-MM-yyyy)");
                
                jTxtParametro2.setEnabled(true);
                jLabelParametro2.setEnabled(true);
            }
            jButtonReporteEntreDias.setBackground(Color.ORANGE);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(4): {
            setAllReportsFalse();
            reportecodllanta=true;
            reportselected=4;
            jLabelParametro1.setText("Codigo de Llanta");
            jTextParametro1.setText("");
            jTxtParametro2.setText("");
            
            jTxtParametro2.setEnabled(false);
            jLabelParametro2.setEnabled(false);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.ORANGE);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(5): {
            //JOptionPane.showMessageDialog(rootPane, "5");
            setAllReportsFalse();
            reportegeneralunidades=true;
            reportselected=5;
            jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
            jLabelParametro2.setText("Fecha Final(dd-MM-yyyy)");
            jTxtParametro2.setEnabled(true);
            jTextParametro1.setText("");
            jLabelParametro2.setEnabled(true);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.ORANGE);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(6): {
            //JOptionPane.showMessageDialog(rootPane, "6");
            setAllReportsFalse();
            reportegeneralfechas=true;
            reportselected=6;
            if (jLabelParametro1.getText().contains("Dia")||jLabelParametro1.getText().contains("Llanta")){
                jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
                jLabelParametro2.setText("Fecha Final(dd-MM-yyyy)");
                jTxtParametro2.setEnabled(true);
                jTextParametro1.setText("");
                jLabelParametro2.setEnabled(true);
            }
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.ORANGE);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(7): {
            setAllReportsFalse();
            reportesemanal=true;
            reportselected=7;
            if (jLabelParametro1.getText().contains("Dia")||jLabelParametro1.getText().contains("Llanta")){
                jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
                jLabelParametro2.setText("Fecha Final(dd-MM-yyyy)");
                jTextParametro1.setText("");
                jTxtParametro2.setEnabled(true);
                jLabelParametro2.setEnabled(true);
            }
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.ORANGE);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(8): {
            setAllReportsFalse();
            reporteaceiteunidad=true;
            reportselected=8;
            jLabelParametro1.setText("Unidad");
            jLabelParametro2.setText("");
            jTextParametro1.setText("");
            jTxtParametro2.setText("");
            jTxtParametro2.setEnabled(false);
            jLabelParametro2.setEnabled(false);
            
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.ORANGE);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(9): {
            setAllReportsFalse();
            reporteaceitefechas=true;
            reportselected=9;
            jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
            jLabelParametro2.setText("Fecha Final(dd-MM-yyyy)");
            jTextParametro1.setText("");
            jTxtParametro2.setEnabled(true);
            jLabelParametro2.setEnabled(true);
            
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.ORANGE);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(10): {
            setAllReportsFalse();
            reportescodbateria=true;
            reportselected=10;
            jLabelParametro1.setText("Codigo Bateria");
            jLabelParametro2.setText("");
            jTextParametro1.setText("");
            jTxtParametro2.setEnabled(false);
            jLabelParametro2.setEnabled(false);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.ORANGE);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(11): {
            setAllReportsFalse();
            reportebateriasunidades=true;
            reportselected=11;
            jLabelParametro1.setText("Unidad");
            jLabelParametro2.setText("");
            jTextParametro1.setText("");
            jTxtParametro2.setEnabled(false);
            jLabelParametro2.setEnabled(false);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.ORANGE);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(12): 
        {
            setAllReportsFalse();
            reportesemanal=true;
            reportselected=12;
            jLabelParametro1.setText("Fecha Inicial (dd-MM-yyyy)");
            jLabelParametro2.setText("Fecha Final(dd-MM-yyyy)");
            jTextParametro1.setText("");
            jTxtParametro2.setEnabled(true);
            jLabelParametro2.setEnabled(true);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.ORANGE);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(13): 
        {
            setAllReportsFalse();
            reporterotacionllantas=true;
            reportselected=13;
            jLabelParametro1.setText("Fecha (dd-MM-yyyy)");
            jLabelParametro2.setText("");
            jTextParametro1.setText("");
            jTxtParametro2.setEnabled(false);
            jTxtParametro2.setText("");
            jLabelParametro2.setEnabled(false);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.ORANGE);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.ORANGE);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(14): 
        {
            setAllReportsFalse();
            reporteproximasrotaciones=true;
            reportselected=14;
            jLabelParametro1.setText("Fecha (dd-MM-yyyy)");
            jLabelParametro2.setText("");
            jTextParametro1.setText("");
            jTxtParametro2.setText("");
            jTxtParametro2.setEnabled(false);
            jLabelParametro2.setEnabled(false);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.ORANGE);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(15): 
        {
            setAllReportsFalse();
            reporteconsumoaceite=true;
            reportselected=15;
            jLabelParametro1.setText("Fecha Inicial(dd-MM-yyyy)");
            jLabelParametro2.setText("Fecha Final (dd-MM-yyyy)");
            jTextParametro1.setText("");
            jTxtParametro2.setEnabled(true);
            jLabelParametro2.setEnabled(true);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.ORANGE);
            jButtonReporteConsumoEntreCambios.setBackground(Color.BLACK);
            break;
        }
        case(16): 
        {
            setAllReportsFalse();
            reporteconsumoentrecambios=true;
            reportselected=16;
            jLabelParametro1.setText("Fecha Inicial(dd-MM-yyyy)");
            jLabelParametro2.setText("Fecha Final (dd-MM-yyyy)");
            jTextParametro1.setText("");
            jTxtParametro2.setEnabled(true);
            jLabelParametro2.setEnabled(true);
            jButtonReporteEntreDias.setBackground(Color.BLACK);
            jButtonReportexUnidad.setBackground(Color.BLACK);
            jButtonReportexRamal.setBackground(Color.BLACK);
            jButtonReportexConductor.setBackground(Color.BLACK);
            jButtonReporteCodLlanta.setBackground(Color.BLACK);
            jButtonReporteUnidades.setBackground(Color.BLACK);
            jButtonReporteFechas.setBackground(Color.BLACK);
            jButtonReporteSemanal.setBackground(Color.BLACK);
            jButtonReporteAceiteUnidad.setBackground(Color.BLACK);
            jButtonReporteAceitefechas.setBackground(Color.BLACK);
            jButtonReporteBateriasFechas.setBackground(Color.BLACK);
            jButtonReporteBateriasUnidades.setBackground(Color.BLACK);
            jButtonReporteCodBateria.setBackground(Color.BLACK);
            jButtonReporteRotacionLlantas.setBackground(Color.BLACK);
            jButtonReporteProximosCambios.setBackground(Color.BLACK);
            jButtonReporteConsumoAceites.setBackground(Color.BLACK);
            jButtonReporteConsumoEntreCambios.setBackground(Color.ORANGE);
            break;
        }
    }
}
public void generateReports(int i){
    /*
        INGRESOS
        0=Ingresos por unidad, 1=Ingresos por conductor, 2=Ingresos por Ramal, 4=Ingresos comparativo entre dias
        LLANTAS
        5=Por codigo llanta, 6=General por unidades, 7=General por fechas, 8= Resumen semanal, 13=Rotacion Llantas, 14=Proximos cambios
        ACEITES
        9=Por unidad, 10=Entre fechas, 15=Consumo Aceites
        BATERIAS
        11=Por codigo, 11=Por unidad, 12=General entre fechas
        */
    switch (i){
       case(0):{
           archivo="c:/SCI/REPORTES/RendimientosDiariosxBus2.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
           title="Reporte Ingresos Diario Detallado por Unidad";
       break;
       }
       case(1):{
           archivo="c:/SCI/REPORTES/RendimientosDiariosxConductor.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
            title="Reporte Ingresos Diario Detallado por Conductor";
       break;
       }
       case(2):{
           archivo="c:/SCI/REPORTES/RendimientosDiariosXRamal.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
            title="Reporte Ingresos Diario Detallado por Ramal";
       break;
       }
       case(3):{
           archivo="c:/SCI/REPORTES/RendimientosDiariosxBusEntreDias.jrxml";
           parametro1="Fecha1";
           parametro2="Fecha2";
           parametro3="CodigoEmpresa";
            title="Reporte Ingresos Diario Comparativo entre Fechas";
       break;
       }
       case(4):{
           archivo="c:/SCI/REPORTES/ReportexCodLlanta.jrxml";
           parametro1="CodLlanta";
          
           parametro2="N/A";
           parametro3="CodigoEmpresa";
           title="Reporte Mantenimiento Llantas Detallado por Codigo de Llanta";
       break;
       }
       case(5):{
           archivo="c:/SCI/REPORTES/ReporteGeneraldeLlantasAgrupadoporUnidad.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
           title="Reporte Mantenimiento Llantas Detallado entre Fechas Agrupado por Unidad";
       break;
       }
       case(6):{
           archivo="c:/SCI/REPORTES/ReporteGeneraldeLlantasAgrupadoporFecha.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
           title="Reporte Mantenimiento Llantas Detallado entre Fechas Agrupado por Fechas";
       break;
       }
       case(7):{
           archivo="c:/SCI/REPORTES/ReporteSemanalCambLlantas.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
          title="Reporte Mantenimiento Llantas Detallado Semanal entre Fechas";
       break;
       }
       case(8):{
           archivo="c:/SCI/REPORTES/ReporteGeneralAceitesxUnidad.jrxml";
           parametro1="Unidad";
           parametro2="N/A";
           parametro3="CodigoEmpresa";
          title="Reporte Mantenimiento Aceites por Unidad";
       break;
       }
       case(9):{
           archivo="c:/SCI/REPORTES/ReporteGeneraldeAceites.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
          title="Reporte Mantenimiento Aceites entre Fechas";
       break;
       }
       case(10):{
           archivo="c:/SCI/REPORTES/ReporteGeneraldeBateriasxCodBateria.jrxml";
           parametro1="CodigoBateria";
           parametro2="N/A";
           parametro3="CodigoEmpresa";
          title="Reporte Mantenimiento Baterias por Codigo Bateria";
       break;
       }
       case(11):{
           archivo="c:/SCI/REPORTES/ReporteSemanalCambLlantas.jrxml";
           parametro1="Unidad";
           parametro2="N/A";
           parametro3="CodigoEmpresa";
          title="Reporte Mantenimiento Baterias por Unidad";
       break;
       }
       case(12):{
           archivo="c:/SCI/REPORTES/ReporteSemanalCambLlantas.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
          title="Reporte Mantenimiento Baterias entre Fechas";
       break;
       }
       case(13):{
           archivo="c:/SCI/REPORTES/ReporteSemanalLlantas.jrxml";
           parametro1="FechaInicial";
           parametro2="N/A";
           parametro3="CodigoEmpresa";
           title="Reporte Rotacion de Llantas";
       break;
       }
       case(14):{
           archivo="c:/SCI/REPORTES/ReporteSemanalCambLlantasSiguienteSemana.jrxml";
           parametro1="FechaInicial";
           parametro2="N/A";
           parametro3="CodigoEmpresa";
           title="Reporte Proximo Cambio de Llantas "+jTextParametro1.getText();
       break;
       }
       case(15):{
           archivo="c:/SCI/REPORTES/ReporteConsumoAceites.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="CodigoEmpresa";
           title="Reporte Consumo Aceites por plantel entre "+jTextParametro1.getText()+" y "+jTxtParametro2.getText();
       break;
       }
       case(16):{
           archivo="c:/SCI/REPORTES/ConsumoAceiteEntreCambios.jrxml";
           parametro1="FechaInicial";
           parametro2="FechaFinal";
           parametro3="Detallado";
           title="Reporte Consumo entre Cambios de Aceites por Bus entre "+jTextParametro1.getText()+" y "+jTxtParametro2.getText();
       break;
       }
       
       
    }
    Thread t=new Thread(){
            @Override
            public void run(){

                
                try {
                    jProgressBar1.setValue(5);
                    jProgressBar1.setString("Ingresando parametros");
                    SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
                    String fechainicial=null;
                    Date date1 = null;
                    if (reportselected==4 ||reportselected==10 ){
                    fechainicial=IdEmpresa+"#"+jTextParametro1.getText();}
                    else{
                    if (reportselected==8 ||reportselected==11 ){
                    fechainicial=jTextParametro1.getText();}
                    else{
                    fechainicial=jTextParametro1.getText();
                    try {
                        date1 = df.parse(fechainicial);
                    } catch (ParseException ex) {
                        
                        JOptionPane.showMessageDialog(null, "Fecha invalida");
                        jProgressBar1.setString("Sistema de Control de Indicadores");
                        jProgressBar1.setValue(0);
                    }
                    }
                    }
                     Date date2 = null;
                    if (parametro2.contains("N/A")==false){
                    String fechafinal=jTxtParametro2.getText();
                    
                    try {
                        date2 = df.parse(fechafinal);
                    } catch (ParseException ex) {
                        
                        JOptionPane.showMessageDialog(null, "Fecha invalida");
                        jProgressBar1.setString("Sistema de Control de Indicadores");
                        jProgressBar1.setValue(0);
                    }
                    }
                    jProgressBar1.setValue(15);
                    jProgressBar1.setString("Compilando reporte");
                    JasperReport reporte=JasperCompileManager.compileReport(archivo);
                    jProgressBar1.setValue(55);
                    jProgressBar1.setString("Generando reporte");
                    HashMap map=new HashMap();
                    if (parametro1.contains("Fecha")){
                        //JOptionPane.showMessageDialog(rootPane, fechainicial);
                        map.put(parametro1, date1);
                    }
                    else{
                    if (parametro1.contains("Unidad")){
                        map.put(parametro1, Integer.parseInt(fechainicial));
                    }
                    else{
                        map.put(parametro1, fechainicial);
                    }
                    }
                    if (parametro2.contains("N/A")==false){
                    map.put(parametro2, date2);}
                    if (parametro3.contains("Detallado")){
                        int i=JOptionPane.showConfirmDialog(rootPane, "Desea que el reporte sea detallado", "Sistema de Control de Indicadores", JOptionPane.YES_NO_OPTION);
                        if (i==JOptionPane.YES_OPTION){
                            map.put(parametro3, true);
                        }
                        else{
                            map.put(parametro3, false);
                        }
                    }
                    else{
                    map.put(parametro3, IdEmpresa);}
                    jProgressBar1.setValue(60);
                    jProgressBar1.setString("Cargando parametros");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, map, new ModuloMySQL().getConexion());
                    JasperViewer jw=new JasperViewer(jasperPrint,false);
                    jw.setTitle(title);
                    jw.setExtendedState(Frame.MAXIMIZED_BOTH);
                    jw.setVisible(true);
                    jProgressBar1.setValue(90);
                    jProgressBar1.setString("Visualizando Reporte");
                    jw.setVisible(true);
                    jProgressBar1.setValue(100);
                    jProgressBar1.setString("Terminado");
                    setBarrProgressDefault();
                } catch (JRException ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el reporte");

                    Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
                    jProgressBar1.setString("Sistema de Control de Indicadores");
                    jProgressBar1.setValue(0);

                }
            }
        };
        t.start();
    
}
public final void getListEmpresas(){
    if (DataUser.administrator){
    final ResultSet rs=modulo.Listar("Select CodigoEmpresa, Nombre_Empresa from Empresas");
    if (rs!=null){
        try {
            while(rs.next()){
                jComboBoxListEmpresas.addItem(rs.getString("Nombre_Empresa"));
                
            }
            jComboBoxListEmpresas.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    try {
                        int index=jComboBoxListEmpresas.getSelectedIndex()+1;
                        
                        rs.absolute(index);
                        IdEmpresa=rs.getInt("CodigoEmpresa");
                        ValoresEstaticos.setIdEmpresa(IdEmpresa);
                        //JOptionPane.showMessageDialog(rootPane, "Cambio Item"+IdEmpresa);
                    } catch (SQLException ex) {
                        Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            rs.first();
            IdEmpresa=rs.getInt("CodigoEmpresa");
        } catch (SQLException ex) {
            Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }}
    else{
        final ResultSet rs=modulo.Listar("Select CodigoEmpresa, Nombre_Empresa from Empresas Where CodigoEmpresa='"+ValoresEstaticos.idempresa+"'");
    if (rs!=null){
        try {
            while(rs.next()){
                jComboBoxListEmpresas.addItem(rs.getString("Nombre_Empresa"));
                
            }
            jComboBoxListEmpresas.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    try {
                        int index=jComboBoxListEmpresas.getSelectedIndex()+1;
                        
                        rs.absolute(index);
                        IdEmpresa=rs.getInt("CodigoEmpresa");
                        ValoresEstaticos.setIdEmpresa(IdEmpresa);
                        //JOptionPane.showMessageDialog(rootPane, "Cambio Item"+IdEmpresa);
                    } catch (SQLException ex) {
                        Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            rs.first();
            IdEmpresa=rs.getInt("CodigoEmpresa");
        } catch (SQLException ex) {
            Logger.getLogger(Reporteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
}
public void setAllReportsFalse(){
    reporteporconductor=false;
    reporteporramal=false;
    reporteporunidad=false;
    reportecompdias=false;
    reportecodllanta=false;
    reportegeneralunidades=false;
    reportegeneralfechas=false;
    reportesemanal=false;
    reporteaceitefechas=false;
    reporteaceiteunidad=false;
    reportebateriasfechas=false;
    reportebateriasunidades=false;
    reportescodbateria=false;
    reporteproximasrotaciones=false;
    reporterotacionllantas=false;
    reporteconsumoaceite=false;
    reporteconsumoentrecambios=false;
}
    private void jButtonReporteFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteFechasActionPerformed
        
        if (DataUser.permiso_reporteria_control_llantas){
            setFunctionReports(6);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteFechasActionPerformed

    private void jButtonReporteSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteSemanalActionPerformed
       
        if (DataUser.permiso_reporteria_control_llantas){
            setFunctionReports(7);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteSemanalActionPerformed

    private void jTextParametro1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextParametro1MouseClicked
        // TODO add your handling code here:
        if (reportselected!=4&reportselected!=8&reportselected!=10&reportselected!=11){
         this.jTextParametro1.setText(new DatePicker(this).setPickedDate());}
    }//GEN-LAST:event_jTextParametro1MouseClicked

    private void jTxtParametro2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtParametro2MouseClicked
        // TODO add your handling code here:
         this.jTxtParametro2.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jTxtParametro2MouseClicked

    private void jButtonReporteAceiteUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteAceiteUnidadActionPerformed
       
        if (DataUser.permiso_reporteria_control_aceites){
            setFunctionReports(8);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteAceiteUnidadActionPerformed

    private void jButtonReporteAceitefechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteAceitefechasActionPerformed
        
        if (DataUser.permiso_reporteria_control_aceites){
            setFunctionReports(9);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteAceitefechasActionPerformed

    private void jButtonReporteCodBateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteCodBateriaActionPerformed
       
        if (DataUser.permiso_reporteria_control_baterias){
            setFunctionReports(10);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteCodBateriaActionPerformed

    private void jButtonReporteBateriasUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteBateriasUnidadesActionPerformed
        
        if (DataUser.permiso_reporteria_control_baterias){
            setFunctionReports(11);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteBateriasUnidadesActionPerformed

    private void jButtonReporteBateriasFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteBateriasFechasActionPerformed
       
        if (DataUser.permiso_reporteria_control_baterias){
            setFunctionReports(12);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteBateriasFechasActionPerformed

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

    private void jButtonReporteRotacionLlantasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteRotacionLlantasActionPerformed
        
        if (DataUser.permiso_reporteria_control_llantas){
            setFunctionReports(13);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
         
    }//GEN-LAST:event_jButtonReporteRotacionLlantasActionPerformed

    private void jButtonReporteProximosCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteProximosCambiosActionPerformed
        
        if (DataUser.permiso_reporteria_control_llantas){
            setFunctionReports(14);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
         
    }//GEN-LAST:event_jButtonReporteProximosCambiosActionPerformed

    private void jButtonReporteConsumoAceitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteConsumoAceitesActionPerformed
      
        if (DataUser.permiso_reporteria_control_aceites){
            setFunctionReports(15);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonReporteConsumoAceitesActionPerformed

    private void jButtonReporteConsumoEntreCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReporteConsumoEntreCambiosActionPerformed
        // TODO add your handling code here:
        if (DataUser.permiso_reporteria_control_aceites){
            setFunctionReports(16);
        }
        else{
        JOptionPane.showMessageDialog(rootPane, "El usuario "+DataUser.username+" no tiene permisos para realizar esta función. Consulte al administrator del sistema", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonReporteConsumoEntreCambiosActionPerformed

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
            java.util.logging.Logger.getLogger(Reporteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reporteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reporteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reporteria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reporteria().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButtonGenerarReporte;
    private javax.swing.JButton jButtonReporteAceiteUnidad;
    private javax.swing.JButton jButtonReporteAceitefechas;
    private javax.swing.JButton jButtonReporteBateriasFechas;
    private javax.swing.JButton jButtonReporteBateriasUnidades;
    private javax.swing.JButton jButtonReporteCodBateria;
    private javax.swing.JButton jButtonReporteCodLlanta;
    private javax.swing.JButton jButtonReporteConsumoAceites;
    private javax.swing.JButton jButtonReporteConsumoEntreCambios;
    private javax.swing.JButton jButtonReporteEntreDias;
    private javax.swing.JButton jButtonReporteFechas;
    private javax.swing.JButton jButtonReporteProximosCambios;
    private javax.swing.JButton jButtonReporteRotacionLlantas;
    private javax.swing.JButton jButtonReporteSemanal;
    private javax.swing.JButton jButtonReporteUnidades;
    private javax.swing.JButton jButtonReportexConductor;
    private javax.swing.JButton jButtonReportexRamal;
    private javax.swing.JButton jButtonReportexUnidad;
    private javax.swing.JComboBox jComboBoxListEmpresas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelParametro1;
    private javax.swing.JLabel jLabelParametro2;
    private javax.swing.JLabel jLabelParametro3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDatos;
    private javax.swing.JTextField jTextParametro1;
    private javax.swing.JTextField jTxtParametro2;
    // End of variables declaration//GEN-END:variables
    public int function=0;
    public static ModuloMySQL modulo=new ModuloMySQL();
    static String nameColumsAll="Fecha, CodConductor, Ramal, Unidad, Litros_Dispensados, Kilometros_Recorridos, Ingresos_Dia, Total_Pasajeros, Total_Cedulas, Cant_Medias_Carreras, Horas_Laboradas_Barras, Horas_Laboradas_Rol, Eficiencia_Horas_Laboradas, Marcas_Totales, Marcas_Efectivas, Marcas_Totales_Carreras, Marcas_Efectivas_Carreras, Marcas_Kilometro, Porcentaje_AdultoMayor, Litros_Carrera, Litros_Pasajero, Litros_Km, Km_Litros, Dinero_Carrera, Dinero_Km";
    static String nameColumsIndi="Fecha, CodConductor, Ramal, Unidad, Eficiencia_Horas_Laboradas, Marcas_Totales, Marcas_Efectivas, Marcas_Totales_Carreras, Marcas_Efectivas_Carreras, Marcas_Kilometro, Porcentaje_AdultoMayor, Litros_Carrera, Litros_Pasajero, Litros_Km, Km_Litros, Dinero_Carrera, Dinero_Km";
    static boolean reporteporconductor;
    static boolean reporteporramal;
    static boolean reporteporunidad=true;
    static boolean reportecompdias;
    static boolean reportecodllanta;
    static boolean reportegeneralunidades;
    static boolean reportegeneralfechas;
    static boolean reportesemanal;
    static boolean reporteaceiteunidad;
    static boolean reporteaceitefechas;
    static boolean reportescodbateria;
    static boolean reportebateriasunidades;
    static boolean reportebateriasfechas;
    static boolean reporterotacionllantas;
    static boolean reporteproximasrotaciones;
    static boolean reporteconsumoaceite;
    static boolean reporteconsumoentrecambios;
    static String archivo;
    static String parametro1;
    static String parametro2 = null;
    static String parametro3;
    static String title;
    static int IdEmpresa;
    static int reportselected;
   
}
