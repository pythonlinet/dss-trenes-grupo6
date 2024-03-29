/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import java.util.List;
import java.util.Random;
import org.uca.dss.curso1011.grupo6.basededatos.DBUtils;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.ReservaTrayecto;

/**
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */

/**Clase que genera un numero de asiento aleatorio
 */
public class AsientoAleatorio implements ReservaAsiento {

    Transbordo transbordo;

   
    /**Constructor de la clase AsientoAleatorio
     */
    public AsientoAleatorio(Transbordo transbordo){
        this.transbordo = transbordo;
    }

    /**Metodo que genera un numero de asiento del trayecto dado
     * @param infoTrayecto
     * @return numero asiento del trayecto
     */
    private int generarAsientoAleatorio(final InformacionTrayecto infoTrayecto)
    {
        int numAsiento=-1;
        boolean flag=true;

        Trayecto trayecto = transbordo.getListado().getViajes().buscarTrayecto(infoTrayecto.getOrigen(), infoTrayecto.getDestino(), infoTrayecto.getHoraSalida());

        int plazas = trayecto.getVehiculo().getPlazas();

        while(flag)
        {
            Random random = new Random();
            numAsiento = random.nextInt(plazas);

            final int comprobacionAsiento=numAsiento;

            List <ReservaTrayecto> reservas = buscaAsiento(infoTrayecto,comprobacionAsiento);

            if(reservas.isEmpty())
            {
                flag=false;
            }
        }

        return numAsiento;
    }

     /**Interfaz generarAsiento
     * @param infoTrayecto 
     * @return generarAsientoAleatorio
     */
    public int generarAsiento(InformacionTrayecto infoTrayecto) {
        return generarAsientoAleatorio(infoTrayecto);
    }

    
     /**Metodo que asigna el numero de asiento pasado como parametro del trayecto dado
     * @param infoTrayecto asiento
     * @return numero asiento del trayecto
     */
    public int asignarAsiento(final InformacionTrayecto infoTrayecto, int asiento) {
           final int comprobacionAsiento=asiento;

           List <ReservaTrayecto> reservas = buscaAsiento(infoTrayecto,comprobacionAsiento);

            if(reservas.isEmpty())
            {
                return comprobacionAsiento;
            }
            else
            {
                return generarAsientoAleatorio(infoTrayecto);
            }
       }

    /**Metodo que devuelve los asientos que coincida con el trayecto y numero de asiento dado como parametro
     * @param infoTrayecto 
     * @return lista de reservas del trayecto
     */
    
    private List<ReservaTrayecto> buscaAsiento(final InformacionTrayecto infoTrayecto, final int comprobacionAsiento)
    {
         ObjectContainer databases = DBUtils.getDb();
            List <ReservaTrayecto> reservas = databases.query(new Predicate <ReservaTrayecto>() {
            public boolean match ( ReservaTrayecto reserva) {
                return reserva.getTrayecto().getOrigen().equals(infoTrayecto.getOrigen()) &&
                       reserva.getTrayecto().getDestino().equals(infoTrayecto.getDestino()) &&
                       reserva.getTrayecto().getHoraSalida().equals(infoTrayecto.getHoraSalida()) &&
                       reserva.getTrayecto().getHoraLlegada().equals(infoTrayecto.getHoraLlegada()) &&
                       reserva.getNumeroAsiento()==comprobacionAsiento;
            }
            }) ;
            return reservas;
    }
}
