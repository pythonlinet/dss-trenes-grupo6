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
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * 
     * @param fichero
     * @return lista de trenes cargados
     * @throws IOException
     */
    public List<Tren> CargarDatosTrenes(String fichero) throws IOException{
        trenes = new ArrayList();
        try {            
            CSVReader reader = new CSVReader(new FileReader(fichero));
            String [ ] nextLine = null ;
            while (( nextLine = reader.readNext()) != null ) {
                Tren tren = new Tren(nextLine[0],Integer.parseInt(nextLine[1]),Float.parseFloat(nextLine[2]));
                trenes.add(tren);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return trenes;        
    }

    /**
     * 
     * @param fichero
     * @param trenes
     * @return lista de trayectos cargados
     * @throws IOException
     */
    public List<Trayecto> CargarDatosTrayectos(String fichero,List<Tren> trenes) throws IOException{
         trayectos = new ArrayList();

        try {
            CSVReader reader = new CSVReader(new FileReader(fichero));
            String [ ] nextLine = null ;
            while (( nextLine = reader.readNext()) != null ) {                
                int arg = 4;                
                
                while(arg + 1 < nextLine.length)
                {
                    Trayecto trayecto = new Trayecto (nextLine[1],nextLine[2],Integer.parseInt(nextLine[3]));

                    String nomTren = nextLine[0];                    
                    Iterator itrenes = trenes.iterator(); 
                    Tren tren = null;
                    while(itrenes.hasNext())
                    {
                        tren = (Tren)itrenes.next();

                        if(tren.getNombre().equals(nomTren))                            
                            trayecto.setTren(tren);                                                    
                    }
                    itrenes = null;                   
                                        
                    String[] CampoHora = nextLine[arg].split (":");
                    String[] CampoHora2 = nextLine[arg+1].split (":");

                    int h = Integer.parseInt(CampoHora[0]);
                    int m =Integer.parseInt(CampoHora[1]);

                    int h2 = Integer.parseInt(CampoHora2[0]);
                    int m2 =Integer.parseInt(CampoHora2[1]);

                    trayecto.setHorario(new Horario(new LocalTime(h,m),new LocalTime(h2,m2)));

                    trayectos.add(trayecto);
                    
                    arg = arg + 2;
                }                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
         return trayectos;
    }
}
