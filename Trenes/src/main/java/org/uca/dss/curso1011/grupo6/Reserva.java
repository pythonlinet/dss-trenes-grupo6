/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import org.joda.time.LocalDate;

/**
 * Clase que tiene la estructura para crear la reserva de un trayecto
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */
public class Reserva {

    private String codigoReserva;
    private Trayecto trayecto;
    private LocalDate fecha;
    
    public Reserva(LocalDate fecha, String CodigoReserva){
        this.fecha = fecha;
        this.codigoReserva = CodigoReserva;
    }

    /**
     * @return the CodigoReserva
     */
    public String getCodigoReserva() {
        return codigoReserva;
    }

    /**
     *
     * @return
     */
    public LocalDate getFecha() {
        return fecha;
    }

    void setTrayecto(Trayecto trayecto) {
        this.trayecto = trayecto;
    }

    Object getTrayecto() {
        return trayecto;
    }    
}
