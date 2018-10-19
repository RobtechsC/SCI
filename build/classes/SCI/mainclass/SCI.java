/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SCI.mainclass;

import CLASES.DataUser;
import PANTALLAS.Login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban Robles
 */
public class SCI {
    public String currentversion="2.0";
    
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
