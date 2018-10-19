/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import java.sql.Timestamp;
import java.sql.Date;
import javax.swing.JOptionPane;


/**
 *
 * @author Esteban
 */
public class ControlSemanalPresionesLlantas {
    static Date fecha;
    static int unidad;
    static String reponsable;
    static int PresionDI;
    static int PresionDD;
    static int PresionTII;
    static int PresionTIE;
    static int PresionTDI;
    static int PresionTDE;
    static int PresionTAI;
    static int PresionTAD;
    static int predio;
    static int codigoEmpresa=ValoresEstaticos.getIdEmpresa();
       
    
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFecha(Date t){
        fecha=t;
    }
    public void setResponsable(String s){
        reponsable=s;
    }
    public void setPresionDD(int s){
        PresionDD=s;
    }
    public void setPredio(int i){
        predio=i;
    }
    public void setPresionDI(int s){
        PresionDI=s;
    }
    
    public void setPresionTII(int f){
        PresionTII=f;
    }
    public void setPresionTIE(int f){
        PresionTIE=f;
    }
    
    public void setPresionTDI(int s){
        PresionTDI=s;
    }
    public void setPresionTDE(int i){
        //JOptionPane.showMessageDialog(null, i);
        PresionTDE=i;
    }
    public void setUnidad(int i){
        unidad=i;
    }
    public void setPresionTAI(int f){
        PresionTAI=f;
    }
    public void setPresionTAD(int f){
        PresionTAD=f;
    }
    public void clearData(){
        fecha=null;
        unidad=0;
        PresionTAI=0;
        PresionDD=0;
        PresionDI=0;
        PresionTAD=0;
        PresionTDE=0;
        PresionTDI=0;
        PresionTIE=0;
        PresionTII=0;
        
    }
    public boolean saveDataMySql(){
        String s="insert into ControlSemanalPresionesLlantas (Fecha, Unidad, PresionDI, PresionDD, PresionTII, PresionTIE, PresionTDI, PresionTDE, PresionTAI, PresionTAD, IdEmpresa, Responsable, Predio, User) values ('"+fecha+"','"+unidad+"','"+PresionDI+"','"+PresionDD+"','"+PresionTII+"','"+PresionTIE+"','"+PresionTDI+"','"+PresionTDE+"','"+PresionTAI+"','"+PresionTAD+"','"+codigoEmpresa+"','"+reponsable+"','"+predio+"','"+DataUser.getusername()+"')";
        
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
            clearData();
            JOptionPane.showMessageDialog(null, a);
            return true;
        }
        else{
            clearData();
            JOptionPane.showMessageDialog(null, a);
            return false;
        }
        
    }
    public boolean updateDataMySql(int consecutivo){
        String s="update ControlSemanalPresionesLlantas set Fecha='"+fecha+"', Unidad='"+unidad+"', PresionDI='"+PresionDI+"', PresionDD='"+PresionDD+"', PresionTII='"+PresionTII+"', PresionTIE='"+PresionTIE+"', PresionTDI='"+PresionTDI+"', PresionTDE='"+PresionTDE+"', PresionTAI='"+PresionTAI+"', PresionTAD='"+PresionTAD+"', Responsable='"+reponsable+"', Predio='"+predio+"' where Consecutivo='"+consecutivo+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
        JOptionPane.showMessageDialog(null, s);
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
