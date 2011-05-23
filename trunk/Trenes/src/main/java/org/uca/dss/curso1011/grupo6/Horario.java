/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;
import org.joda.time.LocalTime;

/**
 * Clase que contiene la hora de salida y de llegada de los trayectos
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */
public class Horario {
    private LocalTime salida;
    private LocalTime llegada;

    /**
     * 
     * @param Salida
     * @param Llegada
     */
    public Horario(LocalTime salida, LocalTime llegada){
        this.salida = salida;
        this.llegada = llegada;
    }

    /**
     * 
     * @return hora salida
     */
    public LocalTime getSalida() {
        return salida;
    }

    /**
     * @param Salida the Salida to set
     */
    public void setSalida(LocalTime salida) {
        this.salida = salida;
    }

    /**
     * @return hora llegada
     */
    public LocalTime getLlegada() {
        return llegada;
    }

    /**
     * @param Llegada the Llegada to set
     */
    public void setLlegada(LocalTime llegada) {
        this.llegada = llegada;
    }
}
