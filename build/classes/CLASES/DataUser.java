/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASES;

/**
 *
 * @author erobles
 */
public class DataUser {
    public static String username;
    public static boolean administrator;
    public static String password;
    public static boolean permitereporteria;
    public static boolean permiso_control_ingresos;
    public static boolean permiso_reporteria_control_ingresos;
    public static boolean permiso_control_llantas;
    public static boolean permiso_reporteria_control_llantas;
    public static boolean permiso_control_aceites;
    public static boolean permiso_reporteria_control_aceites;
    public static boolean permiso_control_baterias;
    public static boolean permiso_reporteria_control_baterias;
    public static boolean permiso_control_parametros;
    public static boolean permiso_creacion_ordenestrabajo;
    public static boolean permiso_cierre_ordenestrabajo;
    
    
    public DataUser(){
    
    }
   
  
    public void setusername(String s){
    DataUser.username=s;
    }
    public static void setAdministrator(boolean b){
        administrator=b;
    }
    public void setPassword(String s){
        DataUser.password=s;
    }
    public static String getPassword(){
        return DataUser.password;
    }
    public static void setPermisoReporteriaGeneral(boolean b){
        DataUser.permitereporteria=b;
    }
    public static void setPermisoControlIngresos(boolean b){
        DataUser.permiso_control_ingresos=b;
    }
    public static void setPermisoCreacionOrdenesTrabajo(boolean b){
        permiso_creacion_ordenestrabajo=b;
    }
    public static void setPermisoCierreOrdenesTrabajo(boolean b){
        permiso_cierre_ordenestrabajo=b;
    }
    public static void setPermisoControlAceites(boolean b){
        DataUser.permiso_control_aceites=b;
    }
    public static void setPermisoControlLlantas(boolean b){
        DataUser.permiso_control_llantas=b;
    }
    public static void setPermisoControlBaterias(boolean b){
        DataUser.permiso_control_baterias=b;
    }
    public static void setPermisoControlParametros(boolean b){
        DataUser.permiso_control_parametros=b;
    }
    public static void setPermisoReporteriaControlIngresos(boolean b){
        DataUser.permiso_reporteria_control_ingresos=b;
    }
    public static void setPermisoReporteriaControlAceites(boolean b){
        DataUser.permiso_reporteria_control_aceites=b;
    }
    public static void setPermisoReporteriaControlLlantas(boolean b){
        DataUser.permiso_reporteria_control_llantas=b;
    }
    public static void setPermisoReporteriaControlBaterias(boolean b){
        DataUser.permiso_reporteria_control_baterias=b;
    }
    
    public static String getusername(){
    return DataUser.username;
    }
    public boolean getadministrator(){
    return this.administrator;
    }
}
