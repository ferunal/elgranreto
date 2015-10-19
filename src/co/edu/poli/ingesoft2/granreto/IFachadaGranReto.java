/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.poli.ingesoft2.granreto;

/**
 *
 * @author fercris
 */
public interface IFachadaGranReto {
    
    
    public void cargarArchivo( String rutaArchivo ) throws GranRetoException;
    
    public String calcular();
}
