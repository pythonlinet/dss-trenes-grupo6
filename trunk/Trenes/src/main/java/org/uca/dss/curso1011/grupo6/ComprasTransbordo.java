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
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazCompras;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import org.uca.dss.curso1011.grupo6.interfazExtendido.ReservaTrayecto;

/**
 * Clase que se encarga de realizar las compras de los transbordos
 * @author Jose Luis
 */
public class ComprasTransbordo implements InterfazCompras{

    private List<InformacionTrayecto> itrayectos;
    private Transbordo transbordo;

    public ComprasTransbordo()
    {
    }


     public List<ReservaTrayecto> obtenerReservasTrayecto(InformacionTrayecto itrayectoArg, LocalDate fecha){

        List<ReservaTrayecto> reservasValidas;
        reservasValidas = new ArrayList();

//        String origen = itrayectoArg.getOrigen();
//        String destino = itrayectoArg.getDestino();
//        LocalTime hora = itrayectoArg.getHoraSalida();

        //Trayecto trayectoComprueba = buscarTrayecto(origen, destino, hora);

        ObjectContainer databases = DBUtils.getDb();

        List <ReservaTrayecto> reservas = databases.query(new Predicate <ReservaTrayecto>() {
            public boolean match ( ReservaTrayecto reserva) {
                return true;
            }
        }) ;


        Iterator<ReservaTrayecto> iReservas = reservas.iterator();
        while (iReservas.hasNext())
        {
            ReservaTrayecto reserva = iReservas.next();

            if(reserva.getFechaSalida().equals(fecha) && reserva.getTrayecto().equals(itrayectoArg))
            {
                reservasValidas .add(reserva);
            }
        }
            return reservasValidas;
    }

     public int getPlazasDisponibles(InformacionTrayecto itrayectoArg,LocalDate fecha) {
        Iterator iter = itrayectos.iterator();
        int plazas = 0;
        boolean encontrado = false;

        List<ReservaTrayecto> reservasValidas;
        reservasValidas = new ArrayList();

         while (iter.hasNext() && encontrado != true)
         {
            InformacionTrayecto itrayecto = (InformacionTrayecto)iter.next();
            if(itrayecto.getOrigen().equals(itrayectoArg.getOrigen()) &&
                    itrayecto.getDestino().equals(itrayectoArg.getDestino()) &&
                    itrayecto.getHoraSalida().equals(itrayectoArg.getHoraSalida()))
                    {
                        encontrado = true;
                        reservasValidas = obtenerReservasTrayecto(itrayecto,fecha);
                        Trayecto trayecto = transbordo.getViajes().buscarTrayecto(itrayecto.getOrigen(), itrayecto.getDestino(), itrayecto.getHoraSalida());

                        plazas =  trayecto. getTren().getPlazas() - reservasValidas.size();
                    }
        }
        if(!encontrado)
        {
            throw new RuntimeException("No existen trayectos en esa fecha");
        }

        return plazas;
    }

    public List<ReservaTrayecto> reservaAsiento(Itinerario itinerario, LocalDate fecha) {
         List<ReservaTrayecto> reservasTrayecto = new ArrayList();

         Iterator<InformacionTrayecto> iItinerario = itinerario.iterator();

         while (iItinerario.hasNext())
         {
             InformacionTrayecto itrayecto = iItinerario.next();

             if(getPlazasDisponibles(itrayecto,fecha)>0)
             {
                ReservaTrayecto reserva = new ReservaTrayecto(itrayecto,fecha, numeroAsiento, itrayecto.getPrecio(), codigoReserva);
             }else
             {
                throw new RuntimeException("No hay plazas disponibles");
             }

             //Terminar falta por completar
         }

         return reservasTrayecto;
    }

    public int asientosLibres(LocalDate fecha, Itinerario itinerario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelaReserva(ReservaTrayecto reserva) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelaReserva(List<ReservaTrayecto> reservas) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * @return the itrayectos
     */
    public List<InformacionTrayecto> getItrayectos() {
        return itrayectos;
    }

    /**
     * @param itrayectos the itrayectos to set
     */
    public void setItrayectos(List<InformacionTrayecto> itrayectos) {
        this.itrayectos = itrayectos;
    }

    /**
     * @return the transbordo
     */
    public Transbordo getTransbordo() {
        return transbordo;
    }

    /**
     * @param transbordo the transbordo to set
     */
    public void setTransbordo(Transbordo transbordo) {
        this.transbordo = transbordo;
    }

}
