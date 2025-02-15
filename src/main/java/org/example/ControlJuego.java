package org.example;

import org.bson.types.ObjectId;
import org.example.Enums.Estado;
import org.example.Enums.TipoNave;
import org.example.Model.*;
import org.example.Repositorios.*;
import org.example.Utils.Campos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        naveRepository = new NaveRepository();
        cargaRepository = new CargaRepository();
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
        List<AgendaLanzamientos> lAgenda = agendaLanzamientoRepository.recuperarPorLanzaderaIdYPlanificado(lanzSelect.getId());
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
        if(aG != null) {
            Nave nave = naveRepository.recuperarNavesPorId(aG.getNaveId());

            System.out.println("Nave en lanzadera: ");
            System.out.println("    Fecha: " + aG.getFecha());
            System.out.println("    Nave: " + nave.getNombre());
            System.out.println("    Tipo: " + nave.getTipo());
            System.out.println("    Combustible: " + nave.getCombustible());
            System.out.println("    Oxigeno: " + nave.getOxigeno());
            System.out.println("    Plan de Vuelo: " + calcularPlanVuelo(aG) + " cuadriculas");
            switch (nave.getTipo()) {
                case EXPLORACION:
                    System.out.println("    Duracion de la mision: " + calcularPlanVuelo(aG) + " dias");
                    break;
                case INVESTIGACION:
                    System.out.println("    Duracion de la mision: " + (calcularPlanVuelo(aG) * 2) + " dias");
                    break;
                case TRANSBORDADOR:
                    System.out.println("    Duracion de la mision: " + (calcularPlanVuelo(aG) * 2) + " dias");
                    break;
            }
            if (aG.getTripulacionIds() == null) {
                System.out.println("    No hay tripulacion asignada");
            } else {
                if (!aG.getTripulacionIds().isEmpty()) {
                    System.out.println("    Tripulacion Asignada: ");
                    for (int i = 0; i < aG.getTripulacionIds().size(); i++) {
                        Tripulante t1 = tripulanteRepository.recuperarTripulantesPorId(aG.getTripulacionIds().get(i));
                        System.out.println("        - " + t1.getNombre() + " (" + t1.getTipo() + ")");
                    }
                }
            }
        }else{
            System.out.println("No se han añadido lanzamientos hasta el momento");
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

        if(aG != null) {
            switch (nave.getTipo()) {
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
        }else{
            System.out.println("Aun no se ha planificado un lanzamiento");
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
                    comand = case1Tripulante(comand, lTripulante.get(i));
                    break;
                case PILOTO:
                    pilo = case1Tripulante(pilo, lTripulante.get(i));
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
                    comand = case1Tripulante(comand, lTripulante.get(i));
                    break;
                case PILOTO:
                    pilo = case1Tripulante(pilo, lTripulante.get(i));
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

    public Tripulante case1Tripulante(Tripulante trip, Tripulante aux){
        if(trip == null){
            trip = aux;
        }else{
            if(trip.getPeso()>aux.getPeso()){
                trip = aux;
            }
        }

        return trip;
    }

    public void mostrarEmbarcados(Nave nave, List<Tripulante> lEmbarcados, ObjectId id){
        if(lEmbarcados.size() != nave.getCapacidad()){
            System.out.println("No hay personal suficiente");
        }else {
            agendaLanzamientoRepository.aniadirTripulacion(id, devolverIdsEmbarcados(lEmbarcados));
            System.out.println("Tripulacion embarcada en la nave " + nave.getNombre() + " (" + nave.getTipo() + "): ");
            for (int i = 0; i < lEmbarcados.size(); i++) {
                System.out.println("        - " + lEmbarcados.get(i).getNombre() + " (" + lEmbarcados.get(i).getTipo() + ")");
                tripulanteRepository.actualizarEstado(lEmbarcados.get(i).getId(), false);
            }
        }
    }

    public void cargarSuministros() {
        AgendaLanzamientos aG = agendaLanzamientoRepository.recuperarPorFechaProxima(lanzSelect.getId(), LocalDate.now());
        Nave nave = naveRepository.recuperarNavesPorId(aG.getNaveId());
        double combustible = 0, pesoTripu = 0, pesoCarga = 0;
        establecerModificador(nave);

        if(aG != null) {
            if(aG.getTripulacionIds() != null) {
                switch (nave.getTipo()) {
                    case EXPLORACION:
                        List<Carga> lCarga = cargaRepository.recuperarCargasPorNave(Campos.CANTIDADPORTIPOEXPLORACION);
                        for (int i = 0; i < lCarga.size(); i++) {
                            pesoCarga += lCarga.get(i).getCantidadPorTipo().getExploracion() * lCarga.get(i).getPesoPorUnidad();
                        }

                        cargarSuministros(nave, aG, pesoCarga);
                        break;
                    case INVESTIGACION:
                        List<Carga> lCarga2 = cargaRepository.recuperarCargasPorNave(Campos.CANTIDADPORTIPOINVESTIGACION);
                        for (int i = 0; i < lCarga2.size(); i++) {
                            pesoCarga += lCarga2.get(i).getCantidadPorTipo().getInvestigacion() * lCarga2.get(i).getPesoPorUnidad();
                        }

                        cargarSuministros(nave, aG, pesoCarga);
                        break;
                    case TRANSBORDADOR:
                        List<Carga> lCarga3 = cargaRepository.recuperarCargasPorNave(Campos.CANTIDADPORTIPOTRANSBORDADOR);
                        for (int i = 0; i < lCarga3.size(); i++) {
                            pesoCarga += lCarga3.get(i).getCantidadPorTipo().getTransbordador() * lCarga3.get(i).getPesoPorUnidad();
                        }

                        cargarSuministros(nave, aG, pesoCarga);
                        break;
                }
            }else{
                System.out.println("No hay tripulacion asignada al lanzamiento");
            }
        }else{
            System.out.println("Aun no se ha planificado un lanzamiento");
        }
        System.out.println();
    }

    private double calcularPesoTripu(AgendaLanzamientos aG) {
        int peso = 0;

        for (int i = 0; i < aG.getTripulacionIds().size(); i++) {
            Tripulante t1 = tripulanteRepository.recuperarTripulantesPorId(aG.getTripulacionIds().get(i));
            peso += t1.getPeso();
        }

        return peso;
    }

    public void cargarSuministros(Nave nave, AgendaLanzamientos aG, double pesoCarga){
        double oxigeno = 0, combustible = 0, pesoTripu = 0;

        if(nave.getTipo() != TipoNave.EXPLORACION) {

            pesoTripu = calcularPesoTripu(aG);

            combustible = (pesoCarga + pesoTripu) * nave.getModificador() * (calcularPlanVuelo(aG) * 2);

            oxigeno = aG.getTripulacionIds().size() * (calcularPlanVuelo(aG) * 2) * 3;

            if (lanzSelect.getCombustibleDisponible() > combustible && lanzSelect.getOxigenoDisponible() > oxigeno) {
                nave.setCombustible(combustible);
                naveRepository.updateCombustible(nave.getId(), combustible);

                lanzSelect.setCombustibleDisponible(lanzSelect.getCombustibleDisponible() - combustible);
                lanzaderaRepository.updateCombustible(lanzSelect.getId(), lanzSelect.getCombustibleDisponible());

                nave.setOxigeno(oxigeno);
                naveRepository.updateOxigeno(nave.getId(), oxigeno);

                lanzSelect.setOxigenoDisponible(lanzSelect.getOxigenoDisponible() - oxigeno);
                lanzaderaRepository.updateOxigeno(lanzSelect.getId(), lanzSelect.getOxigenoDisponible());
                System.out.println("Los suministros han sido cargados con exito");
            } else {
                System.out.println("No hay suficiente combustible u oxigeno en la lanzadera");
            }
        }else{
            pesoTripu = calcularPesoTripu(aG);

            combustible = (pesoCarga+pesoTripu)*nave.getModificador()*calcularPlanVuelo(aG);

            if(lanzSelect.getCombustibleDisponible()>combustible){
                nave.setCombustible(combustible);
                naveRepository.updateCombustible(nave.getId(), combustible);

                lanzSelect.setCombustibleDisponible(lanzSelect.getCombustibleDisponible()-combustible);
                lanzaderaRepository.updateCombustible(lanzSelect.getId(), lanzSelect.getCombustibleDisponible());
                System.out.println("Los suministros han sido cargados con exito");
            }else{
                System.out.println("No hay suficiente combustible en la lanzadera");
            }
        }
    }

    public void cargarSuministrosLanzadera() {
        if(Objects.equals(lanzSelect.getOxigenoDisponible(), lanzSelect.getCapacidadMaximaOxigeno())){
            System.out.println("El oxigeno se encuentra cargado");
        }else{
            System.out.println("Se ha recargado "+(lanzSelect.getCapacidadMaximaOxigeno()-lanzSelect.getOxigenoDisponible())+" de oxigeno");
            lanzSelect.setOxigenoDisponible(lanzSelect.getCapacidadMaximaOxigeno());
            lanzaderaRepository.updateOxigeno(lanzSelect.getId(), lanzSelect.getCapacidadMaximaOxigeno());
        }

        if(Objects.equals(lanzSelect.getCombustibleDisponible(), lanzSelect.getCapacidadMaximaCombustible())){
            System.out.println("El combustible se encuentra cargado");
        }else{
            System.out.println("Se ha recargado "+(lanzSelect.getCapacidadMaximaCombustible()-lanzSelect.getCombustibleDisponible())+" de combustible");
            lanzSelect.setCombustibleDisponible(lanzSelect.getCombustibleDisponible());
            lanzaderaRepository.updateCombustible(lanzSelect.getId(), lanzSelect.getCapacidadMaximaCombustible());
        }
        System.out.println();
    }

    public void cancelarLanzamiento() {
        AgendaLanzamientos aG = agendaLanzamientoRepository.recuperarPorFechaProxima(lanzSelect.getId(), LocalDate.now());
        if (aG != null) {
            Nave nave = naveRepository.recuperarNavesPorId(aG.getNaveId());

            agendaLanzamientoRepository.cambiarEstado(aG.getId(), Estado.CANCELADO);

            desembarcarTripulacion(aG.getTripulacionIds(),aG.getId());

            double combustibleSumar = nave.getCombustible() + lanzSelect.getCombustibleDisponible(), oxigenoSumar = nave.getOxigeno() + lanzSelect.getOxigenoDisponible();

            devolverCombustible(combustibleSumar, nave.getId());

            devolverOxigeno(oxigenoSumar, nave.getId());

            System.out.println("Lanzamiento cancelado: ");
            System.out.println("Nave: " + nave.getNombre());
            System.out.println("Fecha prevista: " + aG.getFecha());
            System.out.println("Suministros devueltos a la lanzadera.");
            System.out.println("Tripulacion desembarcada.");
        }else{
            System.out.println("No hay lanzamientos planificados hasta el momento");
        }
        System.out.println();
    }

    public void aplazarLanzamiento() {
        AgendaLanzamientos aG = agendaLanzamientoRepository.recuperarPorFechaProxima(lanzSelect.getId(), LocalDate.now());
        LocalDate nuevaFecha;
        if (aG != null) {
            Nave nave = naveRepository.recuperarNavesPorId(aG.getNaveId());

            nuevaFecha = aG.getFecha();

            desembarcarTripulacion(aG.getTripulacionIds(), aG.getId());

            double combustibleSumar = nave.getCombustible() + lanzSelect.getCombustibleDisponible(), oxigenoSumar = nave.getOxigeno() + lanzSelect.getOxigenoDisponible();

            devolverCombustible(combustibleSumar, nave.getId());

            devolverOxigeno(oxigenoSumar, nave.getId());

            do {
                nuevaFecha = nuevaFecha.plusMonths(1);
            } while (!comprobarVentana(nuevaFecha));

            System.out.println("Lanzamiento pospuesto: ");
            System.out.println("Nave: " + nave.getNombre());
            System.out.println("Fecha anterio: " + aG.getFecha());
            System.out.println("Nueva fecha: " + nuevaFecha);
            if (nave.getCombustible() != 0 && nave.getOxigeno() != 0) {
                System.out.println("Suministros devueltos a la lanzadera.");
            } else {
                System.out.println("No se habian cargado suministros");
            }
            if (aG.getTripulacionIds() != null) {
                System.out.println("Tripulacion desembarcada.");
            }else{
                System.out.println("No habia tripulacion embarcada");
            }
            agendaLanzamientoRepository.actualizarFecha(aG.getId(), nuevaFecha);
        }else{
            System.out.println("No hay lanzamientos planificados hasta el momento");
        }
        System.out.println();
    }

    public void devolverCombustible(double combustibleSumar, ObjectId id){
        if (!(combustibleSumar > lanzSelect.getCapacidadMaximaCombustible())) {
            lanzSelect.setCombustibleDisponible(combustibleSumar);
            lanzaderaRepository.updateCombustible(lanzSelect.getId(), combustibleSumar);
        } else {
            lanzSelect.setCombustibleDisponible(lanzSelect.getCapacidadMaximaCombustible());
            lanzaderaRepository.updateCombustible(lanzSelect.getId(), lanzSelect.getCapacidadMaximaCombustible());
        }
        naveRepository.updateCombustible(id, 0);
    }

    public void devolverOxigeno(double oxigenoSumar, ObjectId id){
        if (!(oxigenoSumar > lanzSelect.getCapacidadMaximaOxigeno())) {
            lanzSelect.setOxigenoDisponible(oxigenoSumar);
            lanzaderaRepository.updateOxigeno(lanzSelect.getId(), oxigenoSumar);
        } else {
            lanzSelect.setOxigenoDisponible(lanzSelect.getCapacidadMaximaOxigeno());
            lanzaderaRepository.updateOxigeno(lanzSelect.getId(), lanzSelect.getCapacidadMaximaOxigeno());
        }
        naveRepository.updateOxigeno(id, 0);
    }

    public void desembarcarTripulacion(List<ObjectId> tripulacionIds, ObjectId id){
        if (tripulacionIds != null) {
            for (int i = 0; i < tripulacionIds.size(); i++) {
                Tripulante t1 = tripulanteRepository.recuperarTripulantesPorId(tripulacionIds.get(i));
                tripulanteRepository.actualizarEstado(t1.getId(), true);
            }
        }
        agendaLanzamientoRepository.aniadirTripulacion(id, new ArrayList<>());
    }
}
