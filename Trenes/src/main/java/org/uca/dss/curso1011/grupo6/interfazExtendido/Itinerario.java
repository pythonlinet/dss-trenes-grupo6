/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.trenes.interfazExtendido;

import java.util.List;

/**
 * Interfaz de Itinerario, se define por comodidad para el resto.
 * @author dmolina
 */
public interface Itinerario extends List<InformacionTrayecto> {
    public double getPrecio();
}

