/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASES;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Erobles
 */
public class ModuloMySQSOS {
    //String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String driver="com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://108.61.150.108:3306/SOS";
    /*String user = "sa";
    String pwd = "Ciltcr9769";*/
    static String user="";
    static String pwd="";
    public static Calendar calendar;
    static Timestamp fechasentence;
    static PreparedStatement da;
    static String sentence;
    static Statement s2;
    ResultSet tbl;
    static String GET_LOGO = "Select * from Archivos where TipoArchivo=1 order by Consecutivo Desc LIMIT 1";
    static Connection cn; 
     
    public ResultSet Listar (String Cad){
        
        try{
            if (cn==null){
                Class.forName(driver).newInstance();
                cn = DriverManager.getConnection(url,user,pwd);
            }
            if (cn.isClosed()){
            Class.forName(driver).newInstance();
            cn = DriverManager.getConnection(url,user,pwd);
            }
           /* if (ValoresEstaticos.idempresa>0){
            calendar=Calendar.getInstance();
            fechasentence=new Timestamp(calendar.getTimeInMillis());
            
            sentence="insert into ControlComandos(Fecha, Comando_Realizado, PC, IdEmpresa, User) values ('"+fechasentence+"',\""+Cad+"\",'"+ValoresEstaticos.idPC+"','"+ValoresEstaticos.idempresa+"','"+DataUser.username+"')";
            Logger.getLogger(ModuloMySQL.class.getName()).log(Level.SEVERE, sentence);
            da=cn.prepareStatement(sentence);
            da.executeUpdate();}*/
            s2 = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
           
            tbl = s2.executeQuery(Cad);
            
            
            
            return tbl;
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
            JOptionPane.showMessageDialog(null, "SCR. Error al listar, no tiene internet o permisos para hacerlo "+e.getLocalizedMessage());
            
            
            return  null;
        }//FIN DE CATCH
        
    }// FIN METODO LISTAR
    public boolean getLogoImage(){
        try {
            boolean flag = false;
            Connection connection = getConexion();
            PreparedStatement statement = connection.prepareStatement(GET_LOGO);
            ResultSet rs=statement.executeQuery();
            if (rs.first()){
                Blob b=rs.getBlob("Data");
                byte barr[]=new byte[(int)b.length()];
                barr=b.getBytes(1,(int)b.length());
                FileOutputStream fout=new FileOutputStream("c:/SCI/SCI.png");
                fout.write(barr);
                fout.close();
            }
            
            statement.close();
            connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModuloMySQSOS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModuloMySQSOS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        } catch (IOException ex) {
             Logger.getLogger(ModuloMySQSOS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
           
        }
    }
    public Timestamp getTimeServer(){
        try {
            ResultSet r=Listar("Select now() as tiemposerver");
            r.first();
            return r.getTimestamp("tiemposerver");
        } catch (SQLException ex) {
            return new Timestamp(Calendar.getInstance().getTimeInMillis());
        }
    }
    public String Ejecutar (String Cad) {
        try {
            if (cn.isClosed()==false){
                if (ValoresEstaticos.idempresa>0){
                calendar=Calendar.getInstance();
                fechasentence=new Timestamp(calendar.getTimeInMillis());
                sentence="insert into ControlComandos(Fecha, Comando_Realizado, PC, IdEmpresa, User) values ('"+fechasentence+"',\""+Cad+"\",'"+ValoresEstaticos.idPC+"','"+ValoresEstaticos.idempresa+"','"+DataUser.username+"')";
                da=cn.prepareStatement(sentence);
                da.executeUpdate();}
                da = cn.prepareStatement(Cad);
                int r = da.executeUpdate();
            
                return "Se concluyó la operacion en " + r + " fila(s)";
            }
            else
            {
                try {
                    Class.forName(driver).newInstance();
                     cn = DriverManager.getConnection(url,user,pwd);
                     if (ValoresEstaticos.idempresa>0){
                     calendar=Calendar.getInstance();
                    fechasentence=new Timestamp(calendar.getTimeInMillis());
                    sentence="insert into ControlComandos(Fecha, Comando_Realizado, PC, IdEmpresa, User) values ('"+fechasentence+"',\""+Cad+"\",'"+ValoresEstaticos.idPC+"','"+ValoresEstaticos.idempresa+"','"+DataUser.username+"')";
                    da=cn.prepareStatement(sentence);
                    da.executeUpdate();}
                     da = cn.prepareStatement(Cad);
                    int r = da.executeUpdate();            
                    return "Se concluyó la operacion en " + r + " fila(s)";
                    
                    
                }
                catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
                    //javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                    return "Error: " +e.getMessage();
            }
        }
        }
        catch (SQLException ex) {
                return "Error: " +ex.getMessage();
        }
      
        
    
    }//FIN METODO EJECUTAR
    public Connection getConexion(){
    try{
            Class.forName(driver).newInstance();
            cn = DriverManager.getConnection(url,user,pwd);
            return cn;
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
            return null;
        }// FIN DE CATCH
    }
    public boolean setUser(){
        
        ModuloMySQSOS.user=DataUser.getusername();
        ModuloMySQSOS.pwd=DataUser.getPassword();
        return testLogin();
    }
    public boolean testLogin(){
        try{
            Class.forName(driver).newInstance();
            cn = DriverManager.getConnection(url,user,pwd);
            cn.close();
            return true;
        }catch(Exception e){
            return false;
        }// FIN DE CATCH
    }
    public boolean closeConnection(){
      
            try {
                
                cn.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(ModuloMySQSOS.class.getName()).log(Level.SEVERE, null, ex);
            }
        return true;
    }
}// FIN DE LA CLASE
