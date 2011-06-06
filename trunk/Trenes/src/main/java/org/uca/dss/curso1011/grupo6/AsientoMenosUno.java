/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;

/**
 *
 * @author Jose Luis
 */
public class AsientoMenosUno implements ReservaAsiento{

     /**Metodo que devuelve -1 como numero de asiento
     * @param infoTrayecto
     * @return numero asiento del trayecto
     */
    private int generarAsientoMenosUno()
    {
        return -1;
    }


    public int generarAsiento(InformacionTrayecto infoTrayecto) {
        return generarAsientoMenosUno();
    }

}
