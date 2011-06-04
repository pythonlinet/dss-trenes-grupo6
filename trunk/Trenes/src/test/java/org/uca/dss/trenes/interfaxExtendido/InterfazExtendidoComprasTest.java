/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementar todas las pruebas para los transbordos Esta es para realizar las compras
 */

package org.uca.dss.trenes.interfaxExtendido;

import java.util.Random;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import org.uca.dss.trenes.interfaz.*;
import java.util.LinkedList;
import org.joda.time.LocalTime;
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
    /*
     public void testReservaYCancela() {
        List<LocalTime> horas = getHorasPosibles();
        LocalTime hora = getHoraAleatoria(horas);
        int libresAntes = compras.asientosLibres(origen, destino, hoy, hora);
        String codigoReserva = compras.reservaAsiento(origen, destino, hoy, hora);
        int libresDespues = compras.asientosLibres(origen, destino, hoy, hora);
        compras.cancelaReserva(codigoReserva);
        int libresTrasCancelar = compras.asientosLibres(origen, destino, hoy, hora);

        assertSame(libresAntes, libresDespues+1);
        assertSame(libresAntes, libresTrasCancelar);
     }

     @Test(expected=RuntimeException.class)
     public void testCancelaSinReservar() {
         compras.cancelaReserva("cadena");
     }

     @Test
     public void testReservaYCancelaOtra() {
         List<LocalTime> horas = getHorasPosibles();
         LocalTime hora = getHoraAleatoria(horas);
         String codigo = compras.reservaAsiento(origen, destino, hoy, hora);
         codigo = codigo+"NULO";
         try {
            compras.cancelaReserva(codigo);
            fail("Canceló una reserva con falso código");
         } catch (RuntimeException e) { }
         
     }

     @Test(expected=RuntimeException.class)
     public void testReservaDeMas() {
         List<LocalTime> horas = getHorasPosibles();
         LocalTime hora = getHoraAleatoria(horas);

         while (compras.asientosLibres(origen, destino, hoy, hora) > 0) {
                 compras.reservaAsiento(origen, destino, hoy, hora);
         }

         assertTrue(compras.asientosLibres(origen, destino, hoy, hora)==0);
         compras.reservaAsiento(origen, destino, hoy, hora);
     }

     @Test
     public void testLLenaYCancela() {
        List<LocalTime> horas = getHorasPosibles();
        List<String> codigos = new LinkedList<String>();

        for (LocalTime hora : horas) {
             int libresInicialmente = compras.asientosLibres(origen, destino, hoy, hora);
             codigos.clear();

             while (compras.asientosLibres(origen, destino, hoy, hora) > 0) {
                 String codigo = compras.reservaAsiento(origen, destino, hoy, hora);
                 codigos.add(codigo);
             }

             int cancelado = 0;

             for (String codigo : codigos) {
                 compras.cancelaReserva(codigo);
                 cancelado++;
             }

             int libresFinal = compras.asientosLibres(origen, destino, hoy, hora);
             assertSame(libresInicialmente, libresFinal);
         }

    }*/
}