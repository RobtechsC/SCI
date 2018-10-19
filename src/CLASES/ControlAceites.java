/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Esteban
 */
public class ControlAceites {
    static Date fecha;
    static Date fechaOld;
    static int unidad;
    static int idCambioAceite;
    static int idPlantel;
    static int cantidad_cuarto_galon;
    static int codigoEmpresa=ValoresEstaticos.getIdEmpresa();
    static String responsable;
       
    
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFecha(Date t){
        fecha=t;
    }
    public void setFechaOld(Date t){
        fechaOld=t;
    }
    public void setPlantel(int i){
        idPlantel=i;
    }
    public void setResponsable(String s){
        responsable=s;
    }
    public void setCantidadCuartosGalon(int i){
        cantidad_cuarto_galon=i;
    }
    
    public void setUnidad(int i){
        unidad=i;
    }
    
    public void clearData(){
        fecha=null;
        idCambioAceite=0;
        cantidad_cuarto_galon=0;
        idPlantel=-1;
        
        
    }
    public boolean saveDataMySql(){
        String s="insert into Control_Aceites (Fecha, Unidad, Cantidad_Cuartos_Galon, IdEmpresa, Lugar, User) values ('"+fecha+"','"+unidad+"','"+cantidad_cuarto_galon+"','"+codigoEmpresa+"','"+idPlantel+"','"+DataUser.getusername()+"')";
        
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
            if (a.contains("key")){
        JOptionPane.showMessageDialog(null, "Unidad digitada no permitida. Por favor ingrese una unidad valida. Si el numero de unidad esta correcto por favor avisar al administrator del sistema.");}
        return false;
        }
        
    }
    public Date getUltimoCambioAceite(int Unidad){
        try {
            String s="select Consecutivo, Fecha from Control_CambiosAceite where Unidad='"+Unidad+"' Order by Fecha Desc Limit 1";
            ResultSet rs=modulo.Listar(s);
            if (rs!=null){
                if (rs.first()){
                    idCambioAceite=rs.getInt("Consecutivo");
                    return rs.getDate("Fecha");
                }
                else{
                    return null;
                }
                
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlAceites.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        catch (NullPointerException ex) {
            Logger.getLogger(ControlAceites.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
      
    }
    public void getPreviousCambioAceite(int Unidad, Date f){
        
        try {
            String s="select Consecutivo, Fecha from Control_CambiosAceite where Unidad='"+Unidad+"' and Fecha<'"+f+"' Order by Fecha Desc Limit 1";
           // JOptionPane.showMessageDialog(null, s);
            ResultSet rs=modulo.Listar(s);
            if (rs!=null){
                if (rs.first()){
                    idCambioAceite=rs.getInt("Consecutivo");
                    
                }
                
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ControlAceites.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        catch (NullPointerException ex) {
            Logger.getLogger(ControlAceites.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    public boolean saveDataCambioAceite(){
        getPreviousCambioAceite(unidad, fecha);
        String s="insert into Control_CambiosAceite (Fecha, Unidad, Cantidad, Lugar, Responsable, IdEmpresa, User) values ('"+fecha+"','"+unidad+"','"+cantidad_cuarto_galon+"','"+idPlantel+"','"+responsable+"','"+ValoresEstaticos.getIdEmpresa()+"','"+DataUser.getusername()+"')";
        if (idCambioAceite>0){
        modulo.Ejecutar("update Control_CambiosAceite set FechaNextCambio='"+fecha+"' where Consecutivo='"+idCambioAceite+"'");}
        idCambioAceite=0;
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
        JOptionPane.showMessageDialog(null, a);
        return false;
        }
        
    }
    public boolean updateRegister(int consecutivo){
        String s="update Control_Aceites set Fecha='"+fecha+"', Unidad='"+unidad+"', Cantidad_Cuartos_Galon='"+cantidad_cuarto_galon+"', Lugar='"+idPlantel+"' where Consecutivo='"+consecutivo+"' and IdEmpresa='"+codigoEmpresa+"'";
        //JOptionPane.showMessageDialog(null, s);
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
        JOptionPane.showMessageDialog(null, a);
        return false;
        }
        
    }
    public boolean updateRegisterCambio(int consecutivo){
        getPreviousCambioAceite(unidad, fechaOld);
        String s="update Control_CambiosAceite set Fecha='"+fecha+"', Unidad='"+unidad+"', Cantidad='"+cantidad_cuarto_galon+"', Lugar='"+idPlantel+"' where Consecutivo='"+consecutivo+"' and IdEmpresa='"+codigoEmpresa+"'";
        modulo.Ejecutar("update Control_CambiosAceite set FechaNextCambio='"+fecha+"' where Consecutivo='"+idCambioAceite+"'");
        //JOptionPane.showMessageDialog(null, s);
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
        JOptionPane.showMessageDialog(null, a);
        return false;
        }
        
    }
    
    
    
}
