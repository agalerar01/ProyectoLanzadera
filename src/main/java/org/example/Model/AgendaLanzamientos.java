package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.Enums.Estado;

import java.time.LocalDate;
import java.util.List;

public class AgendaLanzamientos {

    private ObjectId id;
    private LocalDate fecha;
    @BsonProperty(value = "lanzadera_id")
    private ObjectId lanzaderaId;
    @BsonProperty(value = "nave_id")
    private ObjectId naveId;
    private Estado estado;
    @BsonProperty(value = "plan_vuelo")
    private List<List<String>> planVuelo;
    @BsonProperty(value = "tripulacion")
    private List<ObjectId> tripulacionIds;

    public AgendaLanzamientos() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ObjectId getLanzaderaId() {
        return lanzaderaId;
    }

    public void setLanzaderaId(ObjectId lanzaderaId) {
        this.lanzaderaId = lanzaderaId;
    }

    public ObjectId getNaveId() {
        return naveId;
    }

    public void setNaveId(ObjectId naveId) {
        this.naveId = naveId;
    }

    public org.example.Enums.Estado getEstado() {
        return estado;
    }

    public void setEstado(org.example.Enums.Estado estado) {
        this.estado = estado;
    }

    public List<List<String>> getPlanVuelo() {
        return planVuelo;
    }

    public void setPlanVuelo(List<List<String>> planVuelo) {
        this.planVuelo = planVuelo;
    }


    public List<ObjectId> getTripulacionIds() {
        return tripulacionIds;
    }

    public void setTripulacionIds(List<ObjectId> tripulacionIds) {
        this.tripulacionIds = tripulacionIds;
    }
}
