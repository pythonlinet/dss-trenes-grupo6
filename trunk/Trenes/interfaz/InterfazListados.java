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

package org.uca.dss.agenciaviajes.interfaz;

import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Permite consultar los trayectos correspondientes
 * Es necesario para hacer los tests automáticos
 *
 * @author dmolina
 */
public interface InterfazListados {
    /**
     * Permite obtener la hora de inicio del viaje. No se mostrarán las
     * horas para las cuales ya no hay asientos disponibles
     * 
     * @param origen ciudad origen
     * @param destino ciudad destino
     * @param fecha fecha del viaje
     * @return un array con las horas de partida disponibles
     */
    public List<LocalTime> getHorarios(String origen, String destino, LocalDate fecha);
}
