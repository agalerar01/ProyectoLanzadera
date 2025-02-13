package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.example.Enums.Estado;
import org.example.Model.AgendaLanzamientos;
import org.example.Utils.Campos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class AgendaLanzamientoRepository {

    private MongoCollection<AgendaLanzamientos> collection;

    public AgendaLanzamientoRepository(){
        MongoDatabase database = ConexionDB.getInstance().recuperarDatabase();

        collection = database.getCollection(Campos.COLECCION_AGENDA_LANZAMIENTOS, AgendaLanzamientos.class);
    }

    public List<AgendaLanzamientos> recuperarPorVentanaHabil(LocalDate fechaAnterio, LocalDate fechaPosterior, ObjectId lanzaderaId) {
        return collection.find(and(eq(Campos.LANZADERAID, lanzaderaId),gt(Campos.FECHA, fechaAnterio),lt(Campos.FECHA, fechaPosterior))).into(new ArrayList<>());
    }

    public List<AgendaLanzamientos> recuperarPorNaveYPlanificado(ObjectId naveId, ObjectId lanzaId) {
        return collection.find(and(eq(Campos.LANZADERAID, lanzaId),eq(Campos.NAVEID, naveId),eq(Campos.ESTADO, Estado.PLANIFICADO))).into(new ArrayList<>());
    }

    public void insertarAgenda(AgendaLanzamientos aG) {
        collection.insertOne(aG);
    }

    public List<AgendaLanzamientos> recuperarPorLanzaderaId(ObjectId lanzId) {
        return collection.find(eq(Campos.LANZADERAID, lanzId)).into(new ArrayList<>());
    }
}
