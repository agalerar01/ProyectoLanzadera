package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Model.Lanzadera;
import org.example.Model.Nave;
import org.example.Utils.Campos;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class NaveRepository {

    private MongoCollection<Nave> collection;

    public NaveRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_NAVES, Nave.class);
    }

    public List<Nave> recuperarNaves() {
        return collection.find().into(new ArrayList<>());
    }

    public List<Nave> recuperarNavesPorLanzadera(ObjectId lanzId) {
        return collection.find(eq(Campos.LANZADERAID, lanzId)).into(new ArrayList<>());
    }

    public Nave recuperarNavesPorId(ObjectId id) {
        return collection.find(eq(Campos.ID, id)).first();
    }
}
