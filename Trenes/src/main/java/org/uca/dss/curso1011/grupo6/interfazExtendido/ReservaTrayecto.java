/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.trenes.interfazExtendido;

import org.joda.time.LocalDate;

/**
 *
 * @author dmolina
 */
public class ReservaTrayecto {
    private int numeroAsiento;
    private String codigoReserva;
    private LocalDate fechaSalida;
    private InformacionTrayecto trayecto;

/**
 * Constructor de la información de una reserva
 * @param recorrido a reservar
 * @param fechaSalida fecha de la reserva
 * @param numeroAsiento asiento reservado
 * @param codigoReserva código de reserva
 */
    public ReservaTrayecto(InformacionTrayecto recorrido,
            LocalDate fechaSalida,
            int numeroAsiento, String codigoReserva) {
        this.trayecto = recorrido;
        this.fechaSalida = fechaSalida;
        this.numeroAsiento = numeroAsiento;
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
        return this.trayecto.getPrecio();
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

