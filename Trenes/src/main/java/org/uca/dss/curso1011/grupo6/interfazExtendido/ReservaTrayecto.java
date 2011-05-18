/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6.interfazExtendido;

import org.joda.time.LocalDate;

/**
 *
 * @author dmolina
 */
public class ReservaTrayecto {
    private int numeroAsiento;
    private String codigoReserva;
    private double precio;
    private LocalDate fechaSalida;
    private InformacionTrayecto trayecto;

    protected ReservaTrayecto(int numeroAsiento, double precio, String codigoReserva) {
        this.numeroAsiento = numeroAsiento;
        this.precio = precio;
        this.codigoReserva = codigoReserva;
    }

    /**
     * @return the numeroAsiento
     */
    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    /**
     * @return the codigoReserva
     */
    public String getCodigoReserva() {
        return codigoReserva;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }
    /**
     * @return the fechaSalida
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @return the trayecto
     */
    public InformacionTrayecto getTrayecto() {
        return trayecto;
    }
}
