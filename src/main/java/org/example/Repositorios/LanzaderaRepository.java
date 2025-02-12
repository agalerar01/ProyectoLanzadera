package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.Model.Lanzadera;
import org.example.Utils.Campos;

import java.util.ArrayList;
import java.util.List;

public class LanzaderaRepository {

    private MongoCollection<Lanzadera> collection;

    public LanzaderaRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_LANZADERA, Lanzadera.class);
    }

    public List<Lanzadera> recuperarLanzaderas() {
        return collection.find().into(new ArrayList<>());
    }
}
