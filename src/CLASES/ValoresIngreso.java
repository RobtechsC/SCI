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
public class ValoresIngreso {
    static Date fecha;
    static int codigoEmpresa;
    static String codigoConductor;
    static String Ramal="7";
    static String Unidad;
    static float Litros_Dispensados;
    static float kilometraje;
    static double ingresos;
    static int cantidad_pasajeros;
    static int cantidad_cedulas;
    static float cantidad_carreras;
    static float horas_rol;
    static float horas_barras;
    static float eficiencia_horas;
    static float marcas_efectivas;
    static int marcastotales_carrera;
    static int marcasefectivas_carrera;
    static float marcas_km;
    static float porcentaje_adultomayor;
    static float litros_carrera;
    static float litros_pasajero;
    static float litros_km;
    static float km_litros;
    static float dinero_carrera;
    static float dinero_km;
    static float totalkmrecorridos=0;
    static float totalcarreras=0;
    static int totaladultomayor=0;
    static float totallitrosdispensados=0;
    static int totalpasajeros=0;
    static float totalhorasbarras=0;
    static int plantel;
    static int consecutivo;
    static int idMaria;
    
    
    static ModuloMySQL modulo=new ModuloMySQL();
    
    public void setFecha(Date t){
        fecha=t;
    }
    public void setConsecutivo(int i){
        consecutivo=i;
    }
    public void setCodigoConductor(String s){
        codigoConductor=s;
    }
    public void setCodigoEmpresa(int i){
        codigoEmpresa=i;
    }
    public void setRamal(String s){
        Ramal=s;
    }
    public void setUnidad(String s){
        Unidad=s;
    }
    public void setIdPlantel(int i){
        plantel=i;
    }
    public void setLitrosDispensados(float f){
        Litros_Dispensados=f;
    }
    public void setKilometraje(float f){
        kilometraje=f;
    }
    public void setIngresos(float f){
        ingresos=f;
    }
    public void setCantidadPasajeros(int i){
        cantidad_pasajeros=i;
    }
    public void setCantidadAdultoMayor(int i){
        cantidad_cedulas=i;
    }
    public void setCantidadCarreras(float f){
        cantidad_carreras=f/2;
    }
    public void setHorasRol(float f){
        horas_rol=f;
    }
    public void setHorasBarras(float f){
        horas_barras=f;
    }
    public float getTotalKmRecorridos(){
        return totalkmrecorridos;
    }
    public int getTotalTotalAdultMayor(){
        return totaladultomayor;
    }
    public float getTotalCarreras(){
        return totalcarreras;
    }
    public float getTotalHorasBarras(){
        return totalhorasbarras;
    }
    public int getTotalPasajeros(){
        return totalpasajeros;
    }
    public float getTotalLitros(){
        return totallitrosdispensados;
    }
    public void setIdMaria(int i){
        idMaria=i;
    }
    public void calculoIndicadores(){
        if (ValoresEstaticos.getIdEmpresa()==3){
            totalkmrecorridos=totalkmrecorridos+kilometraje;
            totallitrosdispensados=totallitrosdispensados+Litros_Dispensados;
        }
        else{
            if (horas_rol>horas_barras){
            eficiencia_horas=(horas_barras*100)/horas_rol;}
            else{
                eficiencia_horas=100;
            }
            marcas_efectivas=(cantidad_pasajeros-cantidad_cedulas);
            marcastotales_carrera=(int)((cantidad_pasajeros)/cantidad_carreras);
            marcasefectivas_carrera=(int)((marcas_efectivas)/cantidad_carreras);
            marcas_km=cantidad_pasajeros/kilometraje;
            porcentaje_adultomayor=(cantidad_cedulas*100)/cantidad_pasajeros;
            litros_carrera=Litros_Dispensados/cantidad_carreras;
            litros_pasajero=Litros_Dispensados/cantidad_pasajeros;
            litros_km=Litros_Dispensados/kilometraje;
            dinero_carrera=(float) (ingresos/cantidad_carreras);
            dinero_km=(float) (ingresos/kilometraje);
            if (Litros_Dispensados>0){
                km_litros=kilometraje/Litros_Dispensados;
            }
            else{
                km_litros=0;
            
            }
            totaladultomayor=totaladultomayor+cantidad_cedulas;
            totalcarreras=totalcarreras+cantidad_carreras;
            totalhorasbarras=totalhorasbarras+horas_barras;
            totalkmrecorridos=totalkmrecorridos+kilometraje;
            totalpasajeros=totalpasajeros+cantidad_pasajeros;
            totallitrosdispensados=totallitrosdispensados+Litros_Dispensados;
        }
        
        
        
    }
    public void clearData(){
        fecha=null;
        codigoEmpresa=0;
        codigoConductor=null;
        Ramal="7";
        Unidad=null;
        Litros_Dispensados=0;
        kilometraje=0;
        ingresos=0;
        cantidad_pasajeros=0;
        cantidad_cedulas=0;
        cantidad_carreras=0;
        horas_rol=0;
        horas_barras=0;
        eficiencia_horas=0;
        marcas_efectivas=0;
        marcastotales_carrera=0;
        marcasefectivas_carrera=0;
        marcas_km=0;
        porcentaje_adultomayor=0;
        litros_carrera=0;
        litros_pasajero=0;
        litros_km=0;
        km_litros=0;
        dinero_carrera=0;
        dinero_km=0;
        plantel=0;
        consecutivo=0;
    }
    public boolean saveDataMySql(){
        calculoIndicadores();
        String s="insert into Ingreso_Datos (CodigoEmpresa, Fecha, CodConductor, Ramal, Unidad, Litros_Dispensados, Kilometros_Recorridos, Ingresos_Dia, Total_Pasajeros, Total_Cedulas, Cant_Medias_Carreras, Horas_Laboradas_Barras, Horas_Laboradas_Rol, Eficiencia_Horas_Laboradas, Marcas_Totales, Marcas_Efectivas, Marcas_Totales_Carreras, Marcas_Efectivas_Carreras, Marcas_Kilometro, Porcentaje_AdultoMayor, Litros_Carrera, Litros_Pasajero, Litros_Km, Km_Litros, Dinero_Carrera, Dinero_Km, Plantel, IdMaria, User) values ('"+codigoEmpresa+"','"+fecha+"','"+codigoConductor+"','"+Ramal+"','"+Unidad+"','"+Litros_Dispensados+"','"+kilometraje+"','"+ingresos+"','"+cantidad_pasajeros+"','"+cantidad_cedulas+"','"+cantidad_carreras+"','"+horas_barras+"','"+horas_rol+"','"+eficiencia_horas+"','"+cantidad_pasajeros+"','"+marcas_efectivas+"','"+marcastotales_carrera+"','"+marcasefectivas_carrera+"','"+marcas_km+"','"+porcentaje_adultomayor+"','"+litros_carrera+"','"+litros_pasajero+"','"+litros_km+"','"+km_litros+"','"+dinero_carrera+"','"+dinero_km+"','"+plantel+"','"+idMaria+"','"+DataUser.getusername()+"')";
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
        JOptionPane.showMessageDialog(null, a);
        return false;}
    }
    public boolean updateDataMySql(){
        calculoIndicadores();
        String s="update Ingreso_Datos set Fecha='"+fecha+"', Unidad='"+Unidad+"', Litros_Dispensados='"+Litros_Dispensados+"', Kilometros_Recorridos='"+kilometraje+"', Plantel='"+plantel+"' where Consecutivo='"+consecutivo+"'";
        String a=modulo.Ejecutar(s);
        if (a.contains("concluyó")){
        
        JOptionPane.showMessageDialog(null, a);
        return true;
        }
        else{
        JOptionPane.showMessageDialog(null, a);
        return false;}
    }
    
    
    
}
