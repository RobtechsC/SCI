/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASES;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BlackCrystal™
 */
public class GetDataRegistrosSOL {
    
    public String idtipo;
    public int longitud;
    public String idsubtipo;
    public String conductor;
    public String hora;
    public String lugar;
    public String comentarios;
    public String ruta;
    public String km;
    public String rendimiento;
    ModuloMySQLSOL modulo=new ModuloMySQLSOL();
    public ResultSet res;
    public String[]data;
    
    
    public void getDatafromSQL(String sentencia, int longi){
     
     data=new String[longi];
     //JOptionPane.showMessageDialog(null, sentencia);
     //JOptionPane.showMessageDialog(null, query);
     longitud=longi;    
     int i=0;
     //try
    // {
        res=modulo.Listar(sentencia);
        /*Logger.getLogger(GetDataRegistros.class.getName()).log(Level.SEVERE, sentencia);
        //if (res.next()&&res!=null){            
        while((i==longi)==false)
        {
            
            JOptionPane.showMessageDialog(null, res.getString(1));
            
            data[i]=res.getObject(i+1).toString();
            i++;
        }*/
     //}
        /*else{
            JOptionPane.showMessageDialog(null, "NO hay datos");
            while((i==longi)==false)
        {
            data[i]="";
            i++;
        }
        }*/
        
        
        
     /*} 
     catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "No hay mas registros");
            Logger.getLogger(GetDataRegistros.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        */
     //return data;
    }
    public String eliminaRegistro(String sentencia) {
        ModuloMySQL Objmod = new ModuloMySQL();
        return Objmod.Ejecutar(sentencia);
    }//FIN METODO ELIMINA
    public boolean goNext(){
    try 
    {
        return res.next();          
    } 
    catch (SQLException ex) {
        Logger.getLogger(GetDataRegistrosSOL.class.getName()).log(Level.SEVERE, null, ex);
        return false;
            
     }
     
        
    }
    public String[] goPrev(){
     try {
            res.previous();
            
        } catch (SQLException ex) {
            Logger.getLogger(GetDataRegistrosSOL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
        
    }
    public ResultSet getSQLData(String s){
    return modulo.Listar(s);
    }
    public String[] getDatos(){
    data=new String[longitud];
    int i=0;
    try{
         while((i==longitud)==false){
         data[i]=res.getString(i+1);
         i++;
         }
        
        } catch (SQLException ex) {
             data=null;
            return data;
            
        }
    return data;
        
    
}
    public String[] goFirst(){
       try{
           
           if(res.first()){
           getDatos();     }    
            
        } catch (SQLException ex) {
            Logger.getLogger(GetDataRegistrosSOL.class.getName()).log(Level.SEVERE, null, ex);
            String[] a=null;
            return a;
        }
        
        return data;
    }
    
    
    
    public String[] goLast(){
        try {
            res.last();
            getDatos();
        } catch (SQLException ex) {
            Logger.getLogger(GetDataRegistrosSOL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
}
