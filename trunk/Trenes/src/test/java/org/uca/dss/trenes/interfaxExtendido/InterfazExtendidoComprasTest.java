/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementar todas las pruebas para los transbordos. Esta es para realizar las compras
 */

package org.uca.dss.trenes.interfaxExtendido;

import org.uca.dss.curso1011.grupo6.AsientoConsecutivo;
import org.uca.dss.curso1011.grupo6.AsientoMenosUno;
import org.uca.dss.curso1011.grupo6.AsientoAleatorio;
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
 * Casos de Tests de compras
 * @author Jose Luis, Juan Carlos y Dani
 * 
 */
public class InterfazExtendidoComprasTest extends InterfazExtendidoTest {

    /**
     * Comprueba que una reserva reduzca los asientos libres 
     * para ese dia y fecha, y no modifica ni otro dia ni otra hora cualquiera
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
     * Comprueba que al realizar una reserva, entonces se reducen los asientos libres
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
     * Comprueba que se puede realizar una reserva y cancelar despues
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

     /**
     * Comprueba que no se puede cancelar una reserva si no está reservada
     */ 
     
     @Test(expected=RuntimeException.class)
     public void testCancelaSinReservar() {         
         InformacionTrayecto infoTrayecto = new InformacionTrayecto("Malaga","Badajoz",new LocalTime("10:00"),new LocalTime("13:00"),10);
         ReservaTrayecto reserva = new ReservaTrayecto(infoTrayecto,hoy, -1, "cadena");
         compras.cancelaReserva(reserva);
     }

     /**
     * Comprueba que realiza una reserva y cancela otra reserva
     */ 
     
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


    /**
     * Comprueba que no realice reserva de mas
     */ 
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
     
     /**
     * Comprueba que las reservas estan completas y cancela
     */     
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

     /**
     * Comprueba que realiza las reservas con la estrategia de asientos aleatorios
     */ 
   @Test
    public void testNumeroAsientosAleatorioNoMantenido() {
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);
        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());


        asiento = new AsientoAleatorio(listTransbordo.getTransbordo());
        comprasTransbordo.setAsiento(asiento);
        comprasTransbordo.setMantenerAsiento(false);

         pos = random.nextInt(itinerarios.size());
                    Itinerario itinerarioReservado = itinerarios.get(pos);
                    ArrayList reservasAsientos = new ArrayList();
                    int reservados = 0;

                     while (compras.asientosLibres(hoy, itinerarioReservado)>0) {
                        List<ReservaTrayecto> reservasAleatorio = compras.reservaAsiento(itinerarioReservado, hoy);
                        int asientos = reservasAleatorio.get(0).getNumeroAsiento();
                        reservasAsientos.add(asientos);
                        reservados = reservados+1;
                    }
                    Collections.sort(reservasAsientos);
                    for(int i =1; i<reservados; i++)
                    {
                        assertEquals(reservasAsientos.get(i),i);
                    }
    }

