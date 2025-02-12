package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.Model.AgendaLanzamientos;
import org.example.Utils.Campos;

public class AgendaLanzamientoRepository {

    private MongoCollection<AgendaLanzamientos> collection;

    public AgendaLanzamientoRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_AGENDA_LANZAMIENTOS, AgendaLanzamientos.class);
    }
}
