/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import java.util.List;
import org.joda.time.LocalDate;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazCompras;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import org.uca.dss.curso1011.grupo6.interfazExtendido.ReservaTrayecto;

/**
 *
 * @author Jose Luis
 */
public class ComprasTransbordo implements InterfazCompras{

    public List<ReservaTrayecto> reservaAsiento(Itinerario itinerario, LocalDate fecha) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int asientosLibres(LocalDate fecha, Itinerario itinerario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelaReserva(ReservaTrayecto reserva) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelaReserva(List<ReservaTrayecto> reservas) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
