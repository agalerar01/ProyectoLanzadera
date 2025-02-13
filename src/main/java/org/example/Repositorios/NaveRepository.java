package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.Model.Lanzadera;
import org.example.Model.Nave;
import org.example.Utils.Campos;

import java.util.ArrayList;
import java.util.List;

public class NaveRepository {

    private MongoCollection<Nave> collection;

    public NaveRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_NAVES, Nave.class);
    }

    public List<Nave> recuperarNaves() {
        return collection.find().into(new ArrayList<>());
    }
}
