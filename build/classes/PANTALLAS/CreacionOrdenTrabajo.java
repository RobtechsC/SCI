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
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
public class CreacionOrdenTrabajo extends javax.swing.JFrame {

    /**
     * Creates new form CreacionOrdenTrabajo
     */
    public CreacionOrdenTrabajo() {
        getLastOrdenTrabajo();
        initComponents();
        getCategorias();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();   
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        jLabelVersion16.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
        getUnidades();setListenerList();
        jTablaTareas.setModel(tablemodel);
    }
    public CreacionOrdenTrabajo(int consecutivo) {
       
        
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();   
        getCategorias();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        this.setLocation(x, y);
        jLabelVersion16.setText("Sistema Control de Indicadores Version: "+ValoresEstaticos.version_sci+"  Usuario Actual: "+DataUser.username);
        getUnidades();
        //getPlanteles();
        //getTiposMantenimiento();
        //listTrabajosAgregados();
        setListenerList();
        jTablaTareas.setModel(tablemodel);
        ordentrabajo=consecutivo;
        jTitulo.setText("ORDEN DE TRABAJO # "+ordentrabajo);
        jLabel2.setText("ACTUALIZACION DE ORDEN DE TRABAJO");
        getDataOrden();
        saveOrden=false;
    }
    private void getUnidades(){
        new Thread(){
            @Override
            public void run(){
                ResultSet rs=modulo.Listar("Select Unidad from UnidadesxEmpresa where IdEmpresa='3' order by Unidad");
        if (rs!=null){
            try {
                while (rs.next()){
                    jUnidad.addItem(rs.getString("Unidad"));
                }
                jUnidad.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        unidad=Integer.parseInt(jUnidad.getSelectedItem().toString());
                    }
                });
                rs.first();
                unidad=rs.getInt("Unidad");
                getPlanteles();
            } catch (SQLException ex) {
                
                Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            }
        }.start();
        
