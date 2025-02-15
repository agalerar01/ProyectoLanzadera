package org.example.Repositorios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
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
        return collection.find(and(eq(Campos.LANZADERAID, lanzaderaId),
                gt(Campos.FECHA, fechaAnterio),
                lt(Campos.FECHA, fechaPosterior),
                eq(Campos.ESTADO, Estado.PLANIFICADO))
        ).into(new ArrayList<>());
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

    public AgendaLanzamientos recuperarPorFechaProxima(ObjectId lanzId, LocalDate fechaActual) {
        return collection.find(and(eq(Campos.LANZADERAID, lanzId),gte(Campos.FECHA, fechaActual),eq(Campos.ESTADO, Estado.PLANIFICADO))).sort(new Document(Campos.FECHA, 1)).first();
    }

    public void aniadirTripulacion(ObjectId aG, List<ObjectId> lIds) {
        collection.updateOne(eq(Campos.ID, aG), Updates.addEachToSet(Campos.TRIPULACION,lIds));
    }

    public AgendaLanzamientos recuperarPorId(ObjectId id) {
        return collection.find(eq(Campos.ID, id)).first();
    }

    public void cambiarEstado(ObjectId id, Estado estado) {
        collection.updateOne(eq(Campos.ID, id), Updates.set(Campos.ESTADO, estado));
    }

    public void actualizarFecha(ObjectId id, LocalDate nuevaFecha) {
        collection.updateOne(eq(Campos.ID, id), Updates.set(Campos.FECHA, nuevaFecha));
    }

    public List<AgendaLanzamientos> recuperarPorLanzaderaIdYPlanificado(ObjectId id) {
        return collection.find(and(eq(Campos.LANZADERAID, id),eq(Campos.ESTADO, Estado.PLANIFICADO))).into(new ArrayList<>());
    }
}
