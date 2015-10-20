/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.poli.ingesoft2.granreto;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fercris
 */
public class FachadaGranReto implements IFachadaGranReto {

    private List<String> lstFilasArchivo;
    Path pathRutaArchivoValida;
    private boolean expresionValida;

    public FachadaGranReto() {
        lstFilasArchivo = new ArrayList<>();
        expresionValida = false;
    }

    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public boolean esNumero(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        int i = 0;
        if (string.charAt(0) == '-') {
            if (string.length() > 1) {
                i++;
            } else {
                return false;
            }
        }
        for (; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public int cantidadOcurrenciasCaracter(String palabra, char caracter) {
        int count = 0;
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == caracter) {
                count++;
            }
        }
        return count;
    }

    public boolean esNumeroConComas(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        int i = 0;
        if (string.charAt(0) == '-') {
            if (string.length() > 1) {
                i++;
            } else {
                return false;
            }
        }
        //Validar si está en notacion cientifica

        //Validar si tiene decimales
        if (cantidadOcurrenciasCaracter(string, ',') > 1) {
            return false;
        }
        String numero = string.replace(",", "");
        for (; i < numero.length(); i++) {
            if (!Character.isDigit(numero.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean esOperacion(String str) {
        if (str.length() != 1) {
            return false;
        }
        return str.contains("+") || str.contains("-") || str.contains("*") || str.contains("/") || str.contains("^");
    }

    public boolean esNotacionCientifica(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        int i = 0;
        if (string.charAt(0) == '-') {
            if (string.length() > 1) {
                i++;
            } else {
                return false;
            }
        }

        if ((cantidadOcurrenciasCaracter(string, ',') == 1 && cantidadOcurrenciasCaracter(string, 'E') == 1)
                && (cantidadOcurrenciasCaracter(string, '+') == 1) || cantidadOcurrenciasCaracter(string, '-') == 1) {
            return esNumero(string.replace("-", "").replace("+", "").replace("E", "").replace(",", ""));
        } else {
            return false;
        }
    }
//</editor-fold>

    public BigDecimal operacion(BigDecimal opr1, BigDecimal opr2, String operacion) {
        BigDecimal resultado;
        switch (operacion) {
            case "+":
                resultado = opr1.add(opr2);
                break;
            case "-":
                resultado = opr1.subtract(opr2);
                break;
            case "*":
                resultado = opr1.multiply(opr2);
                break;
            case "/":
                if (opr2.equals(BigDecimal.ZERO)) {
                    return null;
                } else {
                    resultado = opr1.divide(opr2);
                }
                break;
            case "^":
                resultado = opr1.pow(opr2.intValue());
                break;
            default:
                return null;

        }
        return resultado;
    }

    private boolean validarNumerosExpresion(Integer posicion) {

        if (esNotacionCientifica(lstFilasArchivo.get(posicion))) {
            lstFilasArchivo.set(posicion, DecimalFormat.getInstance().format(Double.parseDouble(lstFilasArchivo.get(posicion).replace(",", "."))));
            return true;
        } else if (esNumeroConComas(lstFilasArchivo.get(posicion))) {
            lstFilasArchivo.set(posicion, DecimalFormat.getInstance().format(Double.parseDouble(lstFilasArchivo.get(posicion).replace(",", "."))));
            return true;
        } else {
            return false;
        }

    }

    private boolean validarPosicionesOperador(Integer posicion) {

        return esOperacion(lstFilasArchivo.get(posicion));

    }

    private boolean validarContenido() {
        int numeros = 0;
        int operadores = 0;
        if (expresionValida) {
            if (!validarNumerosExpresion(0)) {
                numeros++;
            }

            if (!validarNumerosExpresion(1)) {
                numeros++;
            }
            if (!validarNumerosExpresion(3)) {
                numeros++;
            }
            if (!validarNumerosExpresion(4)) {
                numeros++;
            }
            if (!validarNumerosExpresion(7)) {
                numeros++;
            }
            if (!validarPosicionesOperador(2)) {
                operadores++;
            }
            if (!validarPosicionesOperador(5)) {
                operadores++;
            }
            if (!validarPosicionesOperador(6)) {
                operadores++;
            }
            if (!validarPosicionesOperador(8)) {
                operadores++;
            }
            return (numeros == 0) && (operadores == 0);
        } else {
            return false;
        }
    }

    private boolean esArchivoValidoLectura(Path rutaArchivo) {

        return Files.exists(rutaArchivo, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(rutaArchivo)
                && !Files.isDirectory(rutaArchivo, LinkOption.NOFOLLOW_LINKS) && Files.isRegularFile(rutaArchivo, LinkOption.NOFOLLOW_LINKS);
    }

    private void cargarFilasArchivo() {
        try {
            lstFilasArchivo.clear();
            List<String> lineasArchivo = Files.readAllLines(pathRutaArchivoValida);
            //Eliminar lineas vacías, espacios y tabuladores
            for (String lineaArchivo : lineasArchivo) {
                if (!lineaArchivo.trim().isEmpty()) {
                    lstFilasArchivo.add(lineaArchivo.replace(" ", "").replace("\t", ""));
                }
            }

            expresionValida = lstFilasArchivo.size() == 9;
//
//            for (String filaArchivoLeida : lstFilasArchivo) {
//                System.out.println(filaArchivoLeida);
//            }

        } catch (IOException ex) {
            expresionValida = false;
            Logger.getLogger(FachadaGranReto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cargarArchivo(String rutaArchivo) throws GranRetoException {

        if (esArchivoValidoLectura(Paths.get(rutaArchivo))) {
            pathRutaArchivoValida = Paths.get(rutaArchivo);
            cargarFilasArchivo();

        } else {
            expresionValida = false;
            throw new GranRetoException("Error de entrada-salida", new Throwable("El archivo no existe, no es legible, es una carpeta o no es un archivo regular."));
        }

    }

    @Override
    public String calcular() {
        if (validarContenido()) {

            String numero1 = lstFilasArchivo.get(0);
            String numero2 = lstFilasArchivo.get(1);
            String operacion1 = lstFilasArchivo.get(2);
            String numero3 = lstFilasArchivo.get(3);
            String numero4 = lstFilasArchivo.get(4);
            String operacion2 = lstFilasArchivo.get(5);
            String operacion3 = lstFilasArchivo.get(6);
            String numero5 = lstFilasArchivo.get(7);
            String operacion4 = lstFilasArchivo.get(8);

            BigDecimal resultado1 = operacion(new BigDecimal(numero1), new BigDecimal(numero2), operacion1);
            BigDecimal resultado2 = operacion(new BigDecimal(numero3), new BigDecimal(numero4), operacion2);
            BigDecimal resultado3 = operacion(resultado1, resultado2, operacion3);
            BigDecimal resultado4 = operacion(resultado3, new BigDecimal(numero5), operacion4);
            String strResultado = NumberFormat.getIntegerInstance().format(resultado4.doubleValue());
            StringBuilder strBResultado = new StringBuilder(strResultado);
            if (strResultado.length() > 8) {
                strBResultado.replace(strBResultado.length() - 8, strBResultado.length() - 7, "'");
            }
            if (strResultado.length() > 16) {
                strBResultado.replace(strBResultado.length() - 16, strBResultado.length() - 15, "'");
            }
            return strBResultado.toString();

        } else {
            return "ERROR";
        }
    }

}
