/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;

/**
 * Clase para implementar el interfaz itinerario
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */
public class ItinerarioImplementacionInterfaz extends ArrayList<InformacionTrayecto> implements Itinerario{

    private List<InformacionTrayecto> itrayectos;

    
     /**Constructor de la clase ItinerarioImplementacionInterfaz
     * @return precio
     */
    
    public ItinerarioImplementacionInterfaz(List<InformacionTrayecto> itinerario)
    {
        super(itinerario);
    }
    
    /**Metodo que devuelve el precio de un itinerario mediante la suma
     * de los precios de los trayectos que componen el itinerario
     * @return precio
     */
    public double getPrecio() {
        double precio = 0;

        Iterator iter = itrayectos.iterator();

         while (iter.hasNext()){
             InformacionTrayecto itrayecto = (InformacionTrayecto)iter.next();
             precio += itrayecto.getPrecio();
         }

        return precio;
    }

}
