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
 *
 * @author Jose Luis
 */
public class Transbordo implements InterfazListados {

    private Viajes viajes;

    public Transbordo ()
    {

    }

    public List<Itinerario> BuscarInformacionTrayectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList();

        Iterator i = viajes.getTrayectos().iterator();
        
        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        Trayecto trayecto;
        
        while(i.hasNext())
        {
             trayecto = (Trayecto)i.next();

             if(trayecto.getCiudadOrigen().equals(origen) &&
                   !trayecto.getCiudadDestino().equals(destino))
                   //trayecto.getHorario().getSalida().compareTo(horaSalida) >= 0 &&
                   //trayecto.getHorario().getLlegada().compareTo(horaLlegada) <= 0)
            {
                    reservasValidas = viajes.ObtenerReservas(trayecto,fechaSalida);

               if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
               {
                    String origen1 = trayecto.getCiudadOrigen();
                    String destino1 = trayecto.getCiudadDestino();
                    LocalTime salida1 = trayecto.getHorario().getSalida();
                    LocalTime llegada1 =  trayecto.getHorario().getLlegada();                    
                    double precio1 = trayecto.getTren().getPrecio();

                    Iterator j = viajes.getTrayectos().iterator();
                    while(j.hasNext())
                    {
                        Trayecto trayectoj = (Trayecto)j.next();

                        if(trayectoj.getCiudadOrigen().equals(destino1) &&
                            !trayectoj.getCiudadDestino().equals(destino) &&
                            trayectoj.getHorario().getSalida().compareTo(llegada1.plusMinutes(10)) >= 0)
                            //trayectoj.getHorario().getLlegada().compareTo(horaLlegada) <= 0)
                        {
                            reservasValidas = viajes.ObtenerReservas(trayectoj,fechaSalida);

                            if(trayectoj.getTren().getPlazas() - reservasValidas.size() > 0)
                            {
                                //double precioTotal = precio1 + trayectoj.getTren().getPrecio();
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

    public List<Itinerario> BuscarTrayectosDirectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList();

        Iterator i = viajes.getTrayectos().iterator();

        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        Trayecto trayecto;
        while (i.hasNext())
         {
           trayecto = (Trayecto)i.next();
           if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino))
           {
               reservasValidas = viajes.ObtenerReservas(trayecto,fechaSalida);

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
        List<Itinerario> informacionTrayectos = new ArrayList();
        List<Itinerario> trayectosDirectos = new ArrayList();
        List<Itinerario> informacionTrayectoTransbordos = new ArrayList();
                          
        trayectosDirectos = BuscarTrayectosDirectos(origen,destino,fechaSalida);
        informacionTrayectoTransbordos = BuscarInformacionTrayectos(origen,destino,fechaSalida);

        Iterator i = trayectosDirectos.iterator();

         while (i.hasNext())
         {
             Itinerario itinerarioi = (Itinerario)i.next();

             if(itinerarioi.get(1).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerarioi.get(1).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    informacionTrayectos.add(itinerarioi);
             }
         }
        
        Iterator j = informacionTrayectoTransbordos.iterator();

         while (j.hasNext())
         {
             Itinerario itinerario = (Itinerario)j.next();

             if(itinerario.get(1).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerario.get(2).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    informacionTrayectos.add(itinerario);
             }
         }

        return itinerariosDisponibles;
    }

    public List<Itinerario> getHorarios(String origen, String destino, LocalDate fechaSalida) {

        List<Itinerario> itinerarios = null;     
        List<Itinerario> trayectosDirectos = new ArrayList();
        List<Itinerario> informacionTrayectos= new ArrayList();

        trayectosDirectos = BuscarTrayectosDirectos(origen,destino,fechaSalida);
        informacionTrayectos = BuscarInformacionTrayectos(origen,destino,fechaSalida);

        Iterator i = trayectosDirectos.iterator();
        while (i.hasNext())
        {
            Itinerario itinerario = (Itinerario)i.next();
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

   
}
