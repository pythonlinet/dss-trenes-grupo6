/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementar todas las pruebas para los transbordos Esta es para realizar los listados
 */

package org.uca.dss.trenes.interfaxExtendido;

import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import org.joda.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import org.joda.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Casos de Tests de listados
 * @author dmolina
 */
public class InterfazExtendidoListadosTest extends InterfazExtendidoTest {

    /**
     * Comprueba que para fechas iguales (sin reservas) los horarios coinciden
     */
    @Test
    public void testListadoSinReservasPorDias() {
        int days = 10;
        List<Itinerario> horasRef = listado.getItinerarios(origen, destino, new LocalDate());

        for (int day = 1; day <= 10; day++) {
            List<Itinerario> horasAntes = listado.getItinerarios(origen, destino, new LocalDate().minusDays(day));
            List<Itinerario> horasDespues = listado.getItinerarios(origen, destino, new LocalDate().minusDays(day));
            assertEquals(horasAntes, horasRef);
            assertEquals(horasRef, horasDespues);
        }
    }

    /**
     * Compruebo las horas disponibles
     */
    @Test
    public void testListadosSinReservas() {
        //Set<LocalTime> horas = new HashSet<LocalTime>(listado.getHorarios(origen, destino, new LocalDate()));
        Set<LocalTime> horasACompobar = new HashSet<LocalTime>(getHorasPosibles());
        //assertSame(horas.size(), horasACompobar.size());

        // Compruebo que ese horario ha desaparecido
        //assertEquals("No coinciden los horarios", horasACompobar, horas);
    }

    /**
     * Compruebo
     */
    @Test
    public void testListadosConHorariosLlenos() {
        List<LocalTime> horasACompobar = getHorasPosibles();
        // Reservo una hora
        Random random = new Random();
        int pos = random.nextInt(horasACompobar.size());
        LocalTime horaReservada = horasACompobar.get(pos);
        // Reservo hasta que puedo puedo más
        //while (compras.asientosLibres(origen, destino, hoy, horaReservada)>0) {
            //compras.reservaAsiento(origen, destino, hoy, horaReservada);
        //}

        //horasACompobar.remove(pos);
        
        // Compruebo qued haya que comprobar
        //List<LocalTime> horas = listado.getHorarios(origen, destino, hoy);

        // Compruebo que ese horario ha desaparecido
        //assertEquals("No coinciden los horarios", new HashSet<LocalTime>(horasACompobar),
          //                                        new HashSet<LocalTime>(horas));
    }
   
}