/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalDate;
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
             InformacionTrayecto iTrayecto = iItinerario.next();

             if(getPlazasDisponibles(iTrayecto,fecha)>0)
             {
                ReservaTrayecto reserva = new ReservaTrayecto(iTrayecto,fecha, generarAsiento(iTrayecto), generarCodigo(iTrayecto));

                ObjectContainer database = DBUtils.getDb();

                database.store(reserva);
                database.commit();

             }else
             {
                throw new RuntimeException("No hay plazas disponibles");
             }
         }

         return reservasTrayecto;
    }

     /**
     * Metodo que se encarga de generar un codigo unico para una reserva
     * usando la primera letra de la ciudad origen y de destino y los trenes.
     * También usamos las horas y los minutos de la salida del trayecto.
     * Además se usa el dia, mes, año, hora, minuto y segundo del momento en el
     * que se ha hecho la reserva
     * @param trayecto
     * @return codigo generado
     */
    private String generarCodigo(InformacionTrayecto infoTrayecto)
    {
         String cod="";

         Calendar cal = Calendar.getInstance();

         cod=infoTrayecto.getOrigen().substring(0,1)
                 +infoTrayecto.getDestino().substring(0,1)
                 +infoTrayecto.getHoraSalida().getHourOfDay()
                 +infoTrayecto.getHoraSalida().getMinuteOfHour()
                 +Integer.toString(cal.get(Calendar.DATE))
                 +Integer.toString(cal.get(Calendar.MONTH)+1)
                 +Integer.toString(cal.get(Calendar.HOUR_OF_DAY))
                 +Integer.toString(cal.get(Calendar.MINUTE))
                 +Integer.toString(cal.get(Calendar.SECOND));

         return cod;

    }

    private int generarAsiento(InformacionTrayecto infoTrayecto)
    {
        int numAsiento=-1;
        boolean flag=true;

        Trayecto trayecto = transbordo.getViajes().buscarTrayecto(infoTrayecto.getOrigen(), infoTrayecto.getDestino(), infoTrayecto.getHoraSalida());

        int plazas = trayecto.getTren().getPlazas();

        ObjectContainer databases = DBUtils.getDb();
        while(flag)
        {
            numAsiento = (int) Math.random()*plazas+1;

            final int comprobacionAsiento=numAsiento;

            List <ReservaTrayecto> reservas = databases.query(new Predicate <ReservaTrayecto>() {
            public boolean match ( ReservaTrayecto reserva) {
                return reserva.getNumeroAsiento()==comprobacionAsiento;
            }
            }) ;
            
            if(reservas.isEmpty())
            {
                flag=false;
            }
        }

        return numAsiento;
    }

    public int asientosLibres(LocalDate fecha, Itinerario itinerario) {
        //Falta por completar. Preguntar a Dani
        return -1;
    }

    public void cancelaReserva(ReservaTrayecto reserva) {
        boolean flag=false;
        ObjectContainer database = DBUtils.getDb();

       if (reserva.getCodigoReserva().isEmpty() ) {
        throw new IllegalArgumentException("Codigo Reserva no especificada");
       }

       List <ReservaTrayecto> reservasTrayecto = database.query(new Predicate <ReservaTrayecto>() {
       public boolean match ( ReservaTrayecto reservaTrayecto) {
       return true;
         }
       }) ;
       Iterator<ReservaTrayecto> iReservas = reservasTrayecto.iterator();


      while (iReservas.hasNext() && flag==false)
      {
              ReservaTrayecto reservaTrayecto = iReservas.next();

              if(reservaTrayecto.getCodigoReserva().equals(reserva.getCodigoReserva()))
              {
                        database.delete(reservaTrayecto);
                        database.commit();
                        flag = true;
              }
      }
      if (!flag)
      {
            throw new RuntimeException("No existe la reserva "+ reserva.getCodigoReserva() +" a cancelar.");
      }
    }

    public void cancelaReserva(List<ReservaTrayecto> reservas) {
        
        Iterator<ReservaTrayecto> iReservas=reservas.iterator();
        
        while(iReservas.hasNext())
        {
            ReservaTrayecto reservaBorrar = iReservas.next();
            cancelaReserva(reservaBorrar);
        }

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
