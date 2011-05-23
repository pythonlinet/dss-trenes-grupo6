/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.uca.dss.trenes.basededatos.DBUtils;
import org.uca.dss.trenes.interfaz.InterfazCompras;


/**
 * Clase que gestiona las reservas realizadas por lo clientes
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */
public class Compras implements InterfazCompras{
    
    private Viajes viajes;

    public Compras(){}

    /**
     * Metodo que se encarga de introducir un viaje
     * @param viajes
     */
    public void setViajes(Viajes viajes){
        this.viajes = viajes;
    }

    /**
    * Funcion que se encarga de comprobar las excepciones de fecha, ciudad origen
    * y destino y la hora
    * @param fecha de salida de la reserva
    * @param ciudadOrigen del trayecto
    * @param ciudadDestino del trayecto
    * @param hora de salida del trayecto
    */
    private void comprobarExcepcion(LocalDate fecha,String ciudadOrigen, String ciudadDestino, LocalTime hora)
    {
        if ( ciudadOrigen.isEmpty() ) {
        throw new IllegalArgumentException("Ciudad Origen no especificada");
        }

        if ( ciudadDestino.isEmpty() ) {
        throw new IllegalArgumentException("Ciudad Destino no especificada");
        }

        if ( fecha.toString().isEmpty() ) {
        throw new IllegalArgumentException("Fecha no especificada");
        }
        
        if ( hora.toString().isEmpty() ) {
        throw new IllegalArgumentException("Hora no especificada");
        }
    }

    /**
     * Metodo que se encarga de realizar la reserva con la fecha de salida, ciudad
     * y destino y la hora de salida
     * @param fecha salida de la reserva
     * @param origen del trayecto
     * @param destino del trayecto
     * @param hora salida
     * @return codigo de la reserva que se ha realizado
     */
     private String realizarReserva(LocalDate fecha, String origen, String destino, LocalTime hora)
     {
        Trayecto trayecto;
        String codigoReserva="";

        trayecto = viajes.BuscarTrayecto(origen,destino,hora);

        comprobarExcepcion(fecha,origen,destino,hora);

        if(viajes.getPlazasDisponibles(trayecto,fecha)>0)
        {

            ObjectContainer db = DBUtils.getDb();

            List <Reserva> reservas = db.query(new Predicate <Reserva>() {
            public boolean match ( Reserva reserva) {
                return true;
            }
            }) ;

            codigoReserva = this.generarCodigo(trayecto);

            Reserva reserva = new Reserva(fecha, codigoReserva);
            reserva.setTrayecto(trayecto);
            
            db.store(reserva);
            db.commit();
        }
        else
            throw new RuntimeException("No hay plazas disponibles");

        return codigoReserva;

    }


    /**
     * Metodo que se encarga de cancelar una reserva recibiendo como par�metro 
     * el codigo de la reserva. Este metodo no tiene que devolver nada
     * @param codigoReserva para realizar la cancelacion
     */
    private void cancelarReserva(String codigoReserva)
    {        
        boolean flag=false;
        ObjectContainer db = DBUtils.getDb();

       if (codigoReserva.isEmpty() ) {
        throw new IllegalArgumentException("Codigo Reserva no especificada");
       }

       List <Reserva> reservas = db.query(new Predicate <Reserva>() {
       public boolean match ( Reserva reserva) {
       return true;
         }
       }) ;
       Iterator i = reservas.iterator();
        

      while (i.hasNext() && flag==false)
      {
              Reserva reserva = (Reserva)i.next();

              if(reserva.getCodigoReserva().equals(codigoReserva))
              {
                        db.delete(reserva);
                        db.commit();
                        flag = true;
              }
      }
      if (!flag)
            throw new RuntimeException("No existe la reserva "+codigoReserva +" a cancelar.");


    }

    /**
     * Metodo que se encarga de generar un codigo unico para una reserva
     * usando la primera letra de la ciudad origen y de destino y los trenes.
     * Tambi�n usamos las horas y los minutos de la salida del trayecto.
     * Adem�s se usa el dia, mes, a�o, hora, minuto y segundo del momento en el 
     * que se ha hecho la reserva
     * @param trayecto
     * @return codigo generado
     */
    private String generarCodigo(Trayecto trayecto)
    {
         String cod="";

         Calendar c = Calendar.getInstance();

         cod=trayecto.getCiudadOrigen().substring(0,1)
                 +trayecto.getCiudadDestino().substring(0,1)
                 +trayecto.getTren().getNombre().substring(0,1)
                 +trayecto.getHorario().getSalida().getHourOfDay()
                 +trayecto.getHorario().getSalida().getMinuteOfHour()
                 +Integer.toString(c.get(Calendar.DATE))
                 +Integer.toString(c.get(Calendar.MONTH)+1)
                 +Integer.toString(c.get(Calendar.HOUR_OF_DAY))
                 +Integer.toString(c.get(Calendar.MINUTE))
                 +Integer.toString(c.get(Calendar.SECOND));

         return cod;

    }

    /**
     * Metodo que se encarga de obtener el precio de un trayecto. Los parametros
     * que se le proporcionan son ciudad origen y destino, la fecha de salida y 
     * la hora de salida.
     * @param origen del trayecto
     * @param destino del trayecto
     * @param fecha de la reserva
     * @param hora de salida del trayecto
     * @return precio del trayecto
     */
    public double getPrecio(String origen, String destino, LocalDate fecha, LocalTime hora)
    {
        try{

        comprobarExcepcion(fecha,origen,destino,hora);
        Trayecto trayecto = viajes.BuscarTrayecto(origen, destino, hora);

        return trayecto.CalcularPrecio(trayecto.getTren());

         } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    /**
     * Metodo que se encarga de realizar una reserva para un trayecto. Los parametros
     * que necesita son la ciudad de origen y de destino, la fecha de salida y la hora de salida.
     * @param origen del trayecto
     * @param destino del trayecto
     * @param fecha de la reserva
     * @param hora de salida del trayecto
     * @return codigo del asiento reservado
     */
    public String reservaAsiento(String origen, String destino, LocalDate fecha, LocalTime hora){
        return realizarReserva(fecha,origen,destino,hora);
    }

    /**
     * Metodo que se encarga de cancelar una reserva. Recibe como parametro el 
     * codigo de la reserva
     * @param codigoReserva de la que se quiere realizar la cancelacion
     */
    public void cancelaReserva(String codigoReserva) {
        this.cancelarReserva(codigoReserva);
    }
    
    /**
     * Metodo que se encarga de calcular los asientos libres para un trayecto.
     * Los parametro que tiene son la ciudad de origen y de destino, la fecha de salida
     * y la hora de salida.
     * @param origen del trayecto
     * @param destino del trayecto
     * @param fecha que se quiere realizar la reserva
     * @param hora de salida
     * @return numero de asientos reservados
     */
    public int asientosLibres(String origen, String destino, LocalDate fecha, LocalTime hora)
    {
        Trayecto trayecto = viajes.BuscarTrayecto(origen, destino, hora); 
        return viajes.getPlazasDisponibles(trayecto, fecha);                      
    }
}