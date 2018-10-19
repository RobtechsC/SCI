/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASES;

import javax.swing.JOptionPane;

/**
 *
 * @author erobles
 */
public class AdministracionUsuarios {
    static boolean ingresollantas;
    static boolean ingresodiesel;
    static boolean reporteriadiesel;
    static boolean ingresobaterias;
    static boolean ingresoaceites;
    static boolean cierreorden;
    static boolean creacionorden;
    static boolean reporteriallantas;
    static boolean reporteriabaterias;
    static boolean reporteriaaceites;
    static boolean reporteriaorden;
    static boolean parametros;
    static String usuario;
    static boolean setPassword;
    static String password;
    static boolean reporteriageneral;
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public static void setIngresoLlantas(boolean b){
        ingresollantas=b;
    }
    public static void setIngresoBaterias(boolean b){
        ingresobaterias=b;
    }
    public static void setIngresoAceites(boolean b){
        ingresoaceites=b;
    }
    public static void setIngresoDiesel(boolean b){
        ingresodiesel=b;
    }
    public static void setReporteriaDiesel(boolean b){
        reporteriadiesel=b;
    }
    public static void setReporteriaLlantas(boolean b){
        reporteriallantas=b;
    }
    public static void setReporteriaBaterias(boolean b){
        reporteriabaterias=b;
    }
    public static void setReporteriaAceites(boolean b){
        reporteriaaceites=b;
    }
    public static void setReporteriaOrden(boolean b){
        reporteriaorden=b;
    }
    public static void setParametros(boolean b){
        parametros=b;
    }
    public static void setCierreOrden(boolean b){
        cierreorden=b;
    }
    public static void setCreacionOrden(boolean b){
        creacionorden=b;
    }
    public static void setUser(String user){
        usuario=user;
    }
    public static void setPassword(String pass){
        setPassword=true;
        password=pass;
    }
    public static void clearData(){
        ingresollantas=false;
        ingresobaterias=false;
        ingresoaceites=false;
        cierreorden=false;
        creacionorden=false;
        reporteriallantas=false;
        reporteriabaterias=false;
        reporteriaaceites=false;
        reporteriaorden=false;
        parametros=false;
        usuario="";
        setPassword=false;
        password="";
        ingresodiesel=false;
        reporteriadiesel=false;
    }
    public static boolean setPermisosUsuario(){
        reporteriageneral = reporteriaaceites||reporteriabaterias||reporteriallantas||reporteriaorden;
        String s="update ListadoUsuariosxEmpresa set reporteria_general="+reporteriageneral+", ingreso_control_ingresos="+ingresodiesel+", reportes_control_ingresos="+reporteriadiesel+", ingreso_control_llantas="+ingresollantas+", reportes_control_llantas="+reporteriallantas+", ingresos_control_aceites="+ingresoaceites+", reporteria_control_aceites="+reporteriaaceites+", ingresos_control_baterias="+ingresobaterias+", reporteria_control_baterias="+reporteriabaterias+", creacion_ordenestrabajo="+creacionorden+", cierre_ordenestrabajo="+cierreorden+", reporteria_ordentrabajo="+reporteriaorden+", cambios_parametros_empresa="+parametros+" where Usuario='"+usuario+"' and IdEmpresa='"+ValoresEstaticos.idempresa+"'";
        s=modulo.Ejecutar(s);
        if (s.contains("concluyó")){
            clearData();
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, s);
            clearData();
            return false;
        }
    }
    
    
}
