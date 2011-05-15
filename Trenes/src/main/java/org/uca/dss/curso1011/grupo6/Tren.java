/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;


/**
 *
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos Ríos Legupín
 */
public class Tren {
    private String nombre;
    private int plazas;
    private double precio;

    /**
     * 
     * @param nombre
     * @param plazas
     * @param precio
     */
    public Tren(String nombre, int plazas, double precio){
        this.nombre = nombre;
        this.plazas = plazas;
        this.precio = precio;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the plazas
     */
    public int getPlazas() {
        return plazas;
    }

    /**
     * @param plazas the plazas to set
     */
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
