package org.example;

import org.example.Model.Lanzadera;
import org.example.Repositorios.*;

import java.util.List;

import static org.example.Utils.Utils.pedirInt;

public class ControlJuego {

    private static ControlJuego instance;
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
}
