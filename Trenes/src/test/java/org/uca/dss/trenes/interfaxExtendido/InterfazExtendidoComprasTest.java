/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementar todas las pruebas para los transbordos Esta es para realizar las compras
 */

package org.uca.dss.trenes.interfaxExtendido;

import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedList;
import org.joda.time.LocalTime;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.ReservaTrayecto;
import java.util.Random;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dmolina
 */
public class InterfazExtendidoComprasTest extends InterfazExtendidoTest {

    /**
     * Comprueba que una reserva reduzca los asientos libres 
     * para ese día y fecha, y
     * no modifica ni otro día ni otra hora cualquiera
     */
    @Test
    public void testUnaReserva() {
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);        

        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());        
        Itinerario itinerarioReservado = itinerarios.get(pos);
      
        int libresAntes = compras.asientosLibres(hoy, itinerarioReservado);        
        compras.reservaAsiento(itinerarioReservado, hoy);
        int libresDespues = compras.asientosLibres(hoy, itinerarioReservado);
        assertSame(libresAntes, libresDespues+1);
        int libresOtroDia = compras.asientosLibres(hoy.plusDays(1), itinerarioReservado);
        assertSame(libresAntes, libresOtroDia);        
    }

    /**
     * Comprueba que asientos libres se reduzca al reservar cada vez
     */
    @Test
    public void testReservarReduceAsientosLibres() {
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);

        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());
        Itinerario itinerarioReservado = itinerarios.get(pos);

        int libres = compras.asientosLibres(hoy, itinerarioReservado);
        int reservados=0;

        while (compras.asientosLibres(hoy, itinerarioReservado)>0) {
            compras.reservaAsiento(itinerarioReservado, hoy);
            reservados = reservados+1;
        }

        assertSame("No permite reservar tantos asientos como libres", libres, reservados);
    }

    /**
     * Comprueba que se puede cancelar una reservada
     */    
     public void testReservaYCancela() {
         
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);

        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());
        Itinerario itinerarioReservado = itinerarios.get(pos);

        int libresAntes = compras.asientosLibres(hoy, itinerarioReservado);
        List<ReservaTrayecto> reservas = compras.reservaAsiento(itinerarioReservado, hoy);
        int libresDespues = compras.asientosLibres(hoy, itinerarioReservado);
        compras.cancelaReserva(reservas);
        int libresTrasCancelar = compras.asientosLibres(hoy, itinerarioReservado);

        assertSame(libresAntes, libresDespues+1);
        assertSame(libresAntes, libresTrasCancelar);
     }

     @Test(expected=RuntimeException.class)
     public void testCancelaSinReservar() {         
         InformacionTrayecto infoTrayecto = new InformacionTrayecto("Malaga","Badajoz",new LocalTime("10:00"),new LocalTime("13:00"),10);
         ReservaTrayecto reserva = new ReservaTrayecto(infoTrayecto,hoy, -1, "cadena");
         compras.cancelaReserva(reserva);
     }

     @Test
     public void testReservaYCancelaOtra() {
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);

        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());
        Itinerario itinerarioReservado = itinerarios.get(pos);

        InformacionTrayecto infoTrayecto = itinerarioReservado.get(0);
        ReservaTrayecto reserva = new ReservaTrayecto(infoTrayecto,hoy, -1, "NULO");

        try {
            compras.cancelaReserva(reserva);
            fail("Cancela una reserva con falso codigo");
         } catch (RuntimeException e) { }
         
     }



     @Test(expected=RuntimeException.class)
     public void testReservaDeMas() {
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);

        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());
        Itinerario itinerarioReservado = itinerarios.get(pos);

         while (compras.asientosLibres(hoy, itinerarioReservado) > 0) {
                 compras.reservaAsiento(itinerarioReservado, hoy);
         }

         assertTrue(compras.asientosLibres(hoy, itinerarioReservado)==0);
         compras.reservaAsiento(itinerarioReservado, hoy);
     }

     @Test
     public void testLLenaYCancela() {
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);
        List<ReservaTrayecto> reservas = new LinkedList<ReservaTrayecto>();

        for (Itinerario itinerario : itinerarios) {
             int libresInicialmente = compras.asientosLibres(hoy, itinerario);
             reservas.clear();

             while (compras.asientosLibres(hoy, itinerario) > 0) {
                    List<ReservaTrayecto> reservasItinerario = compras.reservaAsiento(itinerario, hoy);
                    reservas.addAll(reservasItinerario);
             }

             for (ReservaTrayecto reserva : reservas) {
                 compras.cancelaReserva(reserva);
             }

             int libresFinal = compras.asientosLibres(hoy, itinerario);
             assertSame(libresInicialmente, libresFinal);
         }

    }

 @Test
    public void testNumeroAsientos() {
        List<ReservaTrayecto> reservasTotales = new ArrayList<ReservaTrayecto>();
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);
        char tipo = tipoAsiento;
        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());
      

        Itinerario itinerario = itinerarios.get(0);

        switch (tipo)
        {
            case 'c':
                for(int i= 0; i<10; i++)
                {
                    List<ReservaTrayecto> reservas = compras.reservaAsiento(itinerario, hoy);
                    int numAsiento = reservas.get(0).getNumeroAsiento();
                    reservasTotales.addAll(reservas);
                    assertEquals(numAsiento,i+1);
                }
                pos = random.nextInt(reservasTotales.size());

                int asientoCancelado = reservasTotales.get(pos).getNumeroAsiento();

                compras.cancelaReserva(reservasTotales.get(pos));
                List<ReservaTrayecto> reservas = compras.reservaAsiento(itinerario, hoy);
                
                int comprobar = reservas.get(0).getNumeroAsiento();

                assertEquals(comprobar,asientoCancelado);


              break;
            case 'a':

                    pos = random.nextInt(itinerarios.size());
                    Itinerario itinerarioReservado = itinerarios.get(pos);
                    ArrayList reservasAsientos = new ArrayList();
                    int reservados = 0;

                     while (compras.asientosLibres(hoy, itinerarioReservado)>0) {
                        List<ReservaTrayecto> reservasAleatorio = compras.reservaAsiento(itinerarioReservado, hoy);
                        int asiento = reservasAleatorio.get(0).getNumeroAsiento();
                        reservasAsientos.add(asiento);
                        reservados = reservados+1;
                    }
                    Collections.sort(reservasAsientos);
                    for(int i =1; i<reservados; i++)
                    {
                        assertEquals(reservasAsientos.get(i),i);
                    }

                    

               break;
            case 'm':
                    pos = random.nextInt(itinerarios.size());
                    Itinerario itinerarioReservadoM = itinerarios.get(pos);
                    ArrayList reservasAsientosM = new ArrayList();
                    reservados = 0;

                     while (compras.asientosLibres(hoy, itinerarioReservadoM)>0) {
                        List<ReservaTrayecto> reservasAleatorio = compras.reservaAsiento(itinerarioReservadoM, hoy);
                        int asiento = reservasAleatorio.get(0).getNumeroAsiento();
                        reservasAsientosM.add(asiento);
                    }
                    assertEquals(compras.asientosLibres(hoy, itinerarioReservadoM),0);
               break;
        }

    }
}