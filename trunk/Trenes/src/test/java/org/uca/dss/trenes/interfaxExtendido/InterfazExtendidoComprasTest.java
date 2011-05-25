/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementar todas las pruebas para los transbordos Esta es para realizar las compras
 */

package org.uca.dss.trenes.interfaxExtendido;

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
public class InterfazExtendidoComprasTest extends InterfazTest {

    /**
     * Comprueba que una reserva reduzca los asientos libres 
     * para ese día y fecha, y
     * no modifica ni otro día ni otra hora cualquiera
     */
    @Test
    public void testUnaReserva() {
        List<LocalTime> horasPosibles = getHorasPosibles();
        LocalTime hora, hora2;

        hora = getHoraAleatoria(horasPosibles);

        do {
            hora2 = getHoraAleatoria(horasPosibles);
        } while (hora2.equals(hora));

        int libresAntes = compras.asientosLibres(origen, destino, hoy, hora);
        int libresOtraHoraAntes = compras.asientosLibres(origen, destino, hoy, hora2);
        compras.reservaAsiento(origen, destino, hoy, hora);
        int libresDespues = compras.asientosLibres(origen, destino, hoy, hora);
        assertSame(libresAntes, libresDespues+1);
        int libresOtroDia = compras.asientosLibres(origen, destino,
                hoy.plusDays(1), hora);
        assertSame(libresAntes, libresOtroDia);
        int libresOtraHoraDespues = compras.asientosLibres(origen, destino,
                hoy, hora2);
        assertSame(libresOtraHoraAntes, libresOtraHoraDespues);
    }

    /**
     * Comprueba que asientos libres se reduzca al reservar cada vez
     */
    @Test
    public void testReservarReduceAsientosLibres() {
        List<LocalTime> horas = getHorasPosibles();
        LocalTime hora = getHoraAleatoria(horas);

        int libres = compras.asientosLibres(origen, destino, hoy, hora);
        int reservados=0;

        while (compras.asientosLibres(origen, destino, hoy, hora)>0) {
            compras.reservaAsiento(origen, destino, hoy, hora);
            reservados = reservados+1;
        }

        assertSame("No permite reservar tantos asientos como libres", libres, reservados);
    }

    /**
     * Comprueba que se puede cancelar una reservada
     */
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

    }
}