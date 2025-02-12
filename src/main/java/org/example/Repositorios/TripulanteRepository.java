package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.Model.Tripulante;
import org.example.Utils.Campos;

public class TripulanteRepository {

    private MongoCollection<Tripulante> collection;

    public TripulanteRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_TRIPULACION, Tripulante.class);
    }
}
