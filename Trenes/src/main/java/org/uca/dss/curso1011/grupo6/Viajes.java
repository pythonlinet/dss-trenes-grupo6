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
import org.uca.dss.curso1011.grupo6.basededatos.DBUtils;
import org.uca.dss.trenes.interfaz.InterfazListados;

/**
 * Clase encargada de realizar los listados de los viajes
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */
public class Viajes implements InterfazListados{
    private List<Trayecto> trayectos;
    
    /**
     *
     * @param trayectos
     */
    public Viajes(List<Trayecto> trayectos){
        this.trayectos = trayectos;
        trayectos = new ArrayList();
    }
/**
 * 
 * @param fecha
 * @param CiudadOrigen
 * @param CiudadDestino
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
     * @return the trayectos
     */
    public List<Trayecto> getTrayectos() {
        return trayectos;
    }

/**
 * 
 * @param trayectoArg
 * @param fecha
 * @return Lista de reservas del trayecto en la fecha indicada
 * Preguntar a Dani el viernes. Importante NO OLVIDAR!!!!!
 */
    public List<Reserva> obtenerReservas(Trayecto trayectoArg, LocalDate fecha){
        
        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        String origen = trayectoArg.getCiudadOrigen();
        String destino = trayectoArg.getCiudadDestino();
        LocalTime hora = trayectoArg.getHorario().getSalida();

        Trayecto trayectoComprueba = buscarTrayecto(origen, destino, hora);

        ObjectContainer databases = DBUtils.getDb();

        List <Reserva> reservas = databases.query(new Predicate <Reserva>() {
            public boolean match ( Reserva reserva) {
                return true;
            }
        }) ;
        

        Iterator<Reserva> iReservas = reservas.iterator();
        while (iReservas.hasNext())
        {
            Reserva reserva = iReservas.next();
            
            if(reserva.getFecha().equals(fecha) && reserva.getTrayecto().equals(trayectoComprueba))
            {
                reservasValidas.add(reserva);
            }
        }                    
            return reservasValidas;
    }
    
/**
 *
 * @param origen
 * @param destino
 * @param hora
 * @return trayecto que coincida con los paramátros
 */
    public Trayecto buscarTrayecto (String origen, String destino, LocalTime hora)
    {
        Iterator<Trayecto> iTrayectos = trayectos.iterator();
        Trayecto trayecto,trayectoEncontrado=null ;
        boolean encontrado = false;

        while (iTrayectos.hasNext() && encontrado != true)
        {
            trayecto = iTrayectos.next();
            if(trayecto.getCiudadOrigen().equals(origen) &&
                    trayecto.getCiudadDestino().equals(destino) &&
                    trayecto.getHorario().getSalida().equals(hora))
                    {
                        encontrado = true;
                        trayectoEncontrado = trayecto;
                    }
        }

        if(!encontrado)
        {
            throw new RuntimeException("No existe el trayecto");
        }
            
        return trayectoEncontrado;
    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fecha
     * @return horarios disponibles
     */
    public List<LocalTime> getHorarios(String origen, String destino, LocalDate fecha) {

        Iterator<Trayecto> iTrayectos = trayectos.iterator();

        List<LocalTime> horariosDisponibles;
        horariosDisponibles = new ArrayList();

        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        comprobarExcepcion(fecha,origen,destino);        

         while (iTrayectos.hasNext())
         {
           Trayecto trayecto = iTrayectos.next();
           if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino))
           {
               reservasValidas = obtenerReservas(trayecto,fecha);                     

               if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
               {

                   LocalTime hora = trayecto.getHorario().getSalida();
                   horariosDisponibles.add(hora);
               }
           }
        }
        return horariosDisponibles;
    }
}
