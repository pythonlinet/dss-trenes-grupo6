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
     * Metodo para obtener el codigo reserva de la reserva
     * @return codigoReserva de una reserva
     */
    public String getCodigoReserva() {
        return codigoReserva;
    }

    /**
     * Metodo que se utiliza para obtener la fecha para la que se ha realizado la
     * reserva
     * @return fecha para la que se ha realizado la reserva
     */
    public LocalDate getFecha() {
        return fecha;
    }
    
    /**
     * Metodo que se utiliza para insertar el trayecto en la reserva. El parametro
     * que necesitamos es el trayecto que queremos guardar en la reserva
     * @param trayecto del que se realiza la reserva
     */
    void setTrayecto(Trayecto trayecto) {
        this.trayecto = trayecto;
    }
    
    /**
     * Metodo que se utiliza para consultar el trayecto del que se ha realizado 
     * la reserva
     * @return trayecto, trayecto del que se ha realizado la reserva
     */
    Object getTrayecto() {
        return trayecto;
    }    
}
