/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author erobles
 */
public class OrdenesTrabajo {
    private static Date fecha_ordentrabajo;
    private static int bus;
    private static int idplantel;
    private static float tiempototal_ordentrabajo;
    public static Time horainicio;
    private static Time horafin;
    private static int estado_ordentrabajo;
    private static int idEmpleado;
    private static int idSubmantenimiento;
    private static int idAlarma;
    private static Date fecha_proxtrabajo;
    private static int km_proxtrabajo;
    private static int numero_ordentrabajo;
    private static ModuloMySQL modulo=new ModuloMySQL();
    public static int lastConsecutivo;
    public static long tiempototal;
    private ResultSet rs;
    
    public void setFechaOrdenTrabajo(Date d){
        fecha_ordentrabajo=d;
    }
    public void setBus(int i){
        bus=i;
    }
    public void setIdPlantel(int i){
        idplantel=i;
    }
    public void setNumeroOrden(int i){
        numero_ordentrabajo=i;
    }
    public void setTiempoOrdenTrabajo(float f){
        tiempototal_ordentrabajo=f;
    }
    public void setHoraInicio(Time t){
        horainicio=t;
        tiempototal=t.getTime();
    }
    public void setHoraFin(Time t){
        horafin=t;
    }
    public void setEstadoOrden(int i){
        estado_ordentrabajo=i;
    }
    public void setIdEmpleado(int i){
        idEmpleado=i;
    }
    public void setIdSubmantenimiento(int i){
        idSubmantenimiento=i;
    }
    public void setIdAlarma(int i){
        idAlarma=i;
    }
    public void setFechaProxTrabajo(Date d){
        fecha_proxtrabajo=d;
    }
    public int getLastConsecutivo(){
        return lastConsecutivo;
    }
    public void setProxKmTrabajo(int  i){
        km_proxtrabajo=i;
    }
    public boolean saveOrdenTrabajo(){
        String s="insert into Ordenes_Trabajo values ('"+numero_ordentrabajo+"','"+fecha_ordentrabajo+"','"+bus+"','"+idplantel+"','"+tiempototal_ordentrabajo+"','"+horainicio+"',"+null+",'"+ValoresEstaticos.idempresa+"','"+estado_ordentrabajo+"','"+DataUser.getusername()+"')";
        //JOptionPane.showMessageDialog(null, s);
        s=modulo.Ejecutar(s);
        if (s.contains("concluyó")){
            return saveTrabajoOrdenTrabajo();
        }
        else{
            JOptionPane.showMessageDialog(null, "Error al guardar la orden de trabajo "+s);
            return false;
        }
    }
    public boolean saveTrabajoOrdenTrabajo(){
        String s="insert into SubOrdenes_Trabajo (Id_OrdenTrabajo, Id_Empleado, Id_TrabajoRealizado) values ('"+numero_ordentrabajo+"','"+idEmpleado+"','"+idSubmantenimiento+"')";
        //JOptionPane.showMessageDialog(null, s);
        s=modulo.Ejecutar(s);
        rs=modulo.Listar("Select Id_SubMantenimiento from SubOrdenes_Trabajo order By Id_SubMantenimiento Desc Limit 1");
        if(s.contains("concluy")){
        try {
            if (rs.first()){
                
                lastConsecutivo=rs.getInt("Id_SubMantenimiento");
                rs=modulo.Listar("Select Tiempo_Estandar from Sub_Mantenimiento where ID_Submantenimiento='"+idSubmantenimiento+"'");
                rs.first();
                tiempototal=tiempototal+(rs.getInt("Tiempo_Estandar")*60000);
                //JOptionPane.showMessageDialog(null, tiempototal);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }}
        else{
            //JOptionPane.showMessageDialog(null, s);
            return false;
        }
        //JOptionPane.showMessageDialog(null, s);
        return false;
        
        
    }
    
}
