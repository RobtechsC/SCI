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
public class ControlMovimientoBaterias {
    static Date fechabateriaentra;
    static Date fechabateriasale;
    static int unidad;
    static float kilometraje;
    static String codbateria_sale;
    static int destinobateriasale;
    static String codbateria_entra; 
    static String responsable;
    static int codigoEmpresa=ValoresEstaticos.getIdEmpresa();   
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFechaEntra(Date t){
        fechabateriaentra=t;
    }
    public void setFechaSale(Date t){
        fechabateriasale=t;
    }
    public void setCodBateriaSale(String s){
        codbateria_sale=s;
    }
    public void setCodBateriaEntra(String s){
        codbateria_entra=s;
    }
    public void setKilometraje(float f){
        kilometraje=f;
    }
    public void setResponsable(String s){
        responsable=s;
    }
   
    public void setDestinoBateriaSale(int i){
        destinobateriasale=i;
    }
    public void setUnidad(int i){
        unidad=i;
    }
    public int getUnidad(){
        return unidad;
    }
    public void Responsable(String f){
        responsable=f;
    }
    public void clearData(){
        fechabateriaentra=null;
        fechabateriasale=null;
        unidad=0;
        kilometraje=0;
        responsable="";
        codbateria_entra="";
        codbateria_sale="";
        destinobateriasale=0;
        
        
        
    }
    public boolean saveDataMySql(){
        String s;
        if (codbateria_sale!=null){
            s="insert into Mantenimiento_MovimientoBaterias (FechaBateriaSale, Unidad, FechaBateriaEntra, CodBateriaSaliente, Destino, CodBateriaEntrante, Kilometraje, Responsable, IdEmpresa, User) values ('"+fechabateriaentra+"','"+unidad+"','"+fechabateriasale+"','"+codbateria_sale+"','"+destinobateriasale+"','"+codbateria_entra+"','"+kilometraje+"','"+responsable+"','"+ValoresEstaticos.getIdEmpresa()+"','"+DataUser.getusername()+"')";
            
        }
        else{
            s="insert into Mantenimiento_MovimientoBaterias (FechaBateriaSale, Unidad, FechaBateriaEntra, CodBateriaSaliente, Destino, CodBateriaEntrante, Kilometraje, Responsable, IdEmpresa, User) values ('"+fechabateriaentra+"','"+unidad+"',"+null+","+null+",'"+destinobateriasale+"','"+codbateria_entra+"','"+kilometraje+"','"+responsable+"','"+ValoresEstaticos.getIdEmpresa()+"','"+DataUser.getusername()+"')";
            
            
        }
        
        String a=modulo.Ejecutar(s);
        
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
            if (a.contains("Duplicate")&a.contains("CodBateria")){
               JOptionPane.showMessageDialog(null, "Codigo de Bateria Duplicado. No se puede guardar"); 
            }
            else{
                if (a.contains("child"))
                JOptionPane.showMessageDialog(null, "Unidad digitada no se encuentra registrada"+a);
            }
        return false;}
    }
    
    
    
}
