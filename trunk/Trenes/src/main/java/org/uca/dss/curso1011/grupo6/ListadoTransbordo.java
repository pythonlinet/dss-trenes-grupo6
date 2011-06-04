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
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazListados;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;

/**
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */

/** Clase que implementa los listados de los transbordos realizados
 */
public class ListadoTransbordo implements InterfazListados {

    private Transbordo transbordo;
    private ComprasTransbordo compras;

    public ListadoTransbordo()
    {
        
    }

     /**Metodo que devuelve los itinerarios comprendidos entre la
     * ciudad origen y ciudad destino, hora salida y hora llegada
     * y en la fecha indicada en el parametro de entrada
     *
     * @param origen
     * @param destino
     * @param fechaSalida
     * @param horaSalida
     * @param horaLlegada
     * @return lista de itinerarios comprendidos entre dos ciudades
     */
   public List<Itinerario> getItinerariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {

        List<Itinerario> infoTrayectos = new ArrayList();
        List<Itinerario> trayectosDirectos = getTransbordo().buscarTrayectosDirectos(origen,destino,fechaSalida);
        List<Itinerario> infoTrayectoTransbordos = getTransbordo().buscarInformacionTrayectos(origen,destino,fechaSalida);

        Iterator<Itinerario> iTraDirectos = trayectosDirectos.iterator();

         while (iTraDirectos.hasNext())
         {
             Itinerario itinerarioi = iTraDirectos.next();

             if(itinerarioi.get(0).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerarioi.get(0).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    infoTrayectos.add(itinerarioi);
             }
         }

        Iterator iInfoTransbordos = infoTrayectoTransbordos.iterator();

         while (iInfoTransbordos.hasNext())
         {
             Itinerario itinerario = (Itinerario)iInfoTransbordos.next();

             if(itinerario.get(0).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerario.get(1).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    infoTrayectos.add(itinerario);
             }
         }
        List<Itinerario> itinerariosDisponibles = infoTrayectos;

        for(Itinerario iterItinerario : infoTrayectos )
        {
            if(getCompras().asientosLibres(fechaSalida, iterItinerario)==0)
            {
                itinerariosDisponibles.remove(iterItinerario);
            }
        }

        return itinerariosDisponibles;
    }

    /**Metodo que devuelve el listado de itinerarios entre ciudad origen
     * y ciudad destino y en la fecha indicada como parametro de entrada
     *
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return lista de itinerarios
     */
    public List<Itinerario> getItinerarios(String origen, String destino, LocalDate fechaSalida) {

        List<Itinerario> itinerarios = getTransbordo().buscarTrayectosDirectos(origen,destino,fechaSalida);
        itinerarios.addAll(getTransbordo().buscarInformacionTrayectos(origen,destino,fechaSalida));
        List<Itinerario> itinerariosDisponibles = new ArrayList<Itinerario>();

        for(Itinerario iterItinerario : itinerarios )
        {
            System.out.println("ASIENTOS LIBRES ITINERARIO: "+getCompras().asientosLibres(fechaSalida, iterItinerario));
            if(getCompras().asientosLibres(fechaSalida, iterItinerario)>0)
            {   
                itinerariosDisponibles.add(iterItinerario);
                System.out.println("ENTRA REMOVE");
            }
            System.out.println("DESPUES SALE REMOVE");
            System.out.println("Disponibles SIZE"+itinerariosDisponibles.size());
        }
        System.out.println("Disponibles SIZE"+itinerariosDisponibles.size());
        return itinerariosDisponibles;
    }

    /**Metodo que obtiene los transbordos 
     * @return transbordo
     */
    public Transbordo getTransbordo() {
        return transbordo;
    }

    /**Metodo que inserta un transbordo
     * @param transbordo
     */
    public void setTransbordo(Transbordo transbordo) {
        this.transbordo = transbordo;
    }

    /**Metodo que obtiene la compra de un trayecto con transbordo
     * @return compras
     */
    public ComprasTransbordo getCompras() {
        return compras;
    }

    /**Metdo que inserta una compra de un trayecto con transbordo
     * @param compras
     */
    public void setCompras(ComprasTransbordo compras) {
        this.compras = compras;
    }

}

