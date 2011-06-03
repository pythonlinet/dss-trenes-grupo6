/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementar todas las pruebas para los transbordos Esta es para realizar los listados
 */

package org.uca.dss.trenes.interfaxExtendido;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;
import org.joda.time.LocalTime;
import java.util.List;
import org.joda.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Casos de Tests de listados
 * @author Jose Luis, Juan Carlos y Dani
 * 
 */
public class InterfazExtendidoListadosTest extends InterfazExtendidoTest {

    /**
     * Comprueba que para fechas iguales (sin reservas) los horarios coinciden
     */
    @Test
    public void testListadoSinReservasPorDias() {
        
        List<Itinerario> itinerariosRef = listado.getItinerarios(origen, destino, new LocalDate());
        for (int day = 1; day <= 10; day++) {
            
            List<Itinerario> itineAntes = listado.getItinerarios(origen, destino, new LocalDate().minusDays(day));
            List<Itinerario> itineDespues = listado.getItinerarios(origen, destino, new LocalDate().plusDays(day));

           assertEquals(itineAntes.size(),itinerariosRef.size());
           assertEquals(itineDespues.size(),itinerariosRef.size());
        }
    }

    /**
     * Compruebo las horas disponibles
     */
    @Test
    public void testListadosSinReservas() {
      List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, new LocalDate());
      List<Itinerario> itineAComprobar = getItinerariosPosibles();

      assertEquals(itinerarios.size(), itineAComprobar.size());
    }

    /**
     * 
     */
    @Test
    public void testItinerariosEntreHoras() {
        
        LocalTime Salida = new LocalTime("9:00");
        LocalTime Llegada = new LocalTime("15:10");

        List<Itinerario> compItinerarios = new ArrayList<Itinerario>();
        List<Itinerario> itineTodos = listado.getItinerarios(origen, destino, new LocalDate());
        List<Itinerario> itineEntre = listado.getItinerariosEntre(origen, destino, hoy, Salida, Llegada);

        for(Itinerario itinerario : itineTodos)
        {
            if(itinerario.size()>1)
            {
                    if(itinerario.get(0).getHoraSalida().isAfter(Salida) && itinerario.get(1).getHoraLlegada().isBefore(Llegada))
                    {
                        compItinerarios.add(itinerario);
                    }
            }
            else
            {
                if(itinerario.get(0).getHoraSalida().isAfter(Salida) && itinerario.get(0).getHoraLlegada().isBefore(Llegada))
                    {
                        compItinerarios.add(itinerario);
                    }

            }
        }


        assertEquals(itineEntre.size(),compItinerarios.size());


    }
    /**
     * Compruebo
     */
    @Test
    public void testListadosConHorariosLlenos() {
        List<Itinerario> itineAComprobar = listado.getItinerarios(origen, destino, hoy);

        Random random = new Random();
        int pos = random.nextInt(itineAComprobar.size());
        Itinerario itinerarioReservado = itineAComprobar.get(pos);
        
        
       while (compras.asientosLibres(hoy, itinerarioReservado) > 0) {
            compras.reservaAsiento(itinerarioReservado, hoy);
        }

        System.out.println("Asientos:"+compras.asientosLibres(hoy, itinerarioReservado));

        itineAComprobar.remove(pos);
        
        
        List<Itinerario> itinerarios = listado.getItinerarios(origen, destino, hoy);

        System.out.println("Comprobar"+itineAComprobar.size());
        System.out.println("Referencia"+itinerarios.size());
        
        assertEquals("No coinciden los itinerarios", new HashSet<Itinerario>(itineAComprobar),new HashSet<Itinerario>(itinerarios));
    }
   
}