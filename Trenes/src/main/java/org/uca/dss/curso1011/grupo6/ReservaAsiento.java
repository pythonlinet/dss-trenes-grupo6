/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;

/**
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */
public interface ReservaAsiento {

    public int generarAsiento(final InformacionTrayecto infoTrayecto);

    public int asignarAsiento(final InformacionTrayecto infoTrayecto, int asiento);
}
