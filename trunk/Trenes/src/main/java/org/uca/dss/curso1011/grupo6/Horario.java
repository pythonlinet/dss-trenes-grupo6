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
     * Constructor de la clase Horario. Lo construye a partir de los parametros
     * de salida y llegada
     * @param salida, horario de salida
     * @param llegada, horario de llegada
     */
    public Horario(LocalTime salida, LocalTime llegada){
        this.salida = salida;
        this.llegada = llegada;
    }

    /**
     * Metodo para obtener la hora de salida de un horario
     * @return salida, horario de salida
     */
    public LocalTime getSalida() {
        return salida;
    }

    /**
     * Metodo para introducir el parametro salida
     * @param salida, horario de salida
     */
    public void setSalida(LocalTime salida) {
        this.salida = salida;
    }

    /**
     * Metodo para obtener el horario de llegada
     * @return llegada, horario de llegada
     */
    public LocalTime getLlegada() {
        return llegada;
    }

    /**
     * Metodo para insertar la hora de llegada
     * @param llegada, horario de llegada
     */
    public void setLlegada(LocalTime llegada) {
        this.llegada = llegada;
    }
}
