package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.Model.Carga;
import org.example.Model.Lanzadera;
import org.example.Utils.Campos;

import java.util.ArrayList;
import java.util.List;

public class CargaRepository {

    private MongoCollection<Carga> collection;

    public CargaRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_CARGA, Carga.class);
    }

    public List<Carga> recuperarCargas() {
        return collection.find().into(new ArrayList<>());
    }
}
