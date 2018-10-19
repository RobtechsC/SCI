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
 * @author erobles
 */
public class ControlMarias {
    public static int idMaria;
    private static float aperturaM1;
    private static float cierreM1;
    private static float aperturaM2;
    private static float cierreM2;
    private static float aperturaM3;
    private static float cierreM3;
    private static float aperturaM4;
    private static float cierreM4;
    private static int idPlantel;
    private static java.sql.Date fecha;
    private static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setAperturaM1(float i){
        aperturaM1=i;
    }
    public void setCierreM1(float i){
        cierreM1=i;
    }
    public void setAperturaM2(float i){
        aperturaM1=i;
    }
    public void setCierreM2(float i){
        cierreM1=i;
    }
    public void setAperturaM3(float i){
        aperturaM1=i;
    }
    public void setCierreM3(float i){
        cierreM1=i;
    }
    public void setAperturaM4(float i){
        aperturaM1=i;
    }
    public void setCierreM4(float i){
        cierreM1=i;
    }
    public void setFecha(Date d){
        fecha=d;
    }
    public void setIdPlantel (int i){
        idPlantel=i;
    }
    public int getIdMaria(){
        return idMaria;
    }
    public void clearData(){
        aperturaM1=0;
        cierreM1=0;
        aperturaM2=0;
        cierreM2=0;
        aperturaM3=0;
        cierreM3=0;
        aperturaM4=0;
        cierreM4=0;
    }
    public boolean saveControlMarias(){
        String s="insert into Control_Marias (Fecha, AperturaM1, CierreM1, AperturaM2, CierreM2, AperturaM3, CierreM3, AperturaM4, CierreM4, ID_Plantel, ID_Empresa, User) values ('"+fecha+"','"+aperturaM1+"','"+cierreM1+"','"+aperturaM2+"','"+cierreM2+"','"+aperturaM3+"','"+cierreM3+"','"+aperturaM4+"','"+cierreM4+"','"+idPlantel+"','"+ValoresEstaticos.idempresa+"','"+DataUser.username+"')";
        s=modulo.Ejecutar(s);
        if (s.contains("concluyó")){
        ResultSet rs=modulo.Listar("select Id_Maria from Control_Marias where ID_Empresa='"+ValoresEstaticos.idempresa+"' Order by Id_Maria Desc Limit 1");
        if (rs!=null){
            try {
                rs.first();
                idMaria=rs.getInt("Id_Maria");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ControlMarias.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
        }
        else{
            JOptionPane.showMessageDialog(null, s);
            return false;
        }
    }
    public boolean updateControlMarias(int consecutivo){
        String s="update Control_Marias set Fecha='"+fecha+"', AperturaM1='"+aperturaM1+"', CierreM1='"+cierreM1+"', AperturaM2='"+aperturaM2+"', CierreM2='"+cierreM2+"', AperturaM3='"+aperturaM3+"', CierreM3='"+cierreM3+"', AperturaM4='"+aperturaM4+"', CierreM4='"+cierreM4+"', ID_Plantel='"+idPlantel+"' where Id_Maria='"+consecutivo+"' and Id_Empresa='"+ValoresEstaticos.getIdEmpresa()+"'";
        modulo.Ejecutar(s);
        ResultSet rs=modulo.Listar("select Id_Maria from Control_Marias where IdEmpresa='"+ValoresEstaticos.idempresa+"' Order by Id_Maria Desc Limit 1");
        if (rs!=null){
            try {
                rs.first();
                idMaria=rs.getInt("Id_Maria");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ControlMarias.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }
}
