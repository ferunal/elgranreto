/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elgranretojuancarlosrico;

import co.edu.poli.ingesoft2.granreto.FachadaGranReto;
import co.edu.poli.ingesoft2.granreto.GranRetoException;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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
//            System.out.println(fachadaGranReto.esNotacionCientifica("253,2-E25"));
//            System.out.println(fachadaGranReto.operacion(new BigDecimal("15.2"), new BigDecimal(2), "-"));
//          //  System.out.println( String.format("%.d", 132546465));
//            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//            dfs.setDecimalSeparator(',');
//            dfs.setPerMill('.');
           
            String ruta = System.getProperty("user.home") + File.separator + "prueba.txt";
            fachadaGranReto.cargarArchivo( ruta);
            System.out.println(fachadaGranReto.calcular());
        } catch (GranRetoException ex) {
            Logger.getLogger(ElGranRetoJuanCarlosRico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