        ValoresEstaticos.muestreMensajeEsperar("Recuperando informacion.");
    }
    private void getDataOrden(){
        new Thread(){
            @Override
            public void run(){
                int i=0;
        int count=tablemodel.getRowCount();
        while (i<count){
           //JOptionPane.showMessageDialog(rootPane, tablemodel.getRowCount()+" "+i);
            tablemodel.removeRow(0);
            //jTablaTareas.remove(i);
            i++;
        }
        jTablaTareas.setModel(tablemodel);
        String select="SELECT\n" +
"     Ordenes_Trabajo.`Id_OrdenTrabajo`,\n" +
"     Ordenes_Trabajo.`Hora_Inicio`,\n" +
"     Ordenes_Trabajo.`Fecha`,\n" +
"     Ordenes_Trabajo.`Id_Bus`,\n" +
"     Ordenes_Trabajo.`Id_Plantel`,\n" +
"     Empleado.`Nombre`,\n" +
"     Sub_Mantenimiento.`Descripcion`,\n" +
"     SubOrdenes_Trabajo.`Id_SubMantenimiento`\n" +
"FROM\n" +
"     `Ordenes_Trabajo` Ordenes_Trabajo INNER JOIN `SubOrdenes_Trabajo` SubOrdenes_Trabajo ON Ordenes_Trabajo.`Id_OrdenTrabajo` = SubOrdenes_Trabajo.`Id_OrdenTrabajo`\n" +
"     INNER JOIN `Sub_Mantenimiento` Sub_Mantenimiento ON SubOrdenes_Trabajo.`Id_TrabajoRealizado` = Sub_Mantenimiento.`ID_Submantenimiento`\n" +
"     INNER JOIN `Empleado` Empleado ON SubOrdenes_Trabajo.`Id_Empleado` = Empleado.`Id_Empleado` where  Ordenes_Trabajo.`Id_OrdenTrabajo`='"+ordentrabajo+"'";
        ResultSet rs=modulo.Listar(select);
        if (rs!=null){
            try {
                while (rs.next()){
                    if (rs.isFirst()){
                        SimpleDateFormat df=new SimpleDateFormat ("dd-MM-yyyy");
                        SimpleDateFormat df2=new SimpleDateFormat("HH:mm");
                        Date d=rs.getDate("Fecha");
                        jUnidad.setSelectedItem(rs.getInt("Id_Bus"));
                        jFecha.setText(df.format(d));
                        jHoraInicio.setText(df2.format(rs.getTime("Hora_Inicio")));
                        OrdenesTrabajo.horainicio=rs.getTime("Hora_Inicio");
                        
                    }
                    ValoresEstaticos.ocultaMensaje();
                    i=rs.getInt("Id_SubMantenimiento");
                    unidad=rs.getInt("Id_Bus");
                    
                   
                    trabajoselecionado=rs.getString("Descripcion");
                    nombreempleado=rs.getString("Nombre");
                    listTrabajosAgregados(i);
                    
                }
               jUnidad.setSelectedItem(String.valueOf(unidad));
               //OrdenesTrabajo.horainicio=rs.getTime("Hora_Inicio");
            } catch (SQLException ex) {
                ValoresEstaticos.ocultaMensaje();
                Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            }
        }.start();
        ValoresEstaticos.muestreMensajeEsperar("Recuperando informacion de la orden de trabajo");
        
    }
    private void getTiposMantenimiento(){
       
               // ValoresEstaticos.changeMensaje("Recuperando listado de tipos de mantenimiento");
                result_tiposmantimiento=modulo.Listar("Select ID_Mantenimiento, Descripcion from Mantenimiento order by ID_Mantenimiento");
        if (result_tiposmantimiento!=null){
            try {
                while (result_tiposmantimiento.next()){
                    jTipoMantenimiento.addItem(result_tiposmantimiento.getString("Descripcion"));
                }
                
                result_tiposmantimiento.first();
                idTipoMantenimiento=result_tiposmantimiento.getInt("ID_Mantenimiento");
                getListTrabajos(idTipoMantenimiento,idCategoria);
                
            } catch (SQLException ex) {
                 
                Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
         
        
    }
    private void getCategorias(){
       
               // ValoresEstaticos.changeMensaje("Recuperando listado de tipos de mantenimiento");
                result_categorias=modulo.Listar("Select IdCategoria, Descripcion from CategoriaOrdenTrabajo order by IdCategoria");
        if (result_categorias!=null){
            try {
                while (result_categorias.next()){
                    jCategoria.addItem(result_categorias.getString("Descripcion"));
                }
                
                result_categorias.first();
                idCategoria=result_categorias.getInt("IdCategoria");
                
                
            } catch (SQLException ex) {
                 
                Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
         
        
    }
    private void setListenerList(){
        jTipoMantenimiento.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange()==ItemEvent.SELECTED){
                        try {
                            int i=jTipoMantenimiento.getSelectedIndex()+1;
                            result_tiposmantimiento.absolute(i);
                            idTipoMantenimiento=result_tiposmantimiento.getInt("ID_Mantenimiento");
                            new Thread(){
                                @Override
                                public void run(){
                                    getListTrabajos(idTipoMantenimiento,idCategoria);
                                }
                            
                            }.start();
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }}
                });
        jCategoria.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange()==ItemEvent.SELECTED){
                        try {
                            int i=jCategoria.getSelectedIndex()+1;
                            result_categorias.absolute(i);
                            idCategoria=result_categorias.getInt("IdCategoria");
                            new Thread(){
                                @Override
                                public void run(){
                                    getListTrabajos(idTipoMantenimiento,idCategoria);
                                }
                            
                            }.start();
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }}
                });
        jEmpleado.addItemListener(new ItemListener() {
                        
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (e.getStateChange()==ItemEvent.SELECTED){
                            try {
                                int i=jEmpleado.getSelectedIndex()+1;
                                result_empleados.absolute(i);
                                if (result_empleados.isBeforeFirst()==false){
                                    idEmpleado=result_empleados.getInt("Id_Empleado");
                                    nombreempleado=result_empleados.getString("Nombre");
                                }
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }}
                    });
        jSubmantenimiento.addItemListener(new ItemListener() {
                        
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                          if (e.getStateChange()==ItemEvent.SELECTED&e.getSource()==jSubmantenimiento){  
                            try {
                                //JOptionPane.showMessageDialog(rootPane, e);
                                int i=jSubmantenimiento.getSelectedIndex()+1;
                                result_trabajos.absolute(i);
                                if (result_trabajos.isBeforeFirst()==false)
                                {
                                    
                                    idSubmanteniento=result_trabajos.getInt("ID_Submantenimiento");
                                    trabajoselecionado=result_trabajos.getString("Descripcion");
                                    //if (idPuesto!=result_trabajos.getInt("ID_Puesto"))
                                    //{
                                        new Runnable() {

                                            @Override
                                            public void run() {
                                                try {
                                                    getListEmpleados(result_trabajos.getInt("ID_Puesto"),result_trabajos.getInt("ID_Puesto2"),result_trabajos.getInt("ID_Puesto3"),result_trabajos.getInt("ID_Puesto4"),result_trabajos.getInt("ID_Puesto5"));//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        }.run();
                                        
                                    //}
                                }
                                
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }}
                    });
    }
    private void getListEmpleados(final int i, final int i2, final int i3, final int i4, final int i5){
               //ValoresEstaticos.changeMensaje("Recuperando listado de empleados");
                try {
                //jEmpleado.removeAllItems();
                idPuesto=i;
                result_empleados=modulo.Listar("Select Id_Empleado, Nombre from Empleado where Id_Puesto='"+i+"' or Id_Puesto='"+i2+"'or Id_Puesto='"+i3+"' or Id_Puesto='"+i4+"' or Id_Puesto='"+i5+"' order by Nombre");
                result_empleados.beforeFirst();
                if (result_empleados.isBeforeFirst()){
                    jEmpleado.removeAllItems();
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
                    Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
            }
            ValoresEstaticos.ocultaMensaje();
        } catch (SQLException ex) {
            ValoresEstaticos.ocultaMensaje();
            JOptionPane.showMessageDialog(rootPane, ex);
            Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        
       
    
    
    private void listTrabajosAgregados(){
         Object[] data=new Object[]{datos.getLastConsecutivo(),unidad,trabajoselecionado,nombreempleado};
         tablemodel.addRow(data);
    
    }
    private void listTrabajosAgregados(int consecutivo){
         Object[] data=new Object[]{consecutivo,unidad,trabajoselecionado,nombreempleado};
         tablemodel.addRow(data);
    
    }
    private void getListTrabajos(final int i, final int a){
       
                //ValoresEstaticos.changeMensaje("Recuperando listado de trabajos");
                 try {
            jSubmantenimiento.removeAllItems();
            result_trabajos=modulo.Listar("Select ID_Submantenimiento, Descripcion, Tiempo_Estandar, ID_Repuesto, ID_Alarma, ID_Puesto, ID_Puesto2, ID_Puesto3, ID_Puesto4, ID_Puesto5 from Sub_Mantenimiento where ID_Mantenimiento ='"+i+"' and IdCategoria='"+idCategoria+"' order by Descripcion");
            if (result_trabajos.isBeforeFirst()){
               
                try {
                    while (result_trabajos.next()){
                        jSubmantenimiento.addItem(result_trabajos.getString("Descripcion"));
                    }
                    
                    if (result_trabajos.first()){
                        
                        idSubmanteniento=result_trabajos.getInt("ID_Submantenimiento");
                        trabajoselecionado=result_trabajos.getString("Descripcion");
                        idPuesto=result_trabajos.getInt("ID_Puesto");
                        getListEmpleados(result_trabajos.getInt("ID_Puesto"),result_trabajos.getInt("ID_Puesto2"),result_trabajos.getInt("ID_Puesto3"),result_trabajos.getInt("ID_Puesto4"),result_trabajos.getInt("ID_Puesto5"));//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        
                    }
                    
                    
                } catch (SQLException ex) {
                     ValoresEstaticos.ocultaMensaje();
                    Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
            else{
                ValoresEstaticos.ocultaMensaje();
                JOptionPane.showMessageDialog(rootPane, "No se encontraron trabajos para el tipo de mantenimiento seleccionado","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
                jEmpleado.removeAllItems();
                
           }
        } catch (SQLException ex) {
             ValoresEstaticos.ocultaMensaje();
            Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    private void getLastOrdenTrabajo() {
        new Thread(){
            @Override
            public void run(){
                 
                try {
            ResultSet rs=modulo.Listar("Select Id_OrdenTrabajo from Ordenes_Trabajo ORDER BY Id_OrdenTrabajo DESC LIMIT 1");
            if (rs.first()){
                ordentrabajo=rs.getInt("Id_OrdenTrabajo")+1;
            }
            else{
                ordentrabajo=1;
            }
            ValoresEstaticos.ocultaMensaje();
            
        } catch (SQLException ex) {
            ValoresEstaticos.ocultaMensaje();
            Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        }.start();
       ValoresEstaticos.muestreMensajeEsperar("Buscando ultima orden de trabajo");
    }
    private void getPlanteles(){
        
                 //ValoresEstaticos.changeMensaje("Recuperando listado de planteles");
                final ResultSet rs=modulo.Listar("Select IdPlantel, Plantel from Planteles_Empresa where IdEmpresa='3' order by IdPlantel");
        if (rs!=null){
            try {
                while (rs.next()){
                    jPlantel.addItem(rs.getString("Plantel"));
                }
                jPlantel.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        try {
                            int i=jPlantel.getSelectedIndex()+1;
                            rs.absolute(i);
                            idPlantel=rs.getInt("IdPlantel");
                        } catch (SQLException ex) {
                            Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                rs.first();
                idPlantel=rs.getInt("IdPlantel");
                getTiposMantenimiento();
            } catch (SQLException ex) {
                
                Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTitulo = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jFecha = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jUnidad = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jPlantel = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jTiempoTotal = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jHoraInicio = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jHoraFin = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTipoMantenimiento = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jAgregar1 = new javax.swing.JButton();
        jAgregar2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jEmpleado = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSubmantenimiento = new javax.swing.JComboBox();
        jAgregar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jCategoria = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jSubtitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaTareas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabelVersion16 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CREACION DE ORDEN DE TRABAJO");

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

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/SCI.png"))); // NOI18N
        jLabel7.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addContainerGap())
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jFecha.setBackground(new java.awt.Color(102, 102, 102));
        jFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFecha.setForeground(new java.awt.Color(255, 153, 0));
        jFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFechaMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FECHA");

        jUnidad.setBackground(new java.awt.Color(102, 102, 102));
        jUnidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jUnidad.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("UNIDAD");

        jPlantel.setBackground(new java.awt.Color(102, 102, 102));
        jPlantel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPlantel.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("PLANTEL");

        jTiempoTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTiempoTotal.setForeground(new java.awt.Color(255, 255, 255));
        jTiempoTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("TIEMPO TOTAL");

        jHoraInicio.setBackground(new java.awt.Color(51, 51, 51));
        jHoraInicio.setForeground(new java.awt.Color(255, 255, 255));
        jHoraInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm"))));
        jHoraInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jHoraInicio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("HORA INI");

        jHoraFin.setBackground(new java.awt.Color(51, 51, 51));
        jHoraFin.setForeground(new java.awt.Color(255, 255, 255));
        jHoraFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm"))));
        jHoraFin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jHoraFin.setEnabled(false);
        jHoraFin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("HORA FIN");

        jLabel11.setBackground(new java.awt.Color(255, 102, 0));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("DATOS BASICOS DE LA ORDEN DE TRABAJO");
        jLabel11.setOpaque(true);

        jTipoMantenimiento.setBackground(new java.awt.Color(102, 102, 102));
        jTipoMantenimiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTipoMantenimiento.setForeground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("TIPO MANTENIMIENTO");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFecha)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jUnidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPlantel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTipoMantenimiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                .addGap(18, 84, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTiempoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jAgregar2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPlantel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTipoMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTiempoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jAgregar2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jEmpleado.setBackground(new java.awt.Color(102, 102, 102));
        jEmpleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Empleado");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("TRABAJO A REALIZAR");

        jSubmantenimiento.setBackground(new java.awt.Color(102, 102, 102));
        jSubmantenimiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jSubmantenimiento.setForeground(new java.awt.Color(255, 255, 255));
        jSubmantenimiento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jAgregar.setBackground(new java.awt.Color(51, 51, 51));
        jAgregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jAgregar.setText("AGREGAR");
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

        jCategoria.setBackground(new java.awt.Color(102, 102, 102));
        jCategoria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCategoria.setForeground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("CATEGORIA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jEmpleado, 0, 372, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSubmantenimiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSubmantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jSubtitulo.setBackground(new java.awt.Color(255, 102, 0));
        jSubtitulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jSubtitulo.setForeground(new java.awt.Color(255, 255, 255));
        jSubtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jSubtitulo.setText("TRABAJOS PARA ORDEN DE TRABAJO # "+String.valueOf(ordentrabajo));
        jSubtitulo.setOpaque(true);

        jTablaTareas.setBackground(new java.awt.Color(51, 51, 51));
        jTablaTareas.setForeground(new java.awt.Color(255, 255, 255));
        jTablaTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTablaTareas);

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ELIMINAR TRABAJO SELECCIONADO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("GENERAR ORDEN DE TRABAJO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jProgressBar1.setString("LISTO");
        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1271, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(306, 306, 306)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jSubtitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));

        jLabel18.setBackground(new java.awt.Color(0, 51, 102));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("SELECCIONE UN TIPO DE CONTROL");
        jLabel18.setOpaque(true);

        jButton15.setBackground(new java.awt.Color(51, 51, 51));
        jButton15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 153, 51));
        jButton15.setText("Control de Aceites");
        jButton15.setFocusable(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(51, 51, 51));
        jButton16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 153, 51));
        jButton16.setText("Control de Llantas");
        jButton16.setFocusable(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(51, 51, 51));
        jButton17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 153, 51));
        jButton17.setText("Ingresos Diarios");
        jButton17.setFocusable(false);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(51, 51, 51));
        jButton18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 153, 51));
        jButton18.setText("Control de Baterias");
        jButton18.setFocusable(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(51, 51, 51));
        jButton19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 153, 51));
        jButton19.setText("Parametros");
        jButton19.setFocusable(false);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(51, 51, 51));
        jButton20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton20.setForeground(new java.awt.Color(255, 153, 51));
        jButton20.setText("SALIR");
        jButton20.setFocusable(false);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(0, 204, 0));
        jButton22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton22.setForeground(new java.awt.Color(0, 51, 102));
        jButton22.setText("Ordenes de Trabajo");
        jButton22.setFocusable(false);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton22)
                        .addGap(18, 18, 18)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 102, 153));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Creación de Ordenes de Trabajo");

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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFechaMouseClicked
        // TODO add your handling code here:
        jFecha.setText(new DatePicker(this).setPickedDate());
    }//GEN-LAST:event_jFechaMouseClicked

    private void jAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAgregarActionPerformed
        if (saveOrden){
        try {
            
            // TODO add your handling code here:
            datos.setBus(unidad);
            datos.setEstadoOrden(1);
            SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat tf=new SimpleDateFormat("HH:mm");
            Date d=sf.parse(jFecha.getText());
            datos.setFechaOrdenTrabajo(new java.sql.Date(d.getTime()));
            Time hori=new Time(tf.parse(jHoraInicio.getText()).getTime());
            //Time horf=new Time(tf.parse(jHoraFin.getText()).getTime());
            datos.setFechaProxTrabajo(null);
            //datos.setHoraFin(horf);
            datos.setHoraInicio(hori);
            datos.setIdAlarma(1);
            datos.setIdEmpleado(idEmpleado);
            datos.setIdPlantel(idPlantel);
            datos.setIdSubmantenimiento(idSubmanteniento);
            datos.setNumeroOrden(ordentrabajo);
            datos.setProxKmTrabajo(0);
            datos.setTiempoOrdenTrabajo(0);
            if (datos.saveOrdenTrabajo()){
                saveOrden=false;
                jHoraInicio.setEditable(false);
                jHoraFin.setEditable(false);
                jFecha.setEditable(false);
                jUnidad.setEditable(false);
                jTiempoTotal.setText(String.valueOf((OrdenesTrabajo.tiempototal-(OrdenesTrabajo.horainicio).getTime())/3600000)+" HORA(S)");
                listTrabajosAgregados();
            }
            
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(rootPane, "Formato de los campos Fecha, Hora Inicio u Hora Fin inválido","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
            datos.setIdEmpleado(idEmpleado);
            datos.setNumeroOrden(ordentrabajo);
            datos.setIdPlantel(idPlantel);
            datos.setIdSubmantenimiento(idSubmanteniento);
            if (datos.saveTrabajoOrdenTrabajo()){
                jTiempoTotal.setText("Se agregaron + "+String.valueOf((OrdenesTrabajo.tiempototal)/3600000)+ 
                       " HORA(S)");
                JOptionPane.showMessageDialog(rootPane, "Se guardo trabajo en la orden de trabajo # "+ordentrabajo);
                listTrabajosAgregados();
            }
        }
        
    }//GEN-LAST:event_jAgregarActionPerformed

    private void jAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAgregar1ActionPerformed
        // TODO add your handling code here:
        if (saveOrden==false){
        saveOrden=true;
        if (jLabel2.getText().contains("ACTUALIZACION")){
            jLabel2.setText("CREACION DE ORDEN DE TRABAJO");
            getLastOrdenTrabajo();
        }
        ordentrabajo++;
        jTitulo.setText("ORDEN DE TRABAJO # "+ordentrabajo);
        jSubtitulo.setText("TRABAJOS PARA ORDEN DE TRABAJO # "+String.valueOf(ordentrabajo));
        int i=0;
        int count=tablemodel.getRowCount();
        while (i<count){
           //JOptionPane.showMessageDialog(rootPane, tablemodel.getRowCount()+" "+i);
            tablemodel.removeRow(0);
            //jTablaTareas.remove(i);
            i++;
        }
        jTablaTareas.setModel(tablemodel);
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
                new ListadoOrdenesTrabajo().setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jAgregar2ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
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
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
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
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
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
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
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
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
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
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String s=jTablaTareas.getValueAt(jTablaTareas.getSelectedRow(), 0).toString();
        int consecutivo=Integer.parseInt(s);
        String mensaje="En realidad desea eliminar el trabajo #"+consecutivo+": "+jTablaTareas.getValueAt(jTablaTareas.getSelectedRow(), 2).toString()+
                " de la orden de trabajo #"+ordentrabajo;
                
        if (JOptionPane.showConfirmDialog(rootPane, mensaje,"Sistema de Control de Indicadores",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
            try {
                ResultSet rs=modulo.Listar("select Estado from SubOrdenes_Trabajo where Id_SubMantenimiento='"+consecutivo+"'");
                rs.first();
                if (rs.getInt("Estado")==3){
                    JOptionPane.showMessageDialog(rootPane,"Trabajo finalizado. No se puede eliminar", "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (modulo.Ejecutar("delete from SubOrdenes_Trabajo where Id_SubMantenimiento='"+consecutivo+"'").contains("concluy")){
                    JOptionPane.showMessageDialog(rootPane, "Se elimino el trabajo correctamente", "Sistema Control de Indicadores", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Error. No se elimino el trabajo", "Sistema Control de Indicadores", JOptionPane.ERROR_MESSAGE);
                }
                getDataOrden();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage(), "Sistema de Control de Indicadores", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    public void generateOrden(){
        final String archivo="c:/SCI/REPORTES/GenerarOrdenTrabajoEmpleados.jrxml";
        Thread t=new Thread(){
            @Override
            public void run(){

                
                try {
                    jProgressBar1.setValue(5);
                    jProgressBar1.setString("Ingresando parametros");
                   
                    jProgressBar1.setValue(15);
                    jProgressBar1.setString("Compilando reporte");
                    JasperReport reporte=JasperCompileManager.compileReport(archivo);
                    jProgressBar1.setValue(55);
                    jProgressBar1.setString("Generando reporte");
                    HashMap map=new HashMap();
                    map.put("OrdenTrabajo", ordentrabajo);
                    map.put("CodigoEmpresa", ValoresEstaticos.idempresa);
                    jProgressBar1.setValue(60);
                    jProgressBar1.setString("Cargando parametros");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, map, new ModuloMySQL().getConexion());
                    JasperViewer jw=new JasperViewer(jasperPrint,false);
                    jw.setTitle("Generador de Ordenes de Trabajo "+ordentrabajo);
                    jw.setExtendedState(Frame.MAXIMIZED_BOTH);
                    jw.setVisible(true);
                    jProgressBar1.setValue(90);
                    jProgressBar1.setString("Visualizando Reporte");
                    jw.setVisible(true);
                    jProgressBar1.setValue(100);
                    jProgressBar1.setString("Terminado");
                    jProgressBar1.setValue(0);
                    jProgressBar1.setString("Listo");
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
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        generateOrden();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreacionOrdenTrabajo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreacionOrdenTrabajo().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAgregar;
    private javax.swing.JButton jAgregar1;
    private javax.swing.JButton jAgregar2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton22;
    private javax.swing.JComboBox jCategoria;
    private javax.swing.JComboBox jEmpleado;
    private javax.swing.JTextField jFecha;
    private javax.swing.JFormattedTextField jHoraFin;
    private javax.swing.JFormattedTextField jHoraInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelVersion16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JComboBox jPlantel;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jSubmantenimiento;
    private javax.swing.JLabel jSubtitulo;
    private javax.swing.JTable jTablaTareas;
    private javax.swing.JLabel jTiempoTotal;
    private javax.swing.JComboBox jTipoMantenimiento;
    private javax.swing.JLabel jTitulo;
    private javax.swing.JComboBox jUnidad;
    // End of variables declaration//GEN-END:variables
    static int ordentrabajo;
    static int unidad;
    static int idPlantel;
    static ModuloMySQL modulo=new ModuloMySQL();
    static OrdenesTrabajo datos=new OrdenesTrabajo();
    static int idTipoMantenimiento;
    static int idSubmanteniento;
    static int idEmpleado;
    static int idPuesto;
    static ResultSet result_tiposmantimiento;
    static ResultSet result_categorias;
    static int idCategoria;
    static ResultSet result_trabajos;
    static ResultSet result_empleados;
    static boolean saveOrden=true;
    static final Object[] columnames=new Object[]{"Consecutivo","Unidad","Trabajo a Realizar","Encargado"};
    static DefaultTableModel tablemodel=new DefaultTableModel(columnames,0);
    String trabajoselecionado;
    String nombreempleado;
}
