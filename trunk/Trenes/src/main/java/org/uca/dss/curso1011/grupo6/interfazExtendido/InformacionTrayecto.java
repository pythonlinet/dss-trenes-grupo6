/*
 *  Copyright (C) 2010 dmolina
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.uca.dss.curso1011.grupo6.interfazExtendido;

import org.joda.time.LocalTime;

/**
 * Esta clase guarda la información de cada viaje individual, para poder informar
 * mejor en los viajes con transbordo
 * @author dmolina
 */
public class InformacionTrayecto {
    /** Ciudad origen del viaje */
    private String origen;
    /** Ciudad destino del viaje */
    private String destino;
    /** hora de inicio del viaje,
     */
    private LocalTime horaSalida;
    /** hora de fin del viaje */
    private LocalTime horaLlegada;

    private double precio;
    
    /**
     * Constructor del itinerario. Es importante tener en cuenta que la
     * hora de llegada no tiene por qué ser posterior a la hora de salida
     * (podría llegarse el día siguiente a la salida). 
     *
     * @param origen ciudad origen
     * @param destino ciudad destino
     * @param horaSalida hora de salida
     * @param horaLlegada
     */
    public InformacionTrayecto(String origen, String destino,
                                LocalTime horaSalida, LocalTime horaLlegada, double precio) {
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.precio  = precio;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @return the horaSalida
     */
    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    /**
     * @return the horaLlegada
     */
    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.origen != null ? this.origen.hashCode() : 0);
        hash = 31 * hash + (this.destino != null ? this.destino.hashCode() : 0);
        hash = 31 * hash + (this.horaSalida != null ? this.horaSalida.hashCode() : 0);
        hash = 31 * hash + (this.horaLlegada != null ? this.horaLlegada.hashCode() : 0);
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.precio) ^ (Double.doubleToLongBits(this.precio) >>> 32));
        return hash;
    }
    
    @Override
    public boolean equals (Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }
        final InformacionTrayecto other = (InformacionTrayecto)obj;

        if(!(this.origen.equals(other.origen)))
        {
            return false;
        }
        if(!(this.destino.equals(other.destino)))
        {
            return false;
        }
        if(!(this.horaSalida.isEqual(other.horaSalida)))
        {
            return false;
        }
        if(!(this.horaLlegada.isEqual(other.horaLlegada)))
        {
            return false;
        }
        if(this.precio != other.precio)
        {
            return false;
        }

        return true;
    }
}
