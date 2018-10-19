/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import java.sql.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author erobles
 */
public class ControlReencauches {
    static Date fecha_boleta;
    static int proveedor;
    static int boleta_proveedor;
    static int boleta_tapachula;
    static String codigo_llanta;
    static Date fecha_entrada;
    static String factura;
    static float costo;
    static int estado;
    static int IdEmpresa;
    static ModuloMySQL modulo=new ModuloMySQL();
    
    
    public void setFechaBoleta (Date d){
        fecha_boleta=d;
    }
    public void setProveedor (int i){
        proveedor=i;
    }
    public void setBoletaProveedor(int i){
        boleta_proveedor=i;
    }
    public void setBoletaTapachula(int i){
        boleta_tapachula=i;
    }
    public void setCodigoLlanta(String s){
        codigo_llanta=s;
    }
    public void setFechaEntrada(Date d){
        fecha_entrada=d;
    }
    public void setFactura(String s){
        factura=s;
    }
    public void setCosto(float f){
        costo=f;
    }
    public void setEstado(int i){
        estado=i;
    }
    public void setIdEmpresa (int i){
        IdEmpresa=i;
    }
    public boolean saveData(){
       String s= modulo.Ejecutar("insert into Control_ReencauchesLlantas (Fecha_Boleta, Proveedor, Boleta_Proveedor, Boleta_Tapachula, CodLlanta, Fecha_Entrada, Factura, Costo, Estado, IdEmpresa, User) values ('"+fecha_boleta+"','"+proveedor+"','"+boleta_proveedor+"','"+boleta_tapachula+"','"+codigo_llanta+"',"+null+","+null+","+null+",'"+2+"','"+IdEmpresa+"','"+DataUser.username+"')");
       if (s.contains("concluy")){
           return true;
       }
       else{
           JOptionPane.showMessageDialog(null, s);
           return false;
       }
    }
    public boolean updateData(int Consecutivo){
       String s= modulo.Ejecutar("update Control_ReencauchesLlantas set Fecha_Boleta='"+fecha_boleta+"', Proveedor='"+proveedor+"', Boleta_Proveedor='"+boleta_proveedor+"', Boleta_Tapachula='"+boleta_tapachula+"', CodLlanta='"+codigo_llanta+"', Fecha_Entrada='"+fecha_entrada+"', Factura='"+factura+"', Costo='"+costo+"', Estado='"+estado+"'");
       if (s.contains("concluy")){
           JOptionPane.showMessageDialog(null, s,"Sistema de Control de Indicadores",JOptionPane.INFORMATION_MESSAGE);
           return true;
       }
       else{
           JOptionPane.showMessageDialog(null, "Error no se pudo guardar el registro","Sistema de Control de Indicadores",JOptionPane.ERROR_MESSAGE);
           return false;
       }
    }
    
}
