/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;

/**
 * Clase para implementar el interfaz itinerario
 * @author Jose Luis
 */
public class ItinerarioImplementacionInterfaz implements Itinerario{

    private List<InformacionTrayecto> itrayectos;

    /**
     * 
     * @return 
     */
    public double getPrecio() {
        double precio = 0;

        Iterator iter = itrayectos.iterator();

         while (iter.hasNext()){
             InformacionTrayecto itrayecto = (InformacionTrayecto)iter.next();
             precio += itrayecto.getPrecio();
         }

        return precio;
    }

    /**
     * 
     * @return 
     */
    public int size() {
        return itrayectos.size();
    }

    /**
     * 
     * @return 
     */
    public boolean isEmpty() {
        return itrayectos.isEmpty();
    }

    /**
     * 
     * @param o
     * @return 
     */
    public boolean contains(Object o) {
        return itrayectos.contains((InformacionTrayecto)o);
    }

    /**
     * 
     * @return 
     */
    public Iterator<InformacionTrayecto> iterator() {
        return itrayectos.iterator();
    }

    /**
     * 
     * @return 
     */
    public Object[] toArray() {
        return itrayectos.toArray();
    }

    /**
     * 
     * @param <T>
     * @param a
     * @return 
     */
    public <T> T[] toArray(T[] a) {
        return itrayectos.toArray(a);
    }

    /**
     * 
     * @param e
     * @return 
     */
    public boolean add(InformacionTrayecto e) {
        return itrayectos.add(e);
    }

    /**
     * 
     * @param o
     * @return 
     */
    public boolean remove(Object o) {
        return itrayectos.remove((InformacionTrayecto)o);
    }

    /**
     * 
     * @param c
     * @return 
     */
    public boolean containsAll(Collection<?> c) {
        return itrayectos.containsAll(c);
    }

    /**
     * 
     * @param c
     * @return 
     */
    public boolean addAll(Collection<? extends InformacionTrayecto> c) {
        return itrayectos.addAll(c);
    }

    /**
     * 
     * @param index
     * @param c
     * @return 
     */
    public boolean addAll(int index, Collection<? extends InformacionTrayecto> c) {
        return itrayectos.addAll(index, c);
    }

    /**
     * 
     * @param c
     * @return 
     */
    public boolean removeAll(Collection<?> c) {
        return itrayectos.removeAll(c);
    }

    /**
     * 
     * @param c
     * @return 
     */
    public boolean retainAll(Collection<?> c) {
        return itrayectos.retainAll(c);
    }

    /**
     * 
     */
    public void clear() {
        itrayectos.clear();
    }

    /**
     * 
     * @param index
     * @return 
     */
    public InformacionTrayecto get(int index) {
        return itrayectos.get(index);
    }

    /**
     * 
     * @param index
     * @param element
     * @return 
     */
    public InformacionTrayecto set(int index, InformacionTrayecto element) {
        return itrayectos.set(index, element);
    }

    /**
     * 
     * @param index
     * @param element 
     */
    public void add(int index, InformacionTrayecto element) {
        itrayectos.add(index, element);
    }

    /**
     * 
     * @param index
     * @return 
     */
    public InformacionTrayecto remove(int index) {
        return itrayectos.remove(index);
    }

    /**
     * 
     * @param o
     * @return 
     */
    public int indexOf(Object o) {
        return itrayectos.indexOf(o);
    }

    /**
     * 
     * @param o
     * @return 
     */
    public int lastIndexOf(Object o) {
        return itrayectos.lastIndexOf(o);
    }

    /**
     * 
     * @return 
     */
    public ListIterator<InformacionTrayecto> listIterator() {
        return itrayectos.listIterator();
    }

    /**
     * 
     * @param index
     * @return 
     */
    public ListIterator<InformacionTrayecto> listIterator(int index) {
        return itrayectos.listIterator(index);
    }

    /**
     * 
     * @param fromIndex
     * @param toIndex
     * @return 
     */
    public List<InformacionTrayecto> subList(int fromIndex, int toIndex) {
        return itrayectos.subList(fromIndex, toIndex);
    }

}
