/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalTime;

/**
 * Clase que se encarga de cargar los datos de los trenes y de los trayectos
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */

public class CargarDatos {
    List<Tren> trenes;
    List<Trayecto> trayectos;

    /**
     * Metodo que se encarga de cargar los datos de un fichero de los trenes.
     * El parametro de entrada es un strign que contiene el nombre del fichero.
     * @param fichero, nombre del fichero que se va a cargar
     * @return lista de trenes cargados
     * @throws IOException
     */
    public List<Tren> cargarDatosTrenes(String fichero){
        trenes = new ArrayList();
        try {            
            CSVReader reader = new CSVReader(new FileReader(fichero));
            String [ ] nextLine = null ;
            try {
                while ((nextLine = reader.readNext()) != null) {
                    Tren tren = new Tren(nextLine[0].trim(), Integer.parseInt(nextLine[1].trim()), Float.parseFloat(nextLine[2].trim()));
                    trenes.add(tren);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }

        } catch (FileNotFoundException ex) {
             throw new RuntimeException(ex.getMessage());
        }

        return trenes;        
    }

    /**
     * Metodo que se encarga de cargar los datos de un fichero de los trayectos.
     * El parametro de entrada es un strign que contiene el nombre del fichero.
     * @param fichero
     * @param trenes
     * @return lista de trayectos cargados
     * @throws IOException
     */
    public List<Trayecto> cargarDatosTrayectos(String fichero,List<Tren> trenes){
         trayectos = new ArrayList();

        try {
            CSVReader reader = new CSVReader(new FileReader(fichero));
            String [ ] nextLine = null ;
            try {
                while ((nextLine = reader.readNext()) != null) {
                    int arg = 4;
                    while (arg + 1 < nextLine.length) {
                        Trayecto trayecto = new Trayecto(nextLine[1].trim(), nextLine[2].trim(), Integer.parseInt(nextLine[3].trim()));
                        String nomTren = nextLine[0].trim();
                        Iterator itrenes = trenes.iterator();
                        Tren tren = null;
                        while (itrenes.hasNext()) {
                            tren = (Tren) itrenes.next();
                            if (tren.getNombre().equals(nomTren)) {
                                trayecto.setTren(tren);
                            }
                        }
                        itrenes = null;
                        String[] CampoHora = nextLine[arg].trim().split(":");
                        String[] CampoHora2 = nextLine[arg + 1].trim().split(":");
                        int h = Integer.parseInt(CampoHora[0]);
                        int m = Integer.parseInt(CampoHora[1]);
                        int h2 = Integer.parseInt(CampoHora2[0]);
                        int m2 = Integer.parseInt(CampoHora2[1]);
                        trayecto.setHorario(new Horario(new LocalTime(h, m), new LocalTime(h2, m2)));
                        trayectos.add(trayecto);
                        arg = arg + 2;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } catch (FileNotFoundException ex) {
             throw new RuntimeException(ex.getMessage());
        }
         return trayectos;
    }
}
