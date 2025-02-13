package org.example;

import org.example.Enums.Estado;
import org.example.Enums.TipoNave;
import org.example.Model.*;
import org.example.Repositorios.*;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Enums.TipoNave.*;
import static org.example.Utils.LanzamientosUtils.generarPlanVuelo;
import static org.example.Utils.Utils.pedirInt;
import static org.example.Utils.Utils.pedirString;

public class ControlJuego {

    private static ControlJuego instance;
    private ConexionDB db = ConexionDB.getInstance();
    private LanzaderaRepository lanzaderaRepository;
    private AgendaLanzamientoRepository agendaLanzamientoRepository;
    private CargaRepository cargaRepository;
    private NaveRepository naveRepository;
    private TripulanteRepository tripulanteRepository;
    private Lanzadera lanzSelect;

    private ControlJuego(){
        lanzaderaRepository = new LanzaderaRepository();
        agendaLanzamientoRepository = new AgendaLanzamientoRepository();
        cargaRepository = new CargaRepository();
        naveRepository = new NaveRepository();
        tripulanteRepository = new TripulanteRepository();
    }

    public static ControlJuego getInstance(){
        if(instance == null){
            instance = new ControlJuego();
        }

        return instance;
    }

    public void seleccionarLanzadera(){
        List<Lanzadera> lLanzaderas =  lanzaderaRepository.recuperarLanzaderas();

        System.out.println("Seleciona una lanzadera disponible: ");
        for(int i = 0; i < lLanzaderas.size(); i++){
            System.out.println((i+1)+". "+lLanzaderas.get(i).getNombre());
        }
        System.out.print("Elige una opcion (1-3): ");
        lanzSelect = lLanzaderas.get(pedirInt()-1);
    }

    public void establecerModificador(){
        List<Nave> lNaves =  naveRepository.recuperarNaves();

        for(int i = 0; i < lNaves.size(); i++){
            switch (lNaves.get(i).getTipo()){
                case EXPLORACION:
                    lNaves.get(i).setModificador(0.01);
                    break;

                case INVESTIGACION:
                    lNaves.get(i).setModificador(0.02);
                    break;

                case TRANSBORDADOR:
                    lNaves.get(i).setModificador(0.03);
                    break;
            }
        }
    }

    public void mostrarPersonalDisponible() {
        List<Tripulante> lTripulante = tripulanteRepository.recuperarPersonalDisponible(lanzSelect.getId());
        int droide=0, piloto=0, ingeniero=0, cientifico=0, comandante=0;

        for (int i = 0; i < lTripulante.size(); i++){
            switch (lTripulante.get(i).getTipo()){
                case DROIDE:
                    droide++;
                    break;
                case PILOTO:
                    piloto++;
                    break;
                case INGENIERO:
                    ingeniero++;
                    break;
                case CIENTIFICO:
                    cientifico++;
                    break;
                case COMANDANTE:
                    comandante++;
                    break;
            }
        }

        System.out.println("Personal disponible en la Lanzadera: ");
        System.out.println("DROIDE: "+droide);
        System.out.println("COMANDANTE: "+comandante);
        System.out.println("CIENTIFICO: "+cientifico);
        System.out.println("INGENIERO: "+ingeniero);
        System.out.println("PILOTO: "+piloto);
    }

    public void planificarLanzamiento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Nave> lNaves = naveRepository.recuperarNavesPorLanzadera(lanzSelect.getId());
        List<Nave> lNavesDisponibles = new ArrayList<>();
        LocalDate fecha = null;
        boolean ventanaHabil = true;

        for(int i = 0; i < lNaves.size(); i++){
            List<AgendaLanzamientos> lAgenda = agendaLanzamientoRepository.recuperarPorNaveYPlanificado(lNaves.get(i).getId(), lanzSelect.getId());

            if(lAgenda.isEmpty()){
                lNavesDisponibles.add(lNaves.get(i));
            }
        }

        System.out.print("Ingrese la fecha del lanzamiento (DD-MM-YYYY): ");
        String stringFecha = pedirString();
        System.out.println();

        System.out.println("Naves disponibles para el lanzamiento: ");
        for(int i = 0; i < lNavesDisponibles.size(); i++){
            System.out.println((i+1)+". "+lNavesDisponibles.get(i).getNombre()+" ("+lNavesDisponibles.get(i).getTipo()+")");
        }

        System.out.print("Seleciona una nave: ");
        Nave naveElegida = lNavesDisponibles.get(pedirInt()-1);

        try {
            fecha = LocalDate.parse(stringFecha, formatter);
            ventanaHabil = comprobarVentana(fecha);
        } catch (DateTimeParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }

        if(ventanaHabil){
            AgendaLanzamientos aG = new AgendaLanzamientos();
            aG.setEstado(Estado.PLANIFICADO);
            aG.setFecha(fecha);
            aG.setLanzaderaId(lanzSelect.getId());
            aG.setNaveId(naveElegida.getId());
            aG.setPlanVuelo(generarPlanVuelo());

            agendaLanzamientoRepository.insertarAgenda(aG);
            System.out.println("Lanzamiento Agendado");
            System.out.println();
        }else{
            System.out.println("En esta fecha no hay ventana habil");
            System.out.println();
        }
    }

    public boolean comprobarVentana(LocalDate fecha){
        LocalDate fechaAnterior = fecha.minusDays(8);
        LocalDate fechaPosterior = fecha.plusDays(8);

        List<AgendaLanzamientos> lAgenda = agendaLanzamientoRepository.recuperarPorVentanaHabil(fechaAnterior, fechaPosterior, lanzSelect.getId());

        if(!lAgenda.isEmpty()){
            return false;
        }
        return true;
    }

    public void cerrarSesion(){
        db.closeMongoClient();
    }

    public void mostrarEstadoLanzadera() {
        List<AgendaLanzamientos> lAgenda = agendaLanzamientoRepository.recuperarPorLanzaderaId(lanzSelect.getId());
        System.out.println("Estado de la lanzadera: "+lanzSelect.getNombre());
        System.out.println("Combustible: "+lanzSelect.getCombustibleDisponible()+" / "+lanzSelect.getCapacidadMaximaCombustible());
        System.out.println("Oxigeno: "+lanzSelect.getOxigenoDisponible()+" / "+lanzSelect.getCapacidadMaximaOxigeno());
        System.out.println("Lanzamientos planificados: ");
        for(int i = 0; i < lAgenda.size(); i++){
            Nave nave = naveRepository.recuperarNavesPorId(lAgenda.get(i).getNaveId());
            System.out.println("    Nave: "+nave.getNombre());
            System.out.println("    Tipo: "+nave.getTipo());
            System.out.println("    Fecha de lanzamiento: "+lAgenda.get(i).getFecha());
            System.out.println("    --------------------------------");
        }
        System.out.println();
    }
}
