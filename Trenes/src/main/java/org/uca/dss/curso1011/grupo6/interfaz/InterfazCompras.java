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

package org.uca.dss.trenes.interfaz;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Este interface permite realizar y cancelar las reservas
 *
 * @author dmolina
 */
public interface InterfazCompras {
    /**
     * Permite realizar una reserva del viaje
     *
     * En el caso de que alguno de los parámetros no sea correcto, debe de devolver
     * una excepción IllegalArgumentException
     *
     * @param origen ciudad de origen
     * @param destino ciudad de destino
     * @param fecha fecha del viaje
     * @param hora hora del viaje
     * @return cadena que identifica a la reserva. Puede ser cualquier cadena
     *
     */
    public String reservaAsiento(String origen, String destino, LocalDate fecha, LocalTime hora);
    /**
     * Permite comprobar el número de asientos libres
     *
     * En el caso de que alguno de los parámetros no sea correcto, debe de devolver
     * una excepción IllegalArgumentException
     *
     * @param origen ciudad de origen
     * @param destino ciudad de destino
     * @param fecha fecha del viaje
     * @param hora hora del viaje
     * @return número de asientos libres (no reservados)
     *
     */
    public int asientosLibres(String origen, String destino, LocalDate fecha, LocalTime hora);
    /**
     * Cancela una reserva, dejando el asiento indicado libre
     *
     * @param reserva cadena que identifica la reserva
     */
    public void cancelaReserva(String codigoReserva);

    /**
     * Devuelve el precio de la reserva del viaje
     *
     * @param origen ciudad de origen
     * @param destino ciudad de destino
     * @param fecha fecha del viaje
     * @param hora hora del viaje
     *
     * @return precio de una reserva del viaje
     */
    public double getPrecio(String origen, String destino, LocalDate fecha, LocalTime hora);
}
