/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

/**
 * Clase que contiene los trayectos disponibles que se pueden realizar
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */

public class Trayecto {
    private String ciudadOrigen;
    private String ciudadDestino;
    private int numeroTramos;
    private Tren tren;
    private Horario horario;    

    /**
     * 
     * @param CiudadOrigen
     * @param CiudadDestino
     * @param NumeroTramos
     */
    public Trayecto(String ciudadOrigen, String ciudadDestino, int numeroTramos)
    {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.numeroTramos = numeroTramos;
    }
    /**
     *
     * @param trenTrayecto
     * @return
     */

    public double CalcularPrecio(Tren trenTrayecto){
        return trenTrayecto.getPrecio() * getNumeroTramos();
    }

    /**
     * @return the CiudadOrigen
     */
    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    /**
     * @param CiudadOrigen the CiudadOrigen to set
     */
    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    /**
     * @return the CiudadDestino
     */
    public String getCiudadDestino() {
        return ciudadDestino;
    }

    /**
     * @param CiudadDestino the CiudadDestino to set
     */
    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    /**
     * @return the NumeroTramos
     */
    public int getNumeroTramos() {
        return numeroTramos;
    }

    /**
     * @param NumeroTramos the NumeroTramos to set
     */
    public void setNumeroTramos(int numeroTramos) {
        this.numeroTramos = numeroTramos;
    }

    /**
     * @return the tren
     */
    public Tren getTren() {
        return tren;
    }

    /**
     * @param tren the tren to set
     */
    public void setTren(Tren tren) {
        this.tren = tren;
    }

    /**
     * @return the horario
     */
    public Horario getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(Horario horario) {
        this.horario = horario;
    }

}
