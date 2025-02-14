package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;
import org.example.Model.Lanzadera;
import org.example.Utils.Campos;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class LanzaderaRepository {

    private MongoCollection<Lanzadera> collection;

    public LanzaderaRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_LANZADERA, Lanzadera.class);
    }

    public List<Lanzadera> recuperarLanzaderas() {
        return collection.find().into(new ArrayList<>());
    }

    public void updateCombustible(ObjectId id, Double combustibleDisponible) {
        collection.updateOne(eq(Campos.ID, id), Updates.set(Campos.COMBUSTIBLEDISPONIBLE, combustibleDisponible));
    }

    public void updateOxigeno(ObjectId id, Double oxigenoDisponible) {
        collection.updateOne(eq(Campos.ID, id), Updates.set(Campos.OXIGENODISPONIBLE, oxigenoDisponible));
    }
}
