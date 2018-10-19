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
public class ControlUnidades {
    static int unidad;
    static int plantel;
    static int estado;
    static ModuloMySQL modulo=new ModuloMySQL();
    public void setUnidad(int i){
        unidad=i;
    }
    public void setEstado(int i){
        estado=i;
    }
    public void setPlantel(int s){
        plantel=s;
    }
    public void saveUnidad(){
        String resultado=modulo.Ejecutar("insert into UnidadesxEmpresa (Unidad, IdEmpresa, Plantel, Estado) values ('"+unidad+"','"+ValoresEstaticos.idempresa+"','"+plantel+"','1')");
        if (resultado.contains("Concluyó")){
            JOptionPane.showMessageDialog(null, "Se inserto el registro correctamente.");
        }
        else{
            JOptionPane.showMessageDialog(null, "No se guardo el registro","Sistema de Control de Indicadoress",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    public void clearData(){
        unidad=0;
        plantel=0;
        estado=0;
        
    }
    public void updateUnidad(){
        //String s="update UnidadesxEmpresa set Estado='"+estado+"' where IdEmpresa='"+ValoresEstaticos.idempresa+"'";
        String resultado="";
        if (plantel>0){
            resultado=modulo.Ejecutar("update UnidadesxEmpresa set Estado='"+estado+"', Plantel='"+plantel+"' where IdEmpresa='"+ValoresEstaticos.idempresa+"' and Unidad='"+unidad+"'");
        }
        else{
            resultado=modulo.Ejecutar("update UnidadesxEmpresa set Estado='"+estado+"' where IdEmpresa='"+ValoresEstaticos.idempresa+"' and Unidad='"+unidad+"'");
        }
        if (resultado.contains("concluyó")){
            JOptionPane.showMessageDialog(null, "Se inserto el registro correctamente.");
            clearData();
        }
        else{
            JOptionPane.showMessageDialog(null, "No se guardo el registro"+resultado,"Sistema de Control de Indicadoress",JOptionPane.ERROR_MESSAGE);
        }
    }
}
