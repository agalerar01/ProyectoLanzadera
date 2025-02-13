package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import org.example.Model.Lanzadera;
import org.example.Model.Tripulante;
import org.example.Utils.Campos;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import static com.mongodb.client.model.Filters.*;

public class TripulanteRepository {

    private MongoCollection<Tripulante> collection;

    public TripulanteRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_TRIPULACION, Tripulante.class);
    }

    public List<Tripulante> recuperarTripulantes() {
        return collection.find().into(new ArrayList<>());
    }

    public List<Tripulante> recuperarPersonalDisponible(ObjectId lanzaderaId) {

        return collection.find(and(eq(Campos.TRIPULANTE_ESTADO, true),(in(Campos.LANZADERAID, lanzaderaId)))).into(new ArrayList<>());
    }

    public Tripulante recuperarTripulantesPorId(ObjectId id) {
        return collection.find(eq(Campos.ID, id)).first();
    }
}
