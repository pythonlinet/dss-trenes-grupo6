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
import org.joda.time.LocalTime;
import org.uca.dss.curso1011.grupo6.basededatos.DBUtils;
import org.uca.dss.trenes.interfaz.InterfazCompras;


/**
 * Clase que gestiona las reservas realizadas por lo clientes
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
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
     * Metodo que se encarga de realizar la reserva con la fecha de salida, ciudad origen
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

        trayecto = viajes.buscarTrayecto(origen,destino,hora);

        comprobarExcepcion(fecha,origen,destino,hora);

        if(getPlazasDisponibles(trayecto,fecha)>0)
        {
            ObjectContainer database = DBUtils.getDb();           

            codigoReserva = this.generarCodigo(trayecto);

            Reserva reserva = new Reserva(fecha, codigoReserva);
            reserva.setTrayecto(trayecto);
            
            database.store(reserva);
            database.commit();
        }
        else
        {
            throw new RuntimeException("No hay plazas disponibles");
        }
        
        return codigoReserva;

    }


    /**
     * Metodo que se encarga de cancelar una reserva recibiendo como parámetro 
     * el codigo de la reserva. Este metodo no tiene que devolver nada
     * @param codigoReserva para realizar la cancelacion
     */
    private void cancelarReserva(String codigoReserva)
    {        
        boolean flag=false;
        ObjectContainer database = DBUtils.getDb();

       if (codigoReserva.isEmpty() ) {
        throw new IllegalArgumentException("Codigo Reserva no especificada");
       }

       List <Reserva> reservas = database.query(new Predicate <Reserva>() {
       public boolean match ( Reserva reserva) {
       return true;
         }
       }) ;
       Iterator<Reserva> iReservas = reservas.iterator();
        

      while (iReservas.hasNext() && flag==false)
      {
              Reserva reserva = iReservas.next();

              if(reserva.getCodigoReserva().equals(codigoReserva))
              {
                        database.delete(reserva);
                        database.commit();
                        flag = true;
              }
      }
      if (!flag)
      {
            throw new RuntimeException("No existe la reserva "+codigoReserva +" a cancelar.");
      }

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
    private String generarCodigo(Trayecto trayecto)
    {
         String cod="";

         Calendar cal = Calendar.getInstance();

         cod=trayecto.getCiudadOrigen().substring(0,1)
                 +trayecto.getCiudadDestino().substring(0,1)
                 +trayecto.getHorario().getSalida().getHourOfDay()
                 +trayecto.getHorario().getSalida().getMinuteOfHour()
                 +Integer.toString(cal.get(Calendar.DATE))
                 +Integer.toString(cal.get(Calendar.MONTH)+1)
                 +Integer.toString(cal.get(Calendar.HOUR_OF_DAY))
                 +Integer.toString(cal.get(Calendar.MINUTE))
                 +Integer.toString(cal.get(Calendar.SECOND));

         return cod;

    }

        public List<Reserva> obtenerReservas(Trayecto trayectoArg, LocalDate fecha){

        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();

        String origen = trayectoArg.getCiudadOrigen();
        String destino = trayectoArg.getCiudadDestino();
        LocalTime hora = trayectoArg.getHorario().getSalida();

        Trayecto trayectoComprueba = viajes.buscarTrayecto(origen, destino, hora);

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
     * Metodo que se utiliza para calcular el numero de plazas disponibles que
     * existe de un trayecto a una fecha indicada.
     * Los parámetros de entrada de la función son el trayecto del que se quiere
     * realizar la comprobación y la fecha a la cual se quiere realizar la
     * comprobación
     * @param trayectoArg
     * @param fecha
     * @return 
     * @return PlazasDisponibles que hay del trayecto a la fecha indicada
     */
    private int getPlazasDisponibles(Trayecto trayectoArg,LocalDate fecha) {
        Iterator<Trayecto> iTrayectos = viajes.getTrayectos().iterator();
        int plazas = 0;
        boolean encontrado = false;
        
        List<Reserva> reservasValidas;
        reservasValidas = new ArrayList();     

         while (iTrayectos.hasNext() && encontrado != true)
         {
            Trayecto trayecto = iTrayectos.next();
            if(trayecto.getCiudadOrigen().equals(trayectoArg.getCiudadOrigen()) &&
                    trayecto.getCiudadDestino().equals(trayectoArg.getCiudadDestino()) &&
                    trayecto.getHorario().getSalida().equals(trayectoArg.getHorario().getSalida()))
                    {
                        encontrado = true;
                        reservasValidas = obtenerReservas(trayecto,fecha);
                        plazas =  trayecto.getTren().getPlazas() - reservasValidas.size();
                    }
        }
        if(!encontrado)
        {
            throw new RuntimeException("No existen trayectos en esa fecha");
        }
        
        return plazas;
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
        Trayecto trayecto = viajes.buscarTrayecto(origen, destino, hora);

        return trayecto.calcularPrecio(trayecto.getTren());

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
        Trayecto trayecto = viajes.buscarTrayecto(origen, destino, hora); 
        return getPlazasDisponibles(trayecto, fecha);                      
    }
}
