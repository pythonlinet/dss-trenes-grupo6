/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalTime;

/**
 * Clase encargada de realizar los listados de los viajes
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */
public class Viajes{
    private List<Trayecto> trayectos;
    
    /**
     * Constructor de la clase Viajes con el par�metro trayectos
     * @param trayectos
     */
   
        public Viajes(List<Trayecto> trayectos)
        {
        this.trayectos = trayectos;
        trayectos = new ArrayList();
         }
        
  /**
    * Metodo privado que comprueba si dos trayectos son iguales
    * @param trayecto1
    * @param trayecto2
    * @return verdad o falso dependiendo de la comprobacion
    */  
    private boolean comprobarTrayecto(Trayecto trayecto, String origen, String destino, LocalTime hora)
    {
     if(trayecto.getCiudadOrigen().equals(origen) &&
                    trayecto.getCiudadDestino().equals(destino) &&
                    trayecto.getHorario().getSalida().equals(hora))
        {
              return true;
        }
        else
        {    
              return false;
        }
    }  

    /**Metodo que obtiene los trayectos
     * @return lista trayectos
     */
    public List<Trayecto> getTrayectos() {
        return trayectos;
    }

 /** Metodo que busca los trayectos entre ciudad origen y ciuadad dstino
 * a la hora indicada por el parametro de entrada
 * @param origen
 * @param destino
 * @param hora
 * @return trayecto que coincida con los param�tros
 */
    public Trayecto buscarTrayecto (String origen, String destino, LocalTime hora)
    {
        Iterator<Trayecto> iTrayectos = trayectos.iterator();
        Trayecto trayecto,trayectoEncontrado=null ;
        boolean encontrado = false;

        while (iTrayectos.hasNext() && encontrado != true)
        {
            trayecto = iTrayectos.next();
            if(comprobarTrayecto(trayecto,origen,destino,hora))
                    {
                        encontrado = true;
                        trayectoEncontrado = trayecto;
                    }
        }

        if(!encontrado)
        {
            throw new RuntimeException("No existe el trayecto");
        }
            
        return trayectoEncontrado;
    }
}
