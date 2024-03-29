/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

/**
 * @author Jose Luis Aparicio Rodriguez
 * @author Daniel Ruiz Camacho
 * @author Juan Carlos R�os Legup�n
 */
public interface Vehiculo {
    
     /**
     * Metodo que se encarga de obtener el nombre del vehiculo
     * @return nombre del vehiculo
     */
    public String getNombre();

    /**
     * Metodo que se encarga de insertar el nombre del vehiculo
     * @param nombre del vehiculo
     */
    public void setNombre(String nombre);

    /**
     * Metodo que se encarga de obtener las plazas del vehiculo
     * @return plazas del vehiculo
     */
    public int getPlazas();

    /**
     * Metodo que se encarga de insertar las plazas en el vehiculo
     * @param plazas del vehiculo
     */
    public void setPlazas(int plazas);

    /**
     * Metodo que se encarga de obtener el precio del vehiculo
     * @return precio del vehiculo
     */
    public double getPrecio();

    /**
     * Metodo que se encarga de insertar el precio del vehiculo
     * @param precio del vehiculo
     */
    public void setPrecio(double precio);

}
