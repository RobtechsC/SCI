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
public class IngresoNuevaBateria {
    static Date fecha;
    static int codigoEmpresa=ValoresEstaticos.getIdEmpresa();
   
    static int IdMarca;
    static int IdProveedor;
    static String CodBateria;
    static float precio;
    static String recibe;
   
    static int estadobateria;
    static int tipoinventario;
       
    
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFecha(Date t){
        fecha=t;
    }
    public void setCodBateria(String s){
        CodBateria=s;
    }
    public void setCodigoEmpresa(int i){
        codigoEmpresa=i;
    }
    public void setInventario(int i){
        tipoinventario=i;
    }
    
    public void setEstadoBateria(int i){
        estadobateria=i;
    }
    public void setPrecio(float f){
        precio=f;
    }
    
    public void setIdMarca(int i){
        IdMarca=i;
    }
    public void setIdProveedor(int i){
        IdProveedor=i;
    }
    public void setRecibe(String s){
        recibe=s;
    }
    
    public void clearData(){
        fecha=null;
        estadobateria=0;
        tipoinventario=0;
        precio=0;
        recibe="";
        IdMarca=0;
        IdProveedor=0;
        CodBateria="";
        
    }
    public boolean saveDataMySql(){
        
        String s="insert into Mantenimiento_IngresoBateriasNuevas (Fecha, CodBateria, Marca, Proveedor, Precio, Recibe, Estado, Inventario, IdEmpresa, User) values ('"+fecha+"','"+CodBateria+"','"+IdMarca+"','"+IdProveedor+"','"+precio+"','"+recibe+"','"+estadobateria+"','"+tipoinventario+"','"+codigoEmpresa+"','"+DataUser.getusername()+"')";
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
    public boolean updateStatusLlanta(){
        
        String s="update Mantenimiento_IngresoBateriasNuevas set Estado='"+estadobateria+"', Inventario='"+tipoinventario+"' where CodBateria='"+CodBateria+"'";
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
