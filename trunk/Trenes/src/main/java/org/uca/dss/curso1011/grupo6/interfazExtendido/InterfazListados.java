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

package org.uca.dss.curso1011.grupo6.interfazExtendido;

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
     * Permite obtener la disponibilidad de un viaje, mostrando para dicho
     * origen y destino el viaje (considerando la posibilidad de realizar
     * un trayecto). 
     * No se mostrarán las combinaciones horas para las cuales no hay asientos
     * disponibles en alguno de los trayectos.
     * Mostrará todos los viajes que cumplan los requisitos horarios indicados.
     * 
     * @param origen ciudad origen
     * @param destino ciudad destino
     * @param fecha fecha de inicio viaje
     * @param horaSalida indica la hora a partir del cual el viaje puede empezar
     * @param horaLlegada indica la hora máxima de llegada
     * @return un array con los itinerarios posibles
     */
    public List<Itinerario> getItinerariosEntre(String origen, String destino,
            LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada);
    /**
     * Permite obtener la disponibilidad de un viaje, mostrando para dicho
     * origen y destino el viaje (considerando la posibilidad de realizar
     * un trayecto).
     * No se mostrarán las combinaciones horas para las cuales no hay asientos
     * disponibles en alguno de los trayectos.
     * Es equivalente al método getHorariosEntre pero sin imporer requisitos
     * horarios.
     *
     * @param origen ciudad origen
     * @param destino ciudad destino
     * @param fecha fecha de inicio viaje
     * @param horaSalida indica la hora a partir del cual el viaje puede empezar
     * @param horaLlegada indica la hora máxima de llegada
     * @return un array con los itinerarios posibles
     */
    public List<Itinerario> getItinerarios(String origen, String destino,
            LocalDate fechaSalida);
}

