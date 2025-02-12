package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.Model.Nave;
import org.example.Utils.Campos;

public class NaveRepository {

    private MongoCollection<Nave> collection;

    public NaveRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_NAVES, Nave.class);
    }

}
