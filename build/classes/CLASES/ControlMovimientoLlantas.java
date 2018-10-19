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
public class ControlMovimientoLlantas {
    static Date fecha;
    static int unidad;
    static float kilometraje;
    static String codllanta_sale;
    static float profundidadllantasale;
    static String posicionllantasale;
    static int destinollantasale;
    static String codllanta_entra;
    static float profundidadllantaentra;
    static float presionaire;
    int unidadprevia;
    static int codigoEmpresa=ValoresEstaticos.getIdEmpresa();
       
    
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFecha(Date t){
        fecha=t;
    }
    public void setCodLlantaSale(String s){
        codllanta_sale=s;
    }
    public void setCodLlantaEntra(String s){
        codllanta_entra=s;
    }
    
    public void setProfundidadLlantaSale(float f){
        profundidadllantasale=f;
    }
    public void setProfundidadLlantaEntra(float f){
        profundidadllantaentra=f;
    }
    
    public void setPosicionLlantaSale(String s){
        posicionllantasale=s;
    }
    public void setSestinoLlantaSale(int i){
        destinollantasale=i;
    }
    public void setUnidad(int i){
        unidadprevia=i;
        unidad=i;
    }
    public int getUnidad(){
        return unidad;
    }
    public void setPresionAire(float f){
        presionaire=f;
    }
    public void setUnidadPrevia(){
        unidad=unidadprevia;
    } 
    public int getUnidadPrevia(){
        return unidadprevia;
    }
    public void setKilometraje(float f){
        kilometraje=f;
    }
    public void clearData(){
        fecha=null;
        unidad=0;
        kilometraje=0;
        codllanta_sale="";
        profundidadllantasale=0;
        posicionllantasale="";
        destinollantasale=0;
        codllanta_entra="";
        profundidadllantaentra=0;
        presionaire=0;
        
    }
    public boolean saveDataMySql(){
        String s;
        if (codllanta_sale!=null){
            s="insert into Mantenimiento_MovimientoLlantas (fecha, Unidad, Kilometraje, Cod_Llanta_Sale, Profundidad_Llanta_Sale, Posicion_Llanta_Sale, Destino_Llanta_Sale, Cod_Llanta_Entra, Profundidad_Llanta_Entra, Posicion_Llanta_Entra, Presion_Aire_Llanta_Entra, IdEmpresa, User) values ('"+fecha+"','"+unidad+"','"+kilometraje+"','"+codllanta_sale+"','"+profundidadllantasale+"','"+posicionllantasale+"','"+destinollantasale+"','"+codllanta_entra+"','"+profundidadllantaentra+"','"+posicionllantasale+"','"+presionaire+"','"+codigoEmpresa+"','"+DataUser.getusername()+"')";
            
        }
        else{
            s="insert into Mantenimiento_MovimientoLlantas (fecha, Unidad, Kilometraje, Cod_Llanta_Sale, Profundidad_Llanta_Sale, Posicion_Llanta_Sale, Destino_Llanta_Sale, Cod_Llanta_Entra, Profundidad_Llanta_Entra, Posicion_Llanta_Entra, Presion_Aire_Llanta_Entra, IdEmpresa, User) values ('"+fecha+"','"+unidad+"','"+kilometraje+"',"+null+",'"+22+"','"+posicionllantasale+"','"+destinollantasale+"','"+codllanta_entra+"','"+profundidadllantaentra+"','"+posicionllantasale+"','"+presionaire+"','"+codigoEmpresa+"','"+DataUser.getusername()+"')";
            
        }
        
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
            if (a.contains("Duplicate")&a.contains("Cod_Llanta")){
               JOptionPane.showMessageDialog(null, "Codigo de Llanta Duplicado. No se puede guardar"); 
            }
            else{
                JOptionPane.showMessageDialog(null, a);
            }
        return false;}
    }
    
    
    
}
