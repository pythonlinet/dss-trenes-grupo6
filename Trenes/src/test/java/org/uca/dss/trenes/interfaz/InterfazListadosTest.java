/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.trenes.interfaz;

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
public class InterfazListadosTest extends InterfazTest {

    /**
     * Comprueba que para fechas iguales (sin reservas) los horarios coinciden
     */
    @Test
    public void testListadoSinReservasPorDias() {
        List<LocalTime> horasRef = listado.getHorarios(origen, destino, new LocalDate());

        for (int day = 1; day <= 10; day++) {
            List<LocalTime> horasAntes = listado.getHorarios(origen, destino, new LocalDate().minusDays(day));
            List<LocalTime> horasDespues = listado.getHorarios(origen, destino, new LocalDate().minusDays(day));
            assertEquals(horasAntes, horasRef);
            assertEquals(horasRef, horasDespues);
        }
    }

    /**
     * Compruebo las horas disponibles
     */
    @Test
    public void testListadosSinReservas() {
        Set<LocalTime> horas = new HashSet<LocalTime>(listado.getHorarios(origen, destino, new LocalDate()));
        Set<LocalTime> horasACompobar = new HashSet<LocalTime>(getHorasPosibles());
        assertSame(horas.size(), horasACompobar.size());

        // Compruebo que ese horario ha desaparecido
        assertEquals("No coinciden los horarios", horasACompobar, horas);
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
        while (compras.asientosLibres(origen, destino, hoy, horaReservada)>0) {
            compras.reservaAsiento(origen, destino, hoy, horaReservada);
        }

        horasACompobar.remove(pos);
        
        // Compruebo qued haya que comprobar
        List<LocalTime> horas = listado.getHorarios(origen, destino, hoy);

        // Compruebo que ese horario ha desaparecido
        assertEquals("No coinciden los horarios", new HashSet<LocalTime>(horasACompobar),
                                                  new HashSet<LocalTime>(horas));
    }
   
}