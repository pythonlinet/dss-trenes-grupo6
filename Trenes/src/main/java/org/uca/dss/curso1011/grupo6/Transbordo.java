/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazListados;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;

/**
 *
 * @author Jose Luis
 */
public class Transbordo implements InterfazListados {

    private List<Trayecto> trayectos;

    public Transbordo ()
    {

    }

    public List<Itinerario> getHorariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Itinerario> getHorarios(String origen, String destino, LocalDate fechaSalida) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the trayectos
     */
    public List<Trayecto> getTrayectos() {
        return trayectos;
    }

    /**
     * @param trayectos the trayectos to set
     */
    public void setTrayectos(List<Trayecto> trayectos) {
        this.trayectos = trayectos;
    }

}
