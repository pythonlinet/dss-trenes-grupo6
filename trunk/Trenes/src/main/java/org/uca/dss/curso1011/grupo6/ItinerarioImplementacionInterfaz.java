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
 *
 * @author Jose Luis
 */
public class ItinerarioImplementacionInterfaz implements Itinerario{

    private List<InformacionTrayecto> itrayectos;

    public double getPrecio() {
        double precio = 0;

        Iterator i = itrayectos.iterator();

         while (i.hasNext()){
             InformacionTrayecto itrayecto = (InformacionTrayecto)i.next();
             precio += itrayecto.getPrecio();
         }

        return precio;
    }

    public int size() {
        return itrayectos.size();
    }

    public boolean isEmpty() {
        return itrayectos.isEmpty();
    }

    public boolean contains(Object o) {
        return itrayectos.contains((InformacionTrayecto)o);
    }

    public Iterator<InformacionTrayecto> iterator() {
        return itrayectos.iterator();
    }

    public Object[] toArray() {
        return itrayectos.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return itrayectos.toArray(a);
    }

    public boolean add(InformacionTrayecto e) {
        return itrayectos.add(e);
    }

    public boolean remove(Object o) {
        return itrayectos.remove((InformacionTrayecto)o);
    }

    public boolean containsAll(Collection<?> c) {
        return itrayectos.containsAll(c);
    }

    public boolean addAll(Collection<? extends InformacionTrayecto> c) {
        return itrayectos.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends InformacionTrayecto> c) {
        return itrayectos.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return itrayectos.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return itrayectos.retainAll(c);
    }

    public void clear() {
        itrayectos.clear();
    }

    public InformacionTrayecto get(int index) {
        return itrayectos.get(index);
    }

    public InformacionTrayecto set(int index, InformacionTrayecto element) {
        return itrayectos.set(index, element);
    }

    public void add(int index, InformacionTrayecto element) {
        itrayectos.add(index, element);
    }

    public InformacionTrayecto remove(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<InformacionTrayecto> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<InformacionTrayecto> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<InformacionTrayecto> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
