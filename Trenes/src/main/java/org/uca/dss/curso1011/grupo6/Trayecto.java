/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

/**
 * Clase que contiene los trayectos disponibles que se pueden realizar entre las
 * diferentes ciudades y los horarios disponibles que tenemos de ellos. También 
 * tenemos el numero de tramos del que se compone el trayecto. El tren con el que
 * se realiza el trayecto también lo guardamos
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */

public class Trayecto {
    private String ciudadOrigen;
    private String ciudadDestino;
    private int numeroTramos;
    //private Tren tren;
    private Vehiculo vehiculo;
    private Horario horario;    

    /**
     * Contructor de la clase trayecto
     * @param ciudadOrigen del trayecto
     * @param ciudadDestino del trayecto
     * @param numeroTramos del trayecto
     */
    public Trayecto(String ciudadOrigen, String ciudadDestino, int numeroTramos)
    {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.numeroTramos = numeroTramos;
    }
    /**
     * Calcula el precio del trayecto por el numero de tramos
     * @param trenTrayecto, tren del trayecto
     * @return precio del trayecto con el tren con el que lo realizamos
     */

    public double calcularPrecio(Vehiculo vehiculoTrayecto){
        return vehiculoTrayecto.getPrecio() * getNumeroTramos();
    }

    /**
     * Metodo que se encarga de obtener la ciudad de origen del trayecto
     * @return CiudadOrigen del trayecto
     */
    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    /**
     * Metodo que se encarga de introducir la ciudad origen en el trayecto
     * @param CiudadOrigen del trayecto
     */
    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    /**
     * Metodo que obtiene la ciudad de destino del trayecto
     * @return CiudadDestino del trayecto
     */
    public String getCiudadDestino() {
        return ciudadDestino;
    }

    /**
     * Metodo que se encarga de insertar la ciudad de destino en el trayecto
     * @param CiudadDestino del trayecto
     */
    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    /**
     * Metodo que obtiene el numero de tramos de un trayecto
     * @return numeroTramos del trayecto
     */
    public int getNumeroTramos() {
        return numeroTramos;
    }

    /**
     * Metodo que inserta el numero de tramos en el trayecto
     * @param NumeroTramos del trayecto
     */
    public void setNumeroTramos(int numeroTramos) {
        this.numeroTramos = numeroTramos;
    }

    /**
     * Metodo que obtiene el tren en el que se realiza el trayecto
     * @return tren del trayecto
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Metodo que se encarga de insertar el metodo en el trayecto
     * @param tren del trayecto
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * Metodo que se encarga de obtener el horario del trayecto
     * @return horario del trayecto
     */
    public Horario getHorario() {
        return horario;
    }

    /**
     * Metodo que se encarga de insertar el horario en el trayecto
     * @param horario del trayecto
     */
    public void setHorario(Horario horario) {
        this.horario = horario;
    }

}
