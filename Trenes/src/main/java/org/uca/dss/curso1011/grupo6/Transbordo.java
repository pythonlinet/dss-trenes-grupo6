/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazListados;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;

/**
 * Clase que se encarga de realizar los listados de los transbordos disponibles
 * @author Jose Luis
 */
public class Transbordo implements InterfazListados {

    private Viajes viajes;

    public Transbordo ()
    {

    }

    public List<Itinerario> buscarInformacionTrayectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList();

        Iterator<Trayecto> iTrayectos = viajes.getTrayectos().iterator();
        
        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        Trayecto trayecto;
        
        while(iTrayectos.hasNext())
        {
             trayecto = iTrayectos.next();

             if(trayecto.getCiudadOrigen().equals(origen) &&
                   !trayecto.getCiudadDestino().equals(destino))
            {
                    reservasValidas = viajes.obtenerReservas(trayecto,fechaSalida);

               if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
               {
                    String origen1 = trayecto.getCiudadOrigen();
                    String destino1 = trayecto.getCiudadDestino();
                    LocalTime salida1 = trayecto.getHorario().getSalida();
                    LocalTime llegada1 =  trayecto.getHorario().getLlegada();                    
                    double precio1 = trayecto.getTren().getPrecio();

                    Iterator<Trayecto> jTrayectos = viajes.getTrayectos().iterator();
                    while(jTrayectos.hasNext())
                    {
                        Trayecto trayectoj = jTrayectos.next();

                        if(trayectoj.getCiudadOrigen().equals(destino1) &&
                            !trayectoj.getCiudadDestino().equals(destino) &&
                            trayectoj.getHorario().getSalida().compareTo(llegada1.plusMinutes(10)) >= 0)
                        {
                            reservasValidas = viajes.obtenerReservas(trayectoj,fechaSalida);

                            if(trayectoj.getTren().getPlazas() - reservasValidas.size() > 0)
                            {
                                InformacionTrayecto itrayecto1 = new InformacionTrayecto(origen1,destino1,salida1,llegada1,precio1);
                                InformacionTrayecto itrayecto2 = new InformacionTrayecto(trayectoj.getCiudadOrigen(),trayectoj.getCiudadDestino(),trayectoj.getHorario().getSalida(),trayectoj.getHorario().getLlegada(),trayectoj.getTren().getPrecio());

                                Itinerario itinerario = new ItinerarioImplementacionInterfaz();
                                itinerario.add(itrayecto1);
                                itinerario.add(itrayecto2);
                                itinerarios.add(itinerario);
                               
                            }
                        }
                     }
                }
             }
         }
        return itinerarios;
    }

    public List<Itinerario> buscarTrayectosDirectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList();

        Iterator<Trayecto> iTrayectos = viajes.getTrayectos().iterator();

        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        Trayecto trayecto;
        while (iTrayectos.hasNext())
         {
           trayecto = iTrayectos.next();
           if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino))
           {
               reservasValidas = viajes.obtenerReservas(trayecto,fechaSalida);

               if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
               {
                   InformacionTrayecto itrayecto = new  InformacionTrayecto(origen,destino,trayecto.getHorario().getSalida(),trayecto.getHorario().getLlegada(),trayecto.getTren().getPrecio());
                   Itinerario itinerario = new ItinerarioImplementacionInterfaz();
                   itinerario.add(itrayecto);
                   itinerarios.add(itinerario);
               }
           }
        }
        return itinerarios;
    }

    public List<Itinerario> getHorariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {

        List<Itinerario> itinerariosDisponibles = new ArrayList();
        List<Itinerario> infoTrayectos = new ArrayList();
        List<Itinerario> trayectosDirectos = new ArrayList();
        List<Itinerario> infoTrayectoTransbordos = new ArrayList();
                          
        trayectosDirectos = buscarTrayectosDirectos(origen,destino,fechaSalida);
        infoTrayectoTransbordos = buscarInformacionTrayectos(origen,destino,fechaSalida);

        Iterator<Itinerario> iTraDirectos = trayectosDirectos.iterator();

         while (iTraDirectos.hasNext())
         {
             Itinerario itinerarioi = iTraDirectos.next();

             if(itinerarioi.get(1).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerarioi.get(1).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    infoTrayectos.add(itinerarioi);
             }
         }
        
        Iterator iInfoTransbordos = infoTrayectoTransbordos.iterator();

         while (iInfoTransbordos.hasNext())
         {
             Itinerario itinerario = (Itinerario)iInfoTransbordos.next();

             if(itinerario.get(1).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerario.get(2).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    infoTrayectos.add(itinerario);
             }
         }

        return itinerariosDisponibles;
    }

    public List<Itinerario> getHorarios(String origen, String destino, LocalDate fechaSalida) {

        List<Itinerario> itinerarios = null;     
        List<Itinerario> trayectosDirectos = new ArrayList();
        List<Itinerario> informacionTrayectos= new ArrayList();

        trayectosDirectos = buscarTrayectosDirectos(origen,destino,fechaSalida);
        informacionTrayectos = buscarInformacionTrayectos(origen,destino,fechaSalida);

        Iterator iter = trayectosDirectos.iterator();
        while (iter.hasNext())
        {
            Itinerario itinerario = (Itinerario)iter.next();
            informacionTrayectos.add(itinerario);
        }        

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

    public List<Itinerario> getItinerariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Itinerario> getItinerarios(String origen, String destino, LocalDate fechaSalida) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}
