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
public class ControlSemanalLlantas {
    static Date fecha;
    static int unidad;
    static String reponsable;
    static int profundidadDI;
    static int profundidadDD;
    static int profundidadTII;
    static int profundidadTIE;
    static int profundidadTDI;
    static int profundidadTDE;
    static int profunddadTAI;
    static int profundidadTAD;
    static int predio;
    static int codigoEmpresa=ValoresEstaticos.getIdEmpresa();
       
    
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFecha(Date t){
        fecha=t;
    }
    public void setResponsable(String s){
        reponsable=s;
    }
    public void setProfundidadDD(int s){
        profundidadDD=s;
    }
    public void setPredio(int i){
        predio=i;
    }
    public void setProfundidadDI(int s){
        profundidadDI=s;
    }
    
    public void setProfundidadTII(int f){
        profundidadTII=f;
    }
    public void setProfundidadTIE(int f){
        profundidadTIE=f;
    }
    
    public void setProfundidadTDI(int s){
        profundidadTDI=s;
    }
    public void setProfundidadTDE(int i){
        //JOptionPane.showMessageDialog(null, i);
        profundidadTDE=i;
    }
    public void setUnidad(int i){
        unidad=i;
    }
    public void setProfundidadTAI(int f){
        profunddadTAI=f;
    }
    public void setProfundidadTAD(int f){
        profundidadTAD=f;
    }
    public void clearData(){
        fecha=null;
        unidad=0;
        profunddadTAI=0;
        profundidadDD=0;
        profundidadDI=0;
        profundidadTAD=0;
        profundidadTDE=0;
        profundidadTDI=0;
        profundidadTIE=0;
        profundidadTII=0;
        
    }
    public boolean saveDataMySql(){
        String s="insert into ControlSemanalLlantas (Fecha, Unidad, ProfundidadDI, ProfundidadDD, ProfundidadTII, ProfundidadTIE, ProfundidadTDI, ProfundidadTDE, ProfundidadTAI, ProfundidadTAD, IdEmpresa, Responsable, Predio, User) values ('"+fecha+"','"+unidad+"','"+profundidadDI+"','"+profundidadDD+"','"+profundidadTII+"','"+profundidadTIE+"','"+profundidadTDI+"','"+profundidadTDE+"','"+profunddadTAI+"','"+profundidadTAD+"','"+codigoEmpresa+"','"+reponsable+"','"+predio+"','"+DataUser.getusername()+"')";
        
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
        String s="update ControlSemanalLlantas set Fecha='"+fecha+"', Unidad='"+unidad+"', ProfundidadDI='"+profundidadDI+"', ProfundidadDD='"+profundidadDD+"', ProfundidadTII='"+profundidadTII+"', ProfundidadTIE='"+profundidadTIE+"', ProfundidadTDI='"+profundidadTDI+"', ProfundidadTDE='"+profundidadTDE+"', ProfundidadTAI='"+profunddadTAI+"', ProfundidadTAD='"+profundidadTAD+"', Responsable='"+reponsable+"', Predio='"+predio+"' where Consecutivo='"+consecutivo+"' and IdEmpresa='"+ValoresEstaticos.getIdEmpresa()+"'";
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
