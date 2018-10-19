/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASES;

import PANTALLAS.Espere;

/**
 *
 * @author erobles
 */
public class ValoresEstaticos {
    public static int idempresa;
    public static String nombreEmpresa;
    public static float version_sci=(float)2.42;
    public static String idPC;
    public static Espere espere;
    public static String title;
    public static void setIdEmpresa(int i){
    idempresa=i;
    }
   public static void setIdPC(String s){
       idPC=s;
   }
    public static int getIdEmpresa(){
    return idempresa;
    }
    public static void setNombreEmpresa(String i){
    nombreEmpresa=i;
    }
   
    public static String getNombreEmpresa(){
    return nombreEmpresa;
    }
    public static void muestreMensajeEsperar(String s){
        title=s;
        espere=new Espere(null,false,title);
        espere.setVisible(true);
    }
    public static void changeMensaje(String s){
        espere.setProgressBarrTitle(s);
    }
    public static boolean isMensajeOn(){
        return espere.isVisible();
    }
    public static void ocultaMensaje(){
        if (espere!=null){
        espere.setVisible(false);}
    }
    
    
}
