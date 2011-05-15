/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.trenes.interfaz;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Before;
import org.uca.dss.curso1011.grupo6.CargarDatos;
import org.uca.dss.curso1011.grupo6.Compras;
import org.uca.dss.curso1011.grupo6.Trayecto;
import org.uca.dss.curso1011.grupo6.Tren;
import org.uca.dss.curso1011.grupo6.Viajes;
import org.uca.dss.trenes.basededatos.DBUtils;

/**
 *
 * @author dmolina
 */
public abstract class InterfazTest {
    /** Referencia a la clase Listados */
    protected InterfazListados listado;
    /** Referencia a la clase compras */
    protected InterfazCompras compras;
    protected LocalDate hoy;
//    protected GestionReservas gestion;
    protected String origen;
    protected String destino;

    public InterfazTest() {
        origen = "cadiz";
        destino = "sevilla";
        hoy = new LocalDate();
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

        List<Tren> trenes = cd.CargarDatosTrenes("./src/main/resources/trenes.csv");
        List<Trayecto> trayectos = cd.CargarDatosTrayectos("./src/main/resources/trayectos.csv",trenes);
        
        Viajes viajes = new Viajes(trayectos);
        Compras listCompras = new Compras();

        listCompras.setViajes(viajes);

        listado = viajes;
        compras = listCompras;
    }

    @After
    public void tearDown() {
//        gestion.clear();
        listado = null;
        compras = null;
    }

}