/**
     * Comprueba que realiza las reservas con la estrategia de asientos aleatorios
     */
   @Test
    public void testNumeroAsientosAleatorioMantenido() {
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);
        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());


        asiento = new AsientoAleatorio(listTransbordo.getTransbordo());
        comprasTransbordo.setAsiento(asiento);
        comprasTransbordo.setMantenerAsiento(true);

        pos = random.nextInt(itinerarios.size());
                    Itinerario itinerarioReservado = itinerarios.get(pos);
                    ArrayList reservasAsientos = new ArrayList();
                    int reservados = 0;


                     while (compras.asientosLibres(hoy, itinerarioReservado)>0) {
                        List<ReservaTrayecto> reservasAleatorio = compras.reservaAsiento(itinerarioReservado, hoy);
                        if(reservasAleatorio.size()==1)
                        {
                            int asientos = reservasAleatorio.get(0).getNumeroAsiento();
                            reservasAsientos.add(asientos);
                            reservados = reservados+1;
                        }
                        else
                        {
                            int asientoPrimerTramo = reservasAleatorio.get(0).getNumeroAsiento();
                            int asientoSegundoTramo = reservasAleatorio.get(1).getNumeroAsiento();
                            assertEquals(asientoPrimerTramo,asientoSegundoTramo);

                            reservasAsientos.add(asientoPrimerTramo);
                            reservados = reservados+1;
                        }
                    }
                    Collections.sort(reservasAsientos);
                    for(int i =1; i<reservados; i++)
                    {
                        assertEquals(reservasAsientos.get(i),i);
                    }
    }

    /**
     * Comprueba que realiza las reservas con la estrategia de asientos consecutivos
     */ 
   
    @Test
    public void testNumeroAsientosConsecutivoNoMantenido() {
        List<ReservaTrayecto> reservasTotales = new ArrayList<ReservaTrayecto>();
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);
        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());


        asiento = new AsientoConsecutivo();
        comprasTransbordo.setAsiento(asiento);
        comprasTransbordo.setMantenerAsiento(false);

        Itinerario itinerario = itinerarios.get(pos);

        for(int i= 0; i<10; i++)
                {
                    List<ReservaTrayecto> reservas = compras.reservaAsiento(itinerario, hoy);
                    int numAsiento = reservas.get(0).getNumeroAsiento();
                    reservasTotales.add(reservas.get(0));
                    assertEquals(numAsiento,i+1);
                    if(reservas.size()>1)
                    {
                        int asientoPrimerTramo = reservas.get(0).getNumeroAsiento();
                        assertEquals(asientoPrimerTramo,reservas.get(1).getNumeroAsiento());
                    }
                }
                pos = random.nextInt(reservasTotales.size());

                int asientoCancelado = reservasTotales.get(pos).getNumeroAsiento();

                compras.cancelaReserva(reservasTotales.get(pos));
                List<ReservaTrayecto> reservas = compras.reservaAsiento(itinerario, hoy);

                int comprobar = reservas.get(0).getNumeroAsiento();

                assertEquals(comprobar,asientoCancelado);
    }

     /**
     * Comprueba que realiza las reservas con la estrategia de asientos consecutivos
     */

    @Test
    public void testNumeroAsientosConsecutivoMantenido() {
        List<ReservaTrayecto> reservasTotales = new ArrayList<ReservaTrayecto>();
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);
        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());


        asiento = new AsientoConsecutivo();
        comprasTransbordo.setAsiento(asiento);
        comprasTransbordo.setMantenerAsiento(true);

        Itinerario itinerario = itinerarios.get(pos);

        for(int i= 0; i<10; i++)
                {
                    List<ReservaTrayecto> reservas = compras.reservaAsiento(itinerario, hoy);
                    int numAsiento = reservas.get(0).getNumeroAsiento();
                    reservasTotales.add(reservas.get(0));
                    assertEquals(numAsiento,i+1);
                }
                pos = random.nextInt(reservasTotales.size());

                int asientoCancelado = reservasTotales.get(pos).getNumeroAsiento();

                compras.cancelaReserva(reservasTotales.get(pos));
                List<ReservaTrayecto> reservas = compras.reservaAsiento(itinerario, hoy);

                int comprobar = reservas.get(0).getNumeroAsiento();

                assertEquals(comprobar,asientoCancelado);
    }
    
    /**
     * Comprueba que realiza las reservas con la estrategia de asientos igual a menos uno
     */ 

        @Test
    public void testNumeroAsientosMenosUno() {        
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);
        Random random = new Random();
        int pos = random.nextInt(itinerarios.size());


        asiento = new AsientoMenosUno();
        comprasTransbordo.setAsiento(asiento);        

        pos = random.nextInt(itinerarios.size());
                    Itinerario itinerarioReservadoM = itinerarios.get(pos);
                    ArrayList reservasAsientosM = new ArrayList();

                     while (compras.asientosLibres(hoy, itinerarioReservadoM)>0) {
                        List<ReservaTrayecto> reservasAleatorio = compras.reservaAsiento(itinerarioReservadoM, hoy);
                        int asientos = reservasAleatorio.get(0).getNumeroAsiento();
                        reservasAsientosM.add(asientos);
                    }
                    assertEquals(compras.asientosLibres(hoy, itinerarioReservadoM),0);
    }
   
}