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
public class IngresoNuevaLlanta {
    static Date fecha;
    static int codigoEmpresa=ValoresEstaticos.getIdEmpresa();
    static float profundidad;
    static int DOT;
    static int IdMarca;
    static int IdProveedor;
    static String CodLlanta;
    static int size;
    static int estadollanta;
    static int tipoinventario;
    static int estadoactual;   
    
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFecha(Date t){
        fecha=t;
    }
    public void setCodLlanta(String s){
        CodLlanta=s;
    }
    public void setCodigoEmpresa(int i){
        codigoEmpresa=i;
    }
    public void setSize(int i){
        size=i;
    }
    public void setEstadoActual(int i){
        estadoactual=i;
    }
    public void setTipoInventario(int i){
        tipoinventario=i;
    }
    public void setEstadoLlanta(int i){
        estadollanta=i;
    }
    public void setProfundidad(float f){
        profundidad=f;
    }
    public void setDOT(int i){
        DOT=i;
    }
    public void setIdMarca(int i){
        IdMarca=i;
    }
    public void setIdProveedor(int i){
        IdProveedor=i;
    }
    
    public void clearData(){
        fecha=null;
        estadoactual=0;
        profundidad=0;
        profundidad=0;
        IdMarca=0;
        IdProveedor=0;
        CodLlanta="";
        
    }
    public boolean saveDataMySql(){
        
        String s="insert into Mantenimiento_IngresoLlantasNuevas (fecha, Cod_Llanta, Marca, DOT, Tamaño, EstadoLlanta, Profundidad, Proveedor, IdEmpresa, Status, User) values ('"+fecha+"','"+CodLlanta+"','"+IdMarca+"','"+DOT+"','"+size+"','"+tipoinventario+"','"+profundidad+"','"+IdProveedor+"','"+codigoEmpresa+"','"+estadoactual+"','"+DataUser.getusername()+"')";
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
        
        String s="update Mantenimiento_IngresoLlantasNuevas set Status=3, EstadoLlanta='"+tipoinventario+"' where Cod_Llanta='"+CodLlanta+"'";
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
