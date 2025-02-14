package org.example;

import org.bson.types.ObjectId;
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

    public void establecerModificador(Nave nave){

        switch (nave.getTipo()){
            case EXPLORACION:
                nave.setModificador(0.01);
                nave.setCapacidad(3);
                break;

            case INVESTIGACION:
                nave.setModificador(0.02);
                nave.setCapacidad(5);
                break;

            case TRANSBORDADOR:
                nave.setModificador(0.03);
                nave.setCapacidad(4);
                break;
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
        if(!lAgenda.isEmpty()) {
            for (int i = 0; i < lAgenda.size(); i++) {
                Nave nave = naveRepository.recuperarNavesPorId(lAgenda.get(i).getNaveId());
                System.out.println("    Nave: " + nave.getNombre());
                System.out.println("    Tipo: " + nave.getTipo());
                System.out.println("    Fecha de lanzamiento: " + lAgenda.get(i).getFecha());
                System.out.println("    --------------------------------");
            }
        }else{
            System.out.println("Aun no se ha planificado ningun lanzamiento");
        }
        System.out.println();
    }

    public void mostrarEstadoProximoLanzamiento() {
        AgendaLanzamientos aG = agendaLanzamientoRepository.recuperarPorFechaProxima(lanzSelect.getId(), LocalDate.now());
        Nave nave = naveRepository.recuperarNavesPorId(aG.getNaveId());

        System.out.println("Nave en lanzadera: ");
        System.out.println("    Fecha: "+aG.getFecha());
        System.out.println("    Nave: " + nave.getNombre());
        System.out.println("    Tipo: " + nave.getTipo());
        System.out.println("    Combustible: "+nave.getCombustible());
        System.out.println("    Oxigeno: "+nave.getOxigeno());
        System.out.println("    Plan de Vuelo: "+calcularPlanVuelo(aG)+" cuadriculas");
        switch (nave.getTipo()){
            case EXPLORACION:
                System.out.println("    Duracion de la mision: "+calcularPlanVuelo(aG)+" dias");
                break;
            case INVESTIGACION:
                System.out.println("    Duracion de la mision: "+(calcularPlanVuelo(aG)*2)+" dias");
                break;
            case TRANSBORDADOR:
                System.out.println("    Duracion de la mision: "+(calcularPlanVuelo(aG)*2)+" dias");
                break;
        }
        if(aG.getTripulacionIds() == null){
            System.out.println("    No hay tripulacion asignada");
        }else{
            if(!aG.getTripulacionIds().isEmpty()) {
                System.out.println("    Tripulacion Asignada: ");
                for (int i = 0; i < aG.getTripulacionIds().size(); i++) {
                    Tripulante t1 = tripulanteRepository.recuperarTripulantesPorId(aG.getTripulacionIds().get(i));
                    System.out.println("        - " + t1.getNombre() + " (" + t1.getTipo() + ")");
                }
            }
        }
        System.out.println();
    }

    public int calcularPlanVuelo(AgendaLanzamientos aG){
        List<List<String>> cuadricula = aG.getPlanVuelo();

        int cont = 0;

        for(int i = 0; i < cuadricula.size(); i++){
            for(int j = 0; j < cuadricula.get(i).size(); j++){
                if(cuadricula.get(i).get(j).equalsIgnoreCase("x")){
                    cont++;
                }
            }
        }
        return cont;
    }

    public void embarcarTripulacion() {
        List<Tripulante> lTripulante = tripulanteRepository.recuperarPersonalDisponible(lanzSelect.getId());
        AgendaLanzamientos aG = agendaLanzamientoRepository.recuperarPorFechaProxima(lanzSelect.getId(), LocalDate.now());
        Nave nave = naveRepository.recuperarNavesPorId(aG.getNaveId());
        establecerModificador(nave);

        switch (nave.getTipo()){
            case TRANSBORDADOR:
                List<Tripulante> lEmbarcados = rellenarTransbordador(lTripulante);
                mostrarEmbarcados(nave, lEmbarcados, aG.getId());
                break;
            case INVESTIGACION:
                List<Tripulante> lEmbarcados2 = rellenarInvestigacion(lTripulante);
                mostrarEmbarcados(nave, lEmbarcados2, aG.getId());
                break;
            case EXPLORACION:
                List<Tripulante> lEmbarcados3 = rellenarExploracion(lTripulante);
                mostrarEmbarcados(nave, lEmbarcados3, aG.getId());
                break;
        }
        System.out.println();
    }

    private List<ObjectId> devolverIdsEmbarcados(List<Tripulante> lEmbarcados) {
        List<ObjectId> lIds = new ArrayList<>();

        for (int i = 0; i < lEmbarcados.size(); i++){
            lIds.add(lEmbarcados.get(i).getId());
        }

        return lIds;
    }

    public List<Tripulante> rellenarTransbordador(List<Tripulante> lTripulante){
        List<Tripulante> lEmbarcados = new ArrayList<>();
        Tripulante comand = null, pilo = null, ing1 = null, ing2 = null;

        for(int i = 0; i < lTripulante.size(); i++){
            switch (lTripulante.get(i).getTipo()){
                case COMANDANTE:
                    comand = caseComandante(comand, lTripulante.get(i));
                    break;
                case PILOTO:
                    pilo = casePiloto(pilo, lTripulante.get(i));
                    break;
                case INGENIERO:
                    if(ing1 == null){
                        ing1 = lTripulante.get(i);
                    }else{
                        if(ing2 == null) {
                            ing2 = lTripulante.get(i);
                        }else{
                            if(ing1.getPeso()>lTripulante.get(i).getPeso()){
                                if(ing2.getPeso()>ing1.getPeso()){
                                    ing2 = ing1;
                                }
                                ing1 = lTripulante.get(i);
                            }
                        }
                    }
                    break;
            }
        }
        if(comand != null){
            lEmbarcados.add(comand);
        }
        if(pilo != null){
            lEmbarcados.add(pilo);
        }
        if(ing1 != null){
            lEmbarcados.add(ing1);
        }
        if(ing2 != null){
            lEmbarcados.add(ing2);
        }

        return lEmbarcados;
    }

    private List<Tripulante> rellenarInvestigacion(List<Tripulante> lTripulante) {
        List<Tripulante> lEmbarcados = new ArrayList<>();
        Tripulante comand = null, pilo = null, cient1 = null, cient2 = null, cient3 = null;

        for(int i = 0; i < lTripulante.size(); i++){
            switch (lTripulante.get(i).getTipo()){
                case COMANDANTE:
                    comand = caseComandante(comand, lTripulante.get(i));
                    break;
                case PILOTO:
                    pilo = casePiloto(pilo, lTripulante.get(i));
                    break;
                case CIENTIFICO:
                    if(cient1 == null){
                        cient1 = lTripulante.get(i);
                    }else{
                        if(cient2 == null) {
                            cient2 = lTripulante.get(i);
                        }else{
                            if(cient3 == null){
                                cient3 = lTripulante.get(i);
                            }else{
                                if(cient1.getPeso()>lTripulante.get(i).getPeso()){
                                    if(cient2.getPeso()>cient1.getPeso()){
                                        if (cient3.getPeso()>cient2.getPeso()) {
                                            cient3 = cient2;
                                        }
                                        cient2 = cient1;
                                    } else if(cient3.getPeso()>cient1.getPeso()) {
                                        cient3 = cient1;
                                    }
                                    cient1 = lTripulante.get(i);
                                }
                            }
                        }
                    }
                    break;
            }
        }
        if(comand != null){
            lEmbarcados.add(comand);
        }
        if(pilo != null){
            lEmbarcados.add(pilo);
        }
        if(cient1 != null){
            lEmbarcados.add(cient1);
        }
        if(cient2 != null){
            lEmbarcados.add(cient2);
        }
        if(cient3 != null){
            lEmbarcados.add(cient3);
        }

        return lEmbarcados;
    }

    private List<Tripulante> rellenarExploracion(List<Tripulante> lTripulante) {
        List<Tripulante> lEmbarcados = new ArrayList<>();
        Tripulante droid1 = null, droid2 = null, droid3 = null;

        for(int i = 0; i < lTripulante.size(); i++){
            switch (lTripulante.get(i).getTipo()){
                case DROIDE:
                    if(droid1 == null){
                        droid1 = lTripulante.get(i);
                    }else{
                        if(droid2 == null) {
                            droid2 = lTripulante.get(i);
                        }else{
                            if(droid3 == null){
                                droid3 = lTripulante.get(i);
                            }else{
                                if(droid1.getPeso()>lTripulante.get(i).getPeso()){
                                    if(droid2.getPeso()>droid1.getPeso()){
                                        if (droid3.getPeso()>droid2.getPeso()) {
                                            droid3 = droid2;
                                        }
                                        droid2 = droid1;
                                    } else if(droid3.getPeso()>droid1.getPeso()) {
                                        droid3 = droid1;
                                    }
                                    droid1 = lTripulante.get(i);
                                }
                            }
                        }
                    }
                    break;
            }
        }
        if(droid1 != null){
            lEmbarcados.add(droid1);
        }
        if(droid2 != null){
            lEmbarcados.add(droid2);
        }
        if(droid3 != null){
            lEmbarcados.add(droid3);
        }

        return lEmbarcados;
    }

    public Tripulante caseComandante(Tripulante comand, Tripulante aux){
        if(comand == null){
            comand = aux;
        }else{
            if(comand.getPeso()>aux.getPeso()){
                comand = aux;
            }
        }

        return comand;
    }

    public Tripulante casePiloto(Tripulante pilo, Tripulante aux){
        if(pilo == null){
            pilo = aux;
        }else{
            if(pilo.getPeso()>aux.getPeso()){
                pilo = aux;
            }
        }

        return pilo;
    }

    public void mostrarEmbarcados(Nave nave, List<Tripulante> lEmbarcados, ObjectId id){
        if(lEmbarcados.size() != nave.getCapacidad()){
            System.out.println("No hay personal suficiente");
        }else {
            agendaLanzamientoRepository.aniadirTripulacion(id, devolverIdsEmbarcados(lEmbarcados));
            System.out.println("Tripulacion embarcada en la nave " + nave.getNombre() + " (" + nave.getTipo() + "): ");
            for (int i = 0; i < lEmbarcados.size(); i++) {
                System.out.println("        - " + lEmbarcados.get(i).getNombre() + " (" + lEmbarcados.get(i).getTipo() + ")");
            }
        }
    }

    public void cargarSuministros() {
        AgendaLanzamientos aG = agendaLanzamientoRepository.recuperarPorFechaProxima(lanzSelect.getId(), LocalDate.now());
        Nave nave = naveRepository.recuperarNavesPorId(aG.getNaveId());
        int oxigeno = 0, combustible = 0;

        switch (nave.getTipo()){
            case EXPLORACION:
                List<Carga> lCarga = cargaRepository.recuperarCargasPorNave();
                break;
            case INVESTIGACION:

                break;
            case TRANSBORDADOR:

                break;
        }
    }
}
