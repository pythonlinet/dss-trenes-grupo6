/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazListados;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import org.uca.dss.trenes.basededatos.DBUtils;

/**
 *
 * @author Jose Luis
 */
public class Transbordo implements InterfazListados {

    private List<Trayecto> trayectos;

    public Transbordo ()
    {

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

    public List<Itinerario> getHorariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {

        List<Itinerario> itinerariosDisponibles = new ArrayList();

        Iterator i = trayectos.iterator();

        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        //ComprobarExcepcion(fecha,origen,destino); Importante!!!

        ObjectContainer db = DBUtils.getDb();

        List <Reserva> reservas = db.query(new Predicate <Reserva>() {
            public boolean match ( Reserva reserva) {
                return true;
            }
        }) ;
         while (i.hasNext())
         {
           Trayecto trayecto = (Trayecto)i.next();
           if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino))
           {
               //reservasValidas = ObtenerReservas(trayecto,fecha);

               if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
               {

                   LocalTime hora = trayecto.getHorario().getSalida();
                   //horariosDisponibles.add(hora);
               }
           }
        }

        return itinerariosDisponibles;
    }

    public List<Itinerario> getHorarios(String origen, String destino, LocalDate fechaSalida) {

        List<Itinerario> itinerarios = null;
        return itinerarios;
    }

   
}
