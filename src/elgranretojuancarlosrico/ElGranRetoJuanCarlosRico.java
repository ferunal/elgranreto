/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elgranretojuancarlosrico;

import co.edu.poli.ingesoft2.granreto.FachadaGranReto;
import co.edu.poli.ingesoft2.granreto.GranRetoException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fercris
 */
public class ElGranRetoJuanCarlosRico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        try {
            FachadaGranReto fachadaGranReto = new FachadaGranReto();
            System.out.println(fachadaGranReto.esNumeroConComas("253,2"));
            
            String ruta = System.getProperty("user.home") + File.separator + "archivo1.txt";
            fachadaGranReto.cargarArchivo( ruta);
        } catch (GranRetoException ex) {
            Logger.getLogger(ElGranRetoJuanCarlosRico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
