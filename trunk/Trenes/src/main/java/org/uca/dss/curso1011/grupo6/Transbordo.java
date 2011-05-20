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
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazListados;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import org.uca.dss.trenes.basededatos.DBUtils;

/**
 *
 * @author Jose Luis
 */
public class Transbordo implements InterfazListados {

    private Viajes viajes;

    public Transbordo ()
    {

    }

    public List<Itinerario> getHorariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {

        List<Itinerario> itinerariosDisponibles = new ArrayList();
        List<InformacionTrayecto> informacionTrayectos = new ArrayList();
        
        Iterator i = viajes.getTrayectos().iterator();

        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        //ComprobarExcepcion(fecha,origen,destino); Importante!!!

        ObjectContainer db = DBUtils.getDb();

        List <Reserva> reservas = db.query(new Predicate <Reserva>() {
            public boolean match ( Reserva reserva) {
                return true;
            }
        }) ;
        
         Trayecto trayecto;
         while (i.hasNext())
         {
           trayecto = (Trayecto)i.next();
           if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino) &&
                   trayecto.getHorario().getSalida().compareTo(horaSalida) >= 0 &&
                   trayecto.getHorario().getLlegada().compareTo(horaLlegada) <= 0)
           {
               reservasValidas = viajes.ObtenerReservas(trayecto,fechaSalida);

               if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
               {
                   InformacionTrayecto itrayecto = new  InformacionTrayecto(origen,destino,horaSalida,horaLlegada,trayecto.getTren().getPrecio());
                   informacionTrayectos.add(itrayecto);
                  //itinerariosDisponibles.add();
               }
           }
        }
        trayecto = null;
        i = null;        
        i = viajes.getTrayectos().iterator();

         while(i.hasNext())
         {
             trayecto = (Trayecto)i.next();
             
             if(trayecto.getCiudadOrigen().equals(origen) &&
                   !trayecto.getCiudadDestino().equals(destino) &&
                   trayecto.getHorario().getSalida().compareTo(horaSalida) >= 0 &&
                   trayecto.getHorario().getLlegada().compareTo(horaLlegada) <= 0)
            {                   
                    reservasValidas = viajes.ObtenerReservas(trayecto,fechaSalida);

               if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
               {
                    String destino1 = trayecto.getCiudadDestino();
                    LocalTime llegada1 =  trayecto.getHorario().getLlegada();
                    double precio1 = trayecto.getTren().getPrecio();
                    
                    Iterator j = viajes.getTrayectos().iterator();
                    while(j.hasNext())
                    {
                        Trayecto trayectoj = (Trayecto)j.next();

                        if(trayectoj.getCiudadOrigen().equals(destino1) &&
                            !trayectoj.getCiudadDestino().equals(destino) &&
                            trayectoj.getHorario().getSalida().compareTo(llegada1.plusMinutes(10)) >= 0 &&
                            trayectoj.getHorario().getLlegada().compareTo(horaLlegada) <= 0)
                        {
                            reservasValidas = viajes.ObtenerReservas(trayectoj,fechaSalida);

                            if(trayectoj.getTren().getPlazas() - reservasValidas.size() > 0)
                            {
                                double precioTotal = precio1 + trayectoj.getTren().getPrecio();
                                InformacionTrayecto jtrayecto = new  InformacionTrayecto(origen,destino,llegada1,trayectoj.getHorario().getLlegada(),precioTotal);
                                informacionTrayectos.add(jtrayecto);
                            }
                        }
                     }
                }
             }
         }

        return itinerariosDisponibles;
    }

    public List<Itinerario> getHorarios(String origen, String destino, LocalDate fechaSalida) {

        List<Itinerario> itinerarios = null;
        return itinerarios;
    }

    /**
     * @return the viajes
     */
    public Viajes getViajes() {
        return viajes;
    }

    /**
     * @param viajes the viajes to set
     */
    public void setViajes(Viajes viajes) {
        this.viajes = viajes;
    }

   
}
