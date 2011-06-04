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
import org.uca.dss.trenes.interfaz.InterfazListados;


/**
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */

/**Clase que implementa los listados de todos los trayectos
 */
public class Listado implements InterfazListados{

    private Viajes viajes;
    private Compras compras;

    /**
    * Funcion que se encarga de comprobar las excepciones de fecha, ciudad origen
    * y destino y la hora
    * @param fecha de salida de la reserva
    * @param ciudadOrigen del trayecto
    * @param ciudadDestino del trayecto
    * @param hora de salida del trayecto
    */
   private void comprobarExcepcion(LocalDate fecha,String CiudadOrigen, String CiudadDestino)
    {
        if ( CiudadOrigen.isEmpty() ) {
        throw new IllegalArgumentException("Ciudad Origen no especificada");
        }

        if ( CiudadDestino.isEmpty() ) {
        throw new IllegalArgumentException("Ciudad Destino no especificada");
        }

        if ( fecha.toString().isEmpty() ) {
        throw new IllegalArgumentException("Fecha no especificada");
        }
    }

   /**
    * Metodo privado que comprueba si dos trayectos son iguales
    * @param trayecto1
    * @param trayecto2
    * @return verdad o falso dependiendo de la comprobacion
    */
    private boolean comprobarTrayecto(Trayecto trayecto,String origen,String destino,LocalDate fecha)
    {
         if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino) &&
                   (compras.asientosLibres(origen, destino, fecha, trayecto.getHorario().getSalida()))> 0)
           {
          return true;
        }
        else
        {    
          return false;
        }
    }

    /**Metodo que obtiene los horarios disponibles de un trayecto
    ** dado ciudad origen, ciudad destino y la fecha
     * @param origen
     * @param destino
     * @param fecha
     * @return horarios disponibles
     */
    public List<LocalTime> getHorarios(String origen, String destino, LocalDate fecha) {

        Iterator<Trayecto> iTrayectos = viajes.getTrayectos().iterator();

        List<LocalTime> horasDisponibles;
        horasDisponibles = new ArrayList();

        comprobarExcepcion(fecha,origen,destino);

         while (iTrayectos.hasNext())
         {
           Trayecto trayecto = iTrayectos.next();
           if(comprobarTrayecto(trayecto,origen,destino,fecha))
           {
                   LocalTime hora = trayecto.getHorario().getSalida();
                   horasDisponibles.add(hora);
           }
        }
        return horasDisponibles;
    }

    /**Metodo que devuelve los viajes
     * @return the viajes
     */
    public Viajes getViajes() {
        return viajes;
    }

    /**Metodo que inserta un viaje
     * @param viajes the viajes to set
     */
    public void setViajes(Viajes viajes) {
        this.viajes = viajes;
    }

    /**Metodo que obtiene una compra
     * @return the compras
     */
    public Compras getCompras() {
        return compras;
    }

    /**Metodo que introduce una compra
     * @param compras the compras to set
     */
    public void setCompras(Compras compras) {
        this.compras = compras;
    }
}
