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

    private Listado listado;

    /**
     * 
     */
    public Transbordo ()
    {

    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return 
     */
    private List<Itinerario> buscarInformacionTrayectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList();

        Iterator<Trayecto> iTrayectos = getListado().getViajes().getTrayectos().iterator();

        Trayecto trayecto;
        
        while(iTrayectos.hasNext())
        {
             trayecto = iTrayectos.next();

             if(trayecto.getCiudadOrigen().equals(origen) &&
                   !trayecto.getCiudadDestino().equals(destino) &&
                   getListado().getCompras().asientosLibres(origen, destino, fechaSalida,trayecto.getHorario().getSalida()) > 0)
            {
                    //reservasValidas = listado.getCompras().obtenerReservas(trayecto,fechaSalida);

              // if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
                                
                    String origen1 = trayecto.getCiudadOrigen();
                    String destino1 = trayecto.getCiudadDestino();
                    LocalTime salida1 = trayecto.getHorario().getSalida();
                    LocalTime llegada1 =  trayecto.getHorario().getLlegada();                    
                    double precio1 = trayecto.getTren().getPrecio();

                    Iterator<Trayecto> jTrayectos = getListado().getViajes().getTrayectos().iterator();
                    while(jTrayectos.hasNext())
                    {
                        Trayecto trayectoj = jTrayectos.next();

                        if(trayectoj.getCiudadOrigen().equals(destino1) &&
                            !trayectoj.getCiudadDestino().equals(destino) &&
                            trayectoj.getHorario().getSalida().compareTo(llegada1.plusMinutes(10)) >= 0 &&
                            getListado().getCompras().asientosLibres(origen, destino, fechaSalida,trayecto.getHorario().getSalida()) > 0)
                        {
                            //reservasValidas = viajes.obtenerReservas(trayectoj,fechaSalida);                                                        
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
        return itinerarios;
    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return 
     */
    private List<Itinerario> buscarTrayectosDirectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList();

        Iterator<Trayecto> iTrayectos = getListado().getViajes().getTrayectos().iterator();

        Trayecto trayecto;
        while (iTrayectos.hasNext())
         {
           trayecto = iTrayectos.next();
           if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino) &&
                   getListado().getCompras().asientosLibres(origen, destino, fechaSalida,trayecto.getHorario().getSalida()) > 0)
            {
                                     
                   InformacionTrayecto itrayecto = new  InformacionTrayecto(origen,destino,trayecto.getHorario().getSalida(),trayecto.getHorario().getLlegada(),trayecto.getTren().getPrecio());
                   Itinerario itinerario = new ItinerarioImplementacionInterfaz();
                   itinerario.add(itrayecto);
                   itinerarios.add(itinerario);
            }
        }
        return itinerarios;
    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @param horaSalida
     * @param horaLlegada
     * @return 
     */
    public List<Itinerario> getItinerariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {

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

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return 
     */
    public List<Itinerario> getItinerarios(String origen, String destino, LocalDate fechaSalida) {

        /***/
        System.out.println("trayectosDirectos: ");
/***/
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
     * @return the listado
     */
    public Listado getListado() {
        return listado;
    }

    /**
     * @param listado the listado to set
     */
    public void setListado(Listado listado) {
        this.listado = listado;
    }
    
}
