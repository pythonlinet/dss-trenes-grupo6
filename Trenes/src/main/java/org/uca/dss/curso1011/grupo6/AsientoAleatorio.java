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
     * @param infoTrayecto
     * @return numero asiento del trayecto
     */
public class AsientoAleatorio implements ReservaAsiento {

    Transbordo transbordo;

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
        ObjectContainer databases = DBUtils.getDb();
        while(flag)
        {
            Random random = new Random();
            numAsiento = random.nextInt(plazas);

            final int comprobacionAsiento=numAsiento;

            List <ReservaTrayecto> reservas = databases.query(new Predicate <ReservaTrayecto>() {
            public boolean match ( ReservaTrayecto reserva) {
                return reserva.getTrayecto().getOrigen().equals(infoTrayecto.getOrigen()) &&
                       reserva.getTrayecto().getDestino().equals(infoTrayecto.getDestino()) &&
                       reserva.getTrayecto().getHoraSalida().equals(infoTrayecto.getHoraSalida()) &&
                       reserva.getTrayecto().getHoraLlegada().equals(infoTrayecto.getHoraLlegada()) &&
                       reserva.getNumeroAsiento()==comprobacionAsiento;
            }
            }) ;

            if(reservas.isEmpty())
            {
                flag=false;
            }
        }

        return numAsiento;
    }

    public int generarAsiento(InformacionTrayecto infoTrayecto) {
        return generarAsientoAleatorio(infoTrayecto);
    }
}
