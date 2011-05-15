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
import org.uca.dss.trenes.basededatos.DBUtils;
import org.uca.dss.trenes.interfaz.InterfazListados;

/**
 *
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
   public void ComprobarExcepcion(LocalDate fecha,String CiudadOrigen, String CiudadDestino)
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
     * @return the PlazasDisponibles
     */
    public int getPlazasDisponibles(Trayecto trayectoArg,LocalDate fecha) {
        Iterator i = trayectos.iterator();
        int plazas = 0;
        boolean encontrado = false;
        
        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();
      
        ObjectContainer db = DBUtils.getDb();

        List <Reserva> reservas = db.query(new Predicate <Reserva>() {
            public boolean match ( Reserva reserva) {
                return true;
            }
        }) ;

         while (i.hasNext() && encontrado != true)
         {
            Trayecto trayecto = (Trayecto)i.next();
            if(trayecto.getCiudadOrigen().equals(trayectoArg.getCiudadOrigen()) &&
                    trayecto.getCiudadDestino().equals(trayectoArg.getCiudadDestino()) &&
                    trayecto.getHorario().getSalida().equals(trayectoArg.getHorario().getSalida()))
                    {
                        encontrado = true;
                        reservasValidas = ObtenerReservas(trayecto,fecha);
                        plazas =  trayecto.getTren().getPlazas() - reservasValidas.size();
                    }
        }

        if(!encontrado)
            throw new RuntimeException("No exist el trayectos en esa fecha");
        
        return plazas;
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
 */
    public List<Reserva> ObtenerReservas(Trayecto trayectoArg, LocalDate fecha){
        
        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        String origen = trayectoArg.getCiudadOrigen();
        String destino = trayectoArg.getCiudadDestino();
        LocalTime hora = trayectoArg.getHorario().getSalida();

        Trayecto trayectoComprueba = BuscarTrayecto(origen, destino, hora);

        ObjectContainer db = DBUtils.getDb();

        List <Reserva> reservas = db.query(new Predicate <Reserva>() {
            public boolean match ( Reserva reserva) {
                return true;
            }
        }) ;
        

        Iterator j = reservas.iterator();
        while (j.hasNext())
        {
            Reserva reserva = (Reserva)j.next();
            
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
    public Trayecto BuscarTrayecto (String origen, String destino, LocalTime hora)
    {
        Iterator i = trayectos.iterator();
        Trayecto trayecto,trayectoen=null ;
        boolean encontrado = false;

        while (i.hasNext() && encontrado != true)
        {
            trayecto = (Trayecto)i.next();
            if(trayecto.getCiudadOrigen().equals(origen) &&
                    trayecto.getCiudadDestino().equals(destino) &&
                    trayecto.getHorario().getSalida().equals(hora))
                    {
                        encontrado = true;
                        trayectoen = trayecto;
                    }
        }

        if(!encontrado)
            throw new RuntimeException("No existe el trayecto");

        return trayectoen;
    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fecha
     * @return horarios disponibles
     */
    public List<LocalTime> getHorarios(String origen, String destino, LocalDate fecha) {

        Iterator i = trayectos.iterator();

        List<LocalTime> horariosDisponibles;
        horariosDisponibles = new ArrayList();

        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        ComprobarExcepcion(fecha,origen,destino);

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
               reservasValidas = ObtenerReservas(trayecto,fecha);                     

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
