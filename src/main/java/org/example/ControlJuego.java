package org.example;

import org.example.Enums.TipoNave;
import org.example.Model.Carga;
import org.example.Model.Lanzadera;
import org.example.Model.Nave;
import org.example.Model.Tripulante;
import org.example.Repositorios.*;

import java.util.List;

import static org.example.Enums.TipoNave.*;
import static org.example.Utils.Utils.pedirInt;

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
    }

    public void cerrarSesion(){
        db.closeMongoClient();
    }
}
