/*
 *  Copyright (C) 2010 dmolina
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.uca.dss.trenes.interfazExtendido;

import java.util.List;
import org.joda.time.LocalDate;

/**
 * Este interface permite realizar y cancelar las reservas
 *
 * @author dmolina
 */
public interface InterfazCompras {
    /**
     * Permite realizar una reserva de un viaje, a partir de su itinerario
     *
     * En el caso de que el itinerario, debe de devolver
     * una excepción IllegalArgumentException
     *
     * @param fecha de la reserva
     * @param itinerario deseado
     * @return Lista de las reservas, permite cancelar parcialmente cada reserva.
     *
     */
    public List<ReservaTrayecto> reservaAsiento(Itinerario itinerario, LocalDate fecha);
    /**
     * Permite comprobar el número de asientos libres de todo el trayecto,
     * considerando los distintos viajes del itinerario.
     *
     * En el caso de que alguno de los parámetros no sea correcto, debe de devolver
     * una excepción IllegalArgumentException
     *
     * @param fecha de la reserva
     * @param itinerario deseado
     * @return número de asientos libres
     *
     */
    public int asientosLibres(LocalDate fecha, Itinerario itinerario);

    /**
     * Cancela una reserva, dejando el asiento indicado libre
     *
     * @param reserva a cancelar
     */
    public void cancelaReserva(ReservaTrayecto reserva);
    /**
     * Cancela la reserva de un itinerario
     *
     * @param reservas lista de reservas a cancelar
     */
    public void cancelaReserva(List<ReservaTrayecto> reservas);
}

