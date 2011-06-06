/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
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
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */
public class ComprasTransbordo implements InterfazCompras{
    
    private Transbordo transbordo;    
    private ReservaAsiento reservaAsiento;
    private boolean mantenerAsiento;
    /*
    public ComprasTransbordo(char modoAsiento)
    {
        this.modoAsiento=modoAsiento;
    }
*/

    public ComprasTransbordo()
    {
        
    }


    /** Metodo que devuelve la lista de los asientos reservados dado un itinerario
      * y una fecha
      * @param itinerario
      * @param fecha
      * @return lista de asientos reservados
      */
    public List<ReservaTrayecto> reservaAsiento(Itinerario itinerario, LocalDate fecha) {
         List<ReservaTrayecto> reservasTrayecto = new ArrayList();
         ReservaTrayecto reserva = null;
         int cont=1,asiento = 0;

         if(asientosLibres(fecha,itinerario)>0)
         {

            for( InformacionTrayecto iTrayecto: itinerario)
            {
                    if(cont==1)
                    {
                        reserva = new ReservaTrayecto(iTrayecto,fecha, reservaAsiento.generarAsiento(iTrayecto), generarCodigo(iTrayecto));
                        cont++;
                        asiento=reserva.getNumeroAsiento();
                    }
                    else
                    {
                        reserva = new ReservaTrayecto(iTrayecto,fecha, reservaAsiento.asignarAsiento(iTrayecto, asiento), generarCodigo(iTrayecto));
                    }

                    ObjectContainer databases = DBUtils.getDb();

                    databases.store(reserva);
                    databases.commit();

                    reservasTrayecto.add(reserva);

             }
         }
            else
                {
                    throw new RuntimeException("No hay plazas disponibles");
                }

         return reservasTrayecto;
    }

     /**
     * Metodo que se encarga de generar un codigo unico para una reserva
     * usando la primera letra de la ciudad origen y de destino y los trenes.
     * Tambiï¿½n usamos las horas y los minutos de la salida del trayecto.
     * Ademï¿½s se usa el dia, mes, aï¿½o, hora, minuto y segundo del momento en el
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

    /**Metodo que devuelve el menor numero de asientos libres entre
     * dos trayectos dados un itinerario y la fecha    
     * @param fecha
     * @param itinerario
     * @return devuelve el menor numero de asientos libres
     */
    public int asientosLibres(final LocalDate fecha, Itinerario itinerario) {

        int minimo = 1000000;

        for(final InformacionTrayecto infoTrayecto : itinerario)
        {
            Trayecto trayecto = transbordo.getListado().getViajes().buscarTrayecto(infoTrayecto.getOrigen(), infoTrayecto.getDestino(), infoTrayecto.getHoraSalida());

            ObjectContainer databases = DBUtils.getDb();

            ObjectSet<ReservaTrayecto> reservas = databases.query(new Predicate<ReservaTrayecto>() {
            @Override
            public boolean match(ReservaTrayecto reserva) {
                return reserva.getFechaSalida().equals(fecha) &&
                       reserva.getTrayecto().getOrigen().equals(infoTrayecto.getOrigen()) &&
                       reserva.getTrayecto().getDestino().equals(infoTrayecto.getDestino()) &&
                       reserva.getTrayecto().getHoraSalida().equals(infoTrayecto.getHoraSalida()) &&
                       reserva.getTrayecto().getHoraLlegada().equals(infoTrayecto.getHoraLlegada());
            }
            }) ;

            int plazas =  trayecto.getVehiculo().getPlazas() - reservas.size();


            if(minimo > plazas )
            {
                 minimo = plazas;
            }
            
        }        
        return minimo;
    }

    /**Metodo que cancela la reserva pasada como parametro
      * @param reserva 
     */
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

              if(reservaTrayecto.getCodigoReserva().equals(reserva.getCodigoReserva())&&
                      reservaTrayecto.getNumeroAsiento() == reserva.getNumeroAsiento())
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

    /**Metodo que cancela las reservas de la lista de reservas
     * recibida como parametro 
     * @param reservas 
     */
    public void cancelaReserva(List<ReservaTrayecto> reservas) {
        
        Iterator<ReservaTrayecto> iReservas=reservas.iterator();
        
        while(iReservas.hasNext())
        {
            ReservaTrayecto reservaBorrar = iReservas.next();
            cancelaReserva(reservaBorrar);
        }

    }


    /**Metodo que devuelve los transbordos
     * @return the transbordo
     */
    public Transbordo getTransbordo() {
        return transbordo;
    }

    /**Metodo que inserta un transbordo
     * @param transbordo the transbordo to set
     */
    public void setTransbordo(Transbordo transbordo) {
        this.transbordo = transbordo;
    }

    /**
     * @return the modoAsiento
     */
    public ReservaAsiento getAsiento() {
        return reservaAsiento;
    }

    public void setAsiento(ReservaAsiento asiento) {
       this.reservaAsiento = asiento;
    }

    /**
     * @return the mantenerAsiento
     */
    public boolean isMantenerAsiento() {
        return mantenerAsiento;
    }

    /**
     * @param mantenerAsiento the mantenerAsiento to set
     */
    public void setMantenerAsiento(boolean mantenerAsiento) {
        this.mantenerAsiento = mantenerAsiento;
    }

}
