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
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */
public class Transbordo{

    private Listado listado;   
    /**
     * 
     */
    public Transbordo ()
    {

    }

    /**Metodo que devuelve toda la informacion de los trayectos dado
     * una ciudad origen, ciudad destino y una fecha 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return lista de itinerarios
     */
    public List<Itinerario> buscarInformacionTrayectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList<Itinerario>();

        List<Trayecto> trayectos = getListado().getViajes().getTrayectos();

        for(Trayecto iTrayecto : trayectos)
        {
             if(iTrayecto.getCiudadOrigen().equals(origen) &&
                   !iTrayecto.getCiudadDestino().equals(destino))
             {
                    String origen1 = iTrayecto.getCiudadOrigen();
                    String destino1 = iTrayecto.getCiudadDestino();
                    LocalTime salida1 = iTrayecto.getHorario().getSalida();
                    LocalTime llegada1 =  iTrayecto.getHorario().getLlegada();
                    double precio1 = iTrayecto.getTren().getPrecio();

                    for(Trayecto jTrayecto : trayectos)
                    {
                        if(jTrayecto.getCiudadOrigen().equals(destino1) &&
                            jTrayecto.getCiudadDestino().equals(destino) &&
                            jTrayecto.getHorario().getSalida().compareTo(llegada1.plusMinutes(10)) >= 0)
                        {

                                InformacionTrayecto itrayecto1 = new InformacionTrayecto(origen1,destino1,salida1,llegada1,precio1);
                                InformacionTrayecto itrayecto2 = new InformacionTrayecto(jTrayecto.getCiudadOrigen(),jTrayecto.getCiudadDestino(),jTrayecto.getHorario().getSalida(),jTrayecto.getHorario().getLlegada(),jTrayecto.getTren().getPrecio());

                                List<InformacionTrayecto> itinerario = new ArrayList<InformacionTrayecto>();
                                itinerario.add(itrayecto1);
                                itinerario.add(itrayecto2);

                                itinerarios.add(new ItinerarioImplementacionInterfaz(itinerario));

                            }
                        }
                }
             }
        return itinerarios;
    }

    /**Metodo que devuelve la lista de los trayectos directos entre
     * ciudad origen y ciudad destino dadasn en la fecha pasada como
     * parametro
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return lista de itinerarios
     */
    public List<Itinerario> buscarTrayectosDirectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Trayecto> trayectos = this.getListado().getViajes().getTrayectos();
        List<Itinerario> listItinerarios = new ArrayList<Itinerario>();


        for(Trayecto iteTrayecto : trayectos)
        {
            String origenTrayecto = iteTrayecto.getCiudadOrigen();
            String destinoTrayecto = iteTrayecto.getCiudadDestino();
            if(origenTrayecto.equals(origen) && destinoTrayecto.equals(destino))
            {
                LocalTime salidaTrayecto = iteTrayecto.getHorario().getSalida();
                LocalTime llegadaTrayecto = iteTrayecto.getHorario().getLlegada();
                double precioTrayecto = iteTrayecto.calcularPrecio(iteTrayecto.getTren());
                InformacionTrayecto infoTrayecto = new InformacionTrayecto(origenTrayecto, destinoTrayecto, salidaTrayecto, llegadaTrayecto, precioTrayecto);                

                List<InformacionTrayecto> itinerario = new ArrayList<InformacionTrayecto>();
                itinerario.add(infoTrayecto);
                listItinerarios.add(new ItinerarioImplementacionInterfaz(itinerario));
            }
        }
        return listItinerarios;
    }

    /**Metodo de obtiene un listado
     * @return the listado
     */
    public Listado getListado() {
        return listado;
    }

    /**Metodo que inserta un listado
     * @param listado the listado to set
     */
    public void setListado(Listado listado) {
        this.listado = listado;
    }

}
