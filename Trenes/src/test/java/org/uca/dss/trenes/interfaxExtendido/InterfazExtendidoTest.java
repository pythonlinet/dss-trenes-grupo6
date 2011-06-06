/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.trenes.interfaxExtendido;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Before;
import org.uca.dss.curso1011.grupo6.AsientoAleatorio;
import org.uca.dss.curso1011.grupo6.CargarDatos;
import org.uca.dss.curso1011.grupo6.Compras;
import org.uca.dss.curso1011.grupo6.ComprasTransbordo;
import org.uca.dss.curso1011.grupo6.ItinerarioReal;
import org.uca.dss.curso1011.grupo6.Listado;
import org.uca.dss.curso1011.grupo6.ListadoTransbordo;
import org.uca.dss.curso1011.grupo6.ReservaAsiento;
import org.uca.dss.curso1011.grupo6.Transbordo;
import org.uca.dss.curso1011.grupo6.Trayecto;
import org.uca.dss.curso1011.grupo6.Tren;
import org.uca.dss.curso1011.grupo6.Viajes;
import org.uca.dss.curso1011.grupo6.basededatos.DBUtils;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazCompras;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazListados;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;

/**
 *
 * @author dmolina
 */
public abstract class InterfazExtendidoTest {
    /** Referencia a la clase Listados */
    protected InterfazListados listado;
    /** Referencia a la clase compras */
    protected InterfazCompras compras;
    protected ComprasTransbordo comprasTransbordo;
    protected ListadoTransbordo listTransbordo;
    protected LocalDate hoy;
    protected String origen;
    protected String destino;
    protected char tipoAsiento;
    protected ReservaAsiento asiento;
    
    public InterfazExtendidoTest() {
        origen = "cadiz";
        destino = "madrid";
        hoy = new LocalDate();        
    }

    public List<Itinerario> getItinerariosPosibles()
    {
        List<Itinerario> itineComprobar = new ArrayList<Itinerario>();
        InformacionTrayecto infoTrayecto = new InformacionTrayecto(origen,destino,new LocalTime("9:15"),new LocalTime("13:15"),30);
        List<InformacionTrayecto> itinerarioDirecto = new ArrayList<InformacionTrayecto>();
        itinerarioDirecto.add(infoTrayecto);
        itineComprobar.add(new ItinerarioReal(itinerarioDirecto));

        InformacionTrayecto infoTrayecto1 = new InformacionTrayecto(origen,"sevilla",new LocalTime("9:00"),new LocalTime("11:00"),10);
        InformacionTrayecto infoTrayecto2 = new InformacionTrayecto("sevilla",destino,new LocalTime("11:30"),new LocalTime("15:30"),30);
        InformacionTrayecto infoTrayecto3 = new InformacionTrayecto("sevilla",destino,new LocalTime("17:30"),new LocalTime("21:30"),30);
        InformacionTrayecto infoTrayecto4 = new InformacionTrayecto(origen,"sevilla",new LocalTime("13:00"),new LocalTime("15:00"),10);

        List<InformacionTrayecto> itineTransbordo = new ArrayList<InformacionTrayecto>();
        itineTransbordo.add(infoTrayecto1);
        itineTransbordo.add(infoTrayecto2);
        itineComprobar.add(new ItinerarioReal(itineTransbordo));

        List<InformacionTrayecto> itineTransbordo2 = new ArrayList<InformacionTrayecto>();
        itineTransbordo2.add(infoTrayecto1);
        itineTransbordo2.add(infoTrayecto3);
        itineComprobar.add(new ItinerarioReal(itineTransbordo2));

        List<InformacionTrayecto> itineTransbordo3 = new ArrayList<InformacionTrayecto>();
        itineTransbordo3.add(infoTrayecto4);
        itineTransbordo3.add(infoTrayecto3);
        itineComprobar.add(new ItinerarioReal(itineTransbordo3));


        return itineComprobar;
    }

    public List<LocalTime> getHorasPosibles() {
        List<LocalTime> horasComprobar = new LinkedList<LocalTime>();
        horasComprobar.add(new LocalTime("9:00"));
        horasComprobar.add(new LocalTime("9:15"));
        horasComprobar.add(new LocalTime("13:00"));
        horasComprobar.add(new LocalTime("17:00"));
        horasComprobar.add(new LocalTime("19:00"));
        return horasComprobar;
    }

    public LocalTime getHoraAleatoria(List<LocalTime> horas) {
        int pos = new Random().nextInt(horas.size());
        return horas.get(pos);
    }

    @Before
    public void setUp() throws IOException {
        
        DBUtils.initDataBase("reservastests.dat");
        CargarDatos cd = new CargarDatos();

        List<Tren> trenes = cd.cargarDatosTrenes("./src/main/resources/trenes.csv");
        List<Trayecto> trayectos = cd.cargarDatosTrayectos("./src/main/resources/trayectosextendido.csv",trenes);

        Viajes viajes = new Viajes(trayectos);
        Listado listadoInt = new Listado();
        Compras listCompras = new Compras();        
        
        listCompras.setViajes(viajes);
        
        listadoInt.setViajes(viajes);
        listadoInt.setCompras(listCompras);
        
        Transbordo transbordo = new Transbordo();
        transbordo.setListado(listadoInt);

        asiento = new AsientoAleatorio(transbordo);

        comprasTransbordo = new ComprasTransbordo();
        comprasTransbordo.setAsiento(asiento);
        
        comprasTransbordo.setTransbordo(transbordo);        

        listTransbordo = new ListadoTransbordo();

        listTransbordo.setTransbordo(transbordo);
        listTransbordo.setCompras(comprasTransbordo);
 
        listado = listTransbordo;
        compras = comprasTransbordo;        
    }

    @After
    public void tearDown() {
        DBUtils.clear();
        listado = null;
        compras = null;
    }
}
