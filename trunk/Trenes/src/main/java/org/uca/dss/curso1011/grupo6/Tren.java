/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

/**
 * Clase que guarda los diferentes datos de los trenes que usamos en los trayectos
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */
public class Tren implements Vehiculo{
    private String nombre;
    private int plazas;
    private double precio;

    /**
     * Contructor de la clase tren con los parametros nombre, plazas y precio
     * @param nombre del tren
     * @param plazas del tren
     * @param precio del tren
     */
    public Tren(String nombre, int plazas, double precio){
        this.nombre = nombre;
        this.plazas = plazas;
        this.precio = precio;
    }

    /**
     * Metodo que se encarga de obtener el nombre del tren
     * @return nombre del tren
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que se encarga de insertar el nombre del tren
     * @param nombre del tren
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que se encarga de obtener las plazas del tren
     * @return plazas del tren
     */
    public int getPlazas() {
        return plazas;
    }

    /**
     * Metodo que se encarga de insertar las plazas en el tren
     * @param plazas del tren
     */
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    /**
     * Metodo que se encarga de obtener el precio del tren
     * @return precio del tren
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Metodo que se encarga de insertar el precio del tren
     * @param precio del tren
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
