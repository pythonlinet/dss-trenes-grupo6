/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.uca.dss.curso1011.grupo6.basededatos.DBUtils;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.ReservaTrayecto;

/**
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */

   /**Clase que genera un numero de asiento consecutivo
     * @param infoTrayecto
     * @return numero asiento del trayecto
     */

public class AsientoConsecutivo implements ReservaAsiento{


    /**Metodo que genera un numero de asiento consecutivo dado un trayecto
     * @param infoTrayecto
     * @return numero asiento del trayecto
     */
    private int generarAsientoConsecutivos(final InformacionTrayecto infoTrayecto)
    {
        int numAsiento=1;
        boolean flag = false;
        ArrayList asientos = new ArrayList();

       List <ReservaTrayecto> reservas = buscaAsientoComprobacion(infoTrayecto,0);

         for(ReservaTrayecto resTrayecto : reservas)
         {
             asientos.add(resTrayecto.getNumeroAsiento());
         }
         Collections.sort(asientos);

         for(int num=0; num < asientos.size(); num++)
         {
             if(!asientos.get(num).equals(num+1) && !flag)
             {
                 numAsiento=num+1;
                 flag = true;
             }
         }

         if(!flag && reservas.size()>0)
         {
             numAsiento=reservas.size()+1;
         }

        return numAsiento;
    }

    public int generarAsiento(InformacionTrayecto infoTrayecto) {
        return generarAsientoConsecutivos(infoTrayecto);
    }

    public int asignarAsiento(InformacionTrayecto infoTrayecto, int asiento) {
         final int comprobacionAsiento=asiento;

           List <ReservaTrayecto> reservas = buscaAsientoComprobacion(infoTrayecto,comprobacionAsiento);

            if(reservas.isEmpty())
            {
                return comprobacionAsiento;
            }
            else
            {
                return generarAsientoConsecutivos(infoTrayecto);
            }
    }

    
      /**
    * Metodo privado que comprueba si dos trayectos son iguales
    * @param trayecto1
    * @param trayecto2
    * @return verdad o falso dependiendo de la comprobacion
    */
    
    private boolean comprobarTrayecto(ReservaTrayecto reserva,InformacionTrayecto infoTrayecto)
    {
        if (reserva.getTrayecto().getOrigen().equals(infoTrayecto.getOrigen()) &&
                       reserva.getTrayecto().getDestino().equals(infoTrayecto.getDestino()) &&
                       reserva.getTrayecto().getHoraSalida().equals(infoTrayecto.getHoraSalida()) &&
                       reserva.getTrayecto().getHoraLlegada().equals(infoTrayecto.getHoraLlegada()))
        {
          return true;
        }
        else
        {    
          return false;
        }
    }
    
    private List<ReservaTrayecto> buscaAsientoComprobacion(final InformacionTrayecto infoTrayecto, final int comprobacionAsiento)
    {
        ObjectContainer databases = DBUtils.getDb();
        if(comprobacionAsiento>0)
        {
         
            List <ReservaTrayecto> reservas = databases.query(new Predicate <ReservaTrayecto>() {
            public boolean match ( ReservaTrayecto reserva) {
                return comprobarTrayecto(reserva,infoTrayecto) &&
                       reserva.getNumeroAsiento()==comprobacionAsiento;
            }
            }) ;
            return reservas;
        }
        else
        {
             List <ReservaTrayecto> reservas = databases.query(new Predicate <ReservaTrayecto>() {
                public boolean match ( ReservaTrayecto reserva) {
                return comprobarTrayecto(reserva,infoTrayecto);
                }
                });
             return reservas;
        }
    }


}
