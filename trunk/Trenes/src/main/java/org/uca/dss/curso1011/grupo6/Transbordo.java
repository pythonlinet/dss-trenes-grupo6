/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uca.dss.curso1011.grupo6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InformacionTrayecto;
import org.uca.dss.curso1011.grupo6.interfazExtendido.InterfazListados;
import org.uca.dss.curso1011.grupo6.interfazExtendido.Itinerario;

/**
 * Clase que se encarga de realizar los listados de los transbordos disponibles
 * @author Jose Luis
 */
public class Transbordo implements InterfazListados {

    private Listado listado;

    /**
     * 
     */
    public Transbordo ()
    {

    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return 
     */
    private List<Itinerario> buscarInformacionTrayectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList<Itinerario>();

        Iterator<Trayecto> iTrayectos = getListado().getViajes().getTrayectos().iterator();

        Trayecto trayecto;
        
        while(iTrayectos.hasNext())
        {
             trayecto = iTrayectos.next();

             if(trayecto.getCiudadOrigen().equals(origen) &&
                   !trayecto.getCiudadDestino().equals(destino) &&
                   getListado().getCompras().asientosLibres(trayecto.getCiudadOrigen(), trayecto.getCiudadDestino(), fechaSalida,trayecto.getHorario().getSalida()) > 0)
            {
/*                 System.out.println("***BUSCAR ORIGEN***");
                 System.out.println("origen: "+trayecto.getCiudadOrigen());
                 System.out.println("destino: "+trayecto.getCiudadDestino());
                 System.out.println("Asientos: "+getListado().getCompras().asientosLibres(trayecto.getCiudadOrigen(), trayecto.getCiudadDestino(), fechaSalida,trayecto.getHorario().getSalida()));
                    //reservasValidas = listado.getCompras().obtenerReservas(trayecto,fechaSalida);
*/
              // if(trayecto.getTren().getPlazas() - reservasValidas.size() > 0)
                                
                    String origen1 = trayecto.getCiudadOrigen();
                    String destino1 = trayecto.getCiudadDestino();
                    LocalTime salida1 = trayecto.getHorario().getSalida();
                    LocalTime llegada1 =  trayecto.getHorario().getLlegada();                    
                    double precio1 = trayecto.getTren().getPrecio();

                    Iterator<Trayecto> jTrayectos = getListado().getViajes().getTrayectos().iterator();
                    while(jTrayectos.hasNext())
                    {                        
                        Trayecto trayectoj = jTrayectos.next();

                        if(trayectoj.getCiudadOrigen().equals(destino1) &&
                            trayectoj.getCiudadDestino().equals(destino) &&
                            trayectoj.getHorario().getSalida().compareTo(llegada1.plusMinutes(10)) >= 0 &&
                            getListado().getCompras().asientosLibres(trayectoj.getCiudadOrigen(), trayectoj.getCiudadDestino(), fechaSalida,trayectoj.getHorario().getSalida()) > 0)
                        {
/*                            System.out.println("***BUSCAR DESTINO***");
                            System.out.println("origen: "+trayectoj.getCiudadOrigen());
                            System.out.println("destino: "+trayectoj.getCiudadDestino());
                            System.out.println("Asientos: "+getListado().getCompras().asientosLibres(trayectoj.getCiudadOrigen(), trayectoj.getCiudadDestino(), fechaSalida,trayectoj.getHorario().getSalida()));
  */
                            //reservasValidas = viajes.obtenerReservas(trayectoj,fechaSalida);                                                        
                                InformacionTrayecto itrayecto1 = new InformacionTrayecto(origen1,destino1,salida1,llegada1,precio1);
                                InformacionTrayecto itrayecto2 = new InformacionTrayecto(trayectoj.getCiudadOrigen(),trayectoj.getCiudadDestino(),trayectoj.getHorario().getSalida(),trayectoj.getHorario().getLlegada(),trayectoj.getTren().getPrecio());

                                List<InformacionTrayecto> itinerario = new ArrayList<InformacionTrayecto>();
                                itinerario.add(itrayecto1);                                
                                itinerario.add(itrayecto2);
                                
                                itinerarios.add(new ItinerarioImplementacionInterfaz(itinerario));
//                                System.out.println("pasa***************");
                                
                            }
                        }                     
                }
             }         
        return itinerarios;
    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return 
     */
    private List<Itinerario> buscarTrayectosDirectos(String origen, String destino, LocalDate fechaSalida)
    {
        List<Itinerario> itinerarios = new ArrayList<Itinerario>();

        Iterator<Trayecto> iTrayectos = getListado().getViajes().getTrayectos().iterator();
//        Iterator<LocalTime> ihoras = getListado().getHorarios(origen, destino, fechaSalida).iterator(); // horas disponibles
        
        Trayecto trayecto;
        while (iTrayectos.hasNext())
         {                        
           trayecto = iTrayectos.next();
            
//            List<LocalTime> horas = getListado().getHorarios(origen, destino, fechaSalida);
//             System.out.println("HorasDisponibles: "+horas.size());
//            LocalTime hora;
//            while(ihoras.hasNext())
//            {
//                hora = ihoras.next();

//                System.out.println("Origen " +trayecto.getCiudadOrigen()+" " +origen);
//                System.out.println("Destino " +trayecto.getCiudadDestino()+" " +destino);
//                System.out.println("plazas" + getListado().getCompras().asientosLibres(trayecto.getCiudadOrigen(), trayecto.getCiudadDestino(), fechaSalida,trayecto.getHorario().getSalida()));
                
                if(trayecto.getCiudadOrigen().equals(origen) &&
                   trayecto.getCiudadDestino().equals(destino) &&
                   getListado().getCompras().asientosLibres(trayecto.getCiudadOrigen(), trayecto.getCiudadDestino(), fechaSalida,trayecto.getHorario().getSalida())>0)
                {
//                    System.out.println("*********************Cumpleeee y entraaaa");
                   InformacionTrayecto itrayecto = new  InformacionTrayecto(origen,destino,trayecto.getHorario().getSalida(),trayecto.getHorario().getLlegada(),trayecto.getTren().getPrecio());
                   List<InformacionTrayecto> itinerario = new ArrayList<InformacionTrayecto>();
                   itinerario.add(itrayecto);
                   itinerarios.add(new ItinerarioImplementacionInterfaz(itinerario));
                }
//            }
        }
        return itinerarios;
    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @param horaSalida
     * @param horaLlegada
     * @return 
     */
    public List<Itinerario> getItinerariosEntre(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalTime horaLlegada) {

        List<Itinerario> infoTrayectos = new ArrayList();
        List<Itinerario> trayectosDirectos = buscarTrayectosDirectos(origen,destino,fechaSalida);
        List<Itinerario> infoTrayectoTransbordos = buscarInformacionTrayectos(origen,destino,fechaSalida);

        Iterator<Itinerario> iTraDirectos = trayectosDirectos.iterator();

         while (iTraDirectos.hasNext())
         {
             Itinerario itinerarioi = iTraDirectos.next();

             if(itinerarioi.get(0).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerarioi.get(0).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    infoTrayectos.add(itinerarioi);
             }
         }
        
        Iterator iInfoTransbordos = infoTrayectoTransbordos.iterator();

         while (iInfoTransbordos.hasNext())
         {
             Itinerario itinerario = (Itinerario)iInfoTransbordos.next();

             if(itinerario.get(0).getHoraSalida().compareTo(horaSalida) >= 0 &&
                   itinerario.get(1).getHoraLlegada().compareTo(horaLlegada) <= 0)
             {
                    infoTrayectos.add(itinerario);
             }
         }

        return infoTrayectos;
    }

    /**
     * 
     * @param origen
     * @param destino
     * @param fechaSalida
     * @return 
     */
    public List<Itinerario> getItinerarios(String origen, String destino, LocalDate fechaSalida) {

        List<Itinerario> itinerarios = buscarTrayectosDirectos(origen,destino,fechaSalida);
        itinerarios.addAll(buscarInformacionTrayectos(origen,destino,fechaSalida));

        Iterator <Itinerario> iter = itinerarios.iterator();

        return itinerarios;
    }

    /**
     * @return the listado
     */
    public Listado getListado() {
        return listado;
    }

    /**
     * @param listado the listado to set
     */
    public void setListado(Listado listado) {
        this.listado = listado;
    }
    
}
