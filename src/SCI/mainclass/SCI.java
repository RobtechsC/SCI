/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SCI.mainclass;

import CLASES.DataUser;
import PANTALLAS.Login;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Esteban Robles
 */
public class SCI {
    public String currentversion="1.0";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String user=System.getProperty("user.name").trim();
        DataUser d=new DataUser();
        d.setusername(user);
        Logger.getLogger(SCI.class.getName()).log(Level.SEVERE, user);
   //     Principal prin = new Principal();
       // TODO code application logic here
       if (user.contains("CIESA")){
           Logger.getLogger(SCI.class.getName()).log(Level.SEVERE, "Cie");
          
            
            
       }
       else {
                 
           
           new Login().setVisible(true);       
            }
            
             
       
       
    }
}
